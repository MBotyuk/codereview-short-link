package com.gmail.mbotyuk.codereviewShortLink.service.impl;

import com.gmail.mbotyuk.codereviewShortLink.dto.ShortLinkDTO;
import com.gmail.mbotyuk.codereviewShortLink.dto.StatDTO;
import com.gmail.mbotyuk.codereviewShortLink.model.OriginalLink;
import com.gmail.mbotyuk.codereviewShortLink.model.ShortLink;
import com.gmail.mbotyuk.codereviewShortLink.model.Stat;
import com.gmail.mbotyuk.codereviewShortLink.service.ConvertingOriginalLinkToShortLinkService;
import com.gmail.mbotyuk.codereviewShortLink.service.OriginalLinkService;
import com.gmail.mbotyuk.codereviewShortLink.service.ShortLinkService;
import com.gmail.mbotyuk.codereviewShortLink.service.StatService;
import com.gmail.mbotyuk.codereviewShortLink.utils.MappingUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OriginalLinkService.class, ShortLinkService.class,
        StatService.class, UrlServiceImpl.class, MappingUtils.class,
        ConvertingOriginalLinkToShortLinkService.class})
class UrlServiceImplTest {

    @MockBean
    OriginalLinkService originalLinkService;
    @MockBean
    ShortLinkService shortLinkService;
    @MockBean
    StatService statService;

    @Autowired
    UrlServiceImpl urlService;
    @Autowired
    MappingUtils mappingUtils;
    @Autowired
    ConvertingOriginalLinkToShortLinkService convertingOriginalLinkToShortLinkService;

    OriginalLink originalLink;
    ShortLink shortLink;
    Stat stat;
    ShortLinkDTO shortLinkDTO;
    StatDTO statDTO;

    @BeforeEach
    void setUp() {
        originalLink = new OriginalLink("http://localhost:9090/");
        shortLink = new ShortLink("test123", originalLink);
        stat = new Stat("http://localhost:9090/", "test123", 0L);
        shortLinkDTO = ShortLinkDTO.builder()
                .link(shortLink.getLink())
                .build();
        statDTO = StatDTO.builder()
                .link("test123")
                .original(stat.getOriginal())
                .rank(1)
                .count(stat.getCount())
                .build();
    }

    @Test
    void givenCorrectOriginalLinkReturnShortLinkDTOTest() {
        when(originalLinkService.findByOriginalLink(any())).thenReturn(null);
        when(originalLinkService.add(any())).thenReturn(originalLink);
        when(shortLinkService.add(any())).thenReturn(shortLink);
        when(statService.add(any())).thenReturn(stat);

        ShortLinkDTO generateShortLinkDTO = urlService.generate(originalLink);

        assertEquals(shortLinkDTO, generateShortLinkDTO);
    }

    @Test
    void givenCorrectShortUrlReturnOriginalUrlTest() {
        when(shortLinkService.findByShortLink(any())).thenReturn(shortLink);
        when(statService.findByStat(any())).thenReturn(stat);

        String originalURL = urlService.getOriginalUrl("test123");

        assertEquals("http://localhost:9090/", originalURL);
    }

    @Test
    void givenCorrectShortUrlReturnStatDTOTest() {
        when(statService.findByStat(any())).thenReturn(stat);
        when(statService.findAllWithSort(any())).thenReturn(List.of(stat));

        StatDTO statDTOTest = urlService.getStat("test123");

        assertEquals(statDTO, statDTOTest);
    }

    @Test
    void givenCorrectParamsReturnListStatDTOTest() {
        when(statService.findAllWithPageable(any())).thenReturn(new PageImpl<>(List.of(stat)));

        List<StatDTO> statDTOList = urlService.getStats(1, 1);

        assertEquals(statDTO, statDTOList.get(0));
    }
}