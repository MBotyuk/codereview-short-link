package com.gmail.mbotyuk.codereviewShortLink.service.impl;

import com.gmail.mbotyuk.codereviewShortLink.model.ShortLink;
import com.gmail.mbotyuk.codereviewShortLink.repository.ShortLinkDAO;
import com.gmail.mbotyuk.codereviewShortLink.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl implements ShortLinkService<ShortLink> {

    private final ShortLinkDAO shortLinkDAO;

    @Override
    public ShortLink add(ShortLink shortLink) {
        return shortLinkDAO.save(shortLink);
    }

    @Override
    @Cacheable(value = "short_original", key = "#originalURL", sync = true)
    public ShortLink findByOriginalLink(String originalURL) {
        return shortLinkDAO.findByOriginalLink(originalURL);
    }

    @Override
    @Cacheable(value = "short_short", key = "#shortURL", sync = true)
    public ShortLink findByShortLink(String shortURL) {
        return shortLinkDAO.findByShortLink(shortURL);
    }
}
