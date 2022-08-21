package com.gmail.mbotyuk.codereviewShortLink.service.impl;

import com.gmail.mbotyuk.codereviewShortLink.dto.ShortLinkDTO;
import com.gmail.mbotyuk.codereviewShortLink.dto.StatDTO;
import com.gmail.mbotyuk.codereviewShortLink.exception.FindInDatabaseException;
import com.gmail.mbotyuk.codereviewShortLink.model.OriginalLink;
import com.gmail.mbotyuk.codereviewShortLink.model.ShortLink;
import com.gmail.mbotyuk.codereviewShortLink.model.Stat;
import com.gmail.mbotyuk.codereviewShortLink.service.*;
import com.gmail.mbotyuk.codereviewShortLink.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final static Sort SORT_DIRECTION_DESC_PROPERTY_COUNT = Sort.by(new Sort.Order(Sort.Direction.DESC, "count"));

    private final ConvertingOriginalLinkToShortLinkService convertingOriginalLinkToShortLinkService;
    private final OriginalLinkService<OriginalLink> originalLinkService;
    private final ShortLinkService<ShortLink> shortLinkService;
    private final StatService<Stat> statService;
    private final MappingUtils mappingUtils;

    @Override
    @Transactional
    public ShortLinkDTO generate(OriginalLink originalLink) {
        ShortLink shortLink;
        if (originalLinkService.findByOriginalLink(originalLink.getOriginal()) != null) {
            log.info("OriginalLink is in the database");
            shortLink = shortLinkService.findByOriginalLink(originalLink.getOriginal());
        } else {
            originalLinkService.add(originalLink);
            log.info("OriginalLink successfully added to the database");

            final ShortLink shortLinkWithoutID = convertingOriginalLinkToShortLinkService.convert(originalLink);
            log.info("Successfully converted OriginalLink to ShortLink");
            log.debug("ShortLink without ID: {}", shortLinkWithoutID);

            shortLink = shortLinkService.add(shortLinkWithoutID);
            log.info("ShortLink successfully added to the database");

            final Stat stat = new Stat(originalLink.getOriginal(), shortLink.getLink(), 0L);
            log.info("Successfully create new stat for ShortLink");
            log.debug("Stat = {}", stat);

            statService.add(stat);
            log.info("Stat for ShortLink successfully added to the database");
        }

        log.debug("ShortLink = {}", shortLink);
        final ShortLinkDTO shortLinkDTO = mappingUtils.mapToShortLinkDTO(shortLink);
        log.info("Successfully mapping ShortLink to ShortLinkDTO");

        return shortLinkDTO;
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        final ShortLink shortLink = Optional
                .ofNullable(shortLinkService.findByShortLink(shortUrl))
                .orElseThrow(() -> new FindInDatabaseException("Error find by short url in table short_link"));
        log.info("Short url successfully found in the database");

        final Stat stat = statService.findByStat(shortUrl);
        log.info("Stat successfully found in the database");
        stat.setCount(stat.getCount() + 1);
        log.debug("Stat = {}", stat);
        statService.add(stat);
        log.info("Stat successfully added to the database");

        return shortLink.getOriginalLink().getOriginal();
    }

    @Override
    public StatDTO getStat(String shortUrl) {
        final Stat stat = Optional
                .ofNullable(statService.findByStat(shortUrl))
                .orElseThrow(() -> new FindInDatabaseException("Error find by short url in table stat"));
        log.info("Stat successfully found in the database");

        final List<Stat> listStatsWithSort = statService.findAllWithSort(SORT_DIRECTION_DESC_PROPERTY_COUNT);

        final StatDTO statDTO = StatDTO.builder()
                .link(shortUrl)
                .original(stat.getOriginal())
                .rank(listStatsWithSort.indexOf(stat) + 1)
                .count(stat.getCount())
                .build();
        log.info("Successfully mapping Stat to StatDTO");

        return statDTO;
    }

    @Override
    public List<StatDTO> getStats(int page, int count) {
        final Pageable paging = PageRequest.of(--page, count, SORT_DIRECTION_DESC_PROPERTY_COUNT);
        final Page<Stat> allStats = statService.findAllWithPageable(paging);
        log.info("All stat successfully found in the database");

        final List<Stat> statList = allStats.getContent();
        log.info("List stats size = {}", statList.size());

        final List<StatDTO> resultList = new ArrayList<>(statList.size());
        for (Stat stat : statList) {
            resultList.add(mappingUtils.mapToStatDTO(stat, statList.indexOf(stat) + 1));
        }

        return resultList;
    }
}