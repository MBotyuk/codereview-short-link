package com.gmail.mbotyuk.codereviewShortLink.utils;

import com.gmail.mbotyuk.codereviewShortLink.dto.ShortLinkDTO;
import com.gmail.mbotyuk.codereviewShortLink.dto.StatDTO;
import com.gmail.mbotyuk.codereviewShortLink.model.ShortLink;
import com.gmail.mbotyuk.codereviewShortLink.model.Stat;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    public ShortLinkDTO mapToShortLinkDTO(ShortLink shortLink) {
        return ShortLinkDTO.builder()
                .link(shortLink.getLink())
                .build();
    }

    public StatDTO mapToStatDTO(Stat stat, int rank) {
        return StatDTO.builder()
                .link(stat.getShortLink())
                .original(stat.getOriginal())
                .rank(rank)
                .count(stat.getCount())
                .build();
    }
}
