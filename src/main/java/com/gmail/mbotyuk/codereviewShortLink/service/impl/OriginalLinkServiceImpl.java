package com.gmail.mbotyuk.codereviewShortLink.service.impl;

import com.gmail.mbotyuk.codereviewShortLink.model.OriginalLink;
import com.gmail.mbotyuk.codereviewShortLink.repository.OriginalLinkDAO;
import com.gmail.mbotyuk.codereviewShortLink.service.OriginalLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OriginalLinkServiceImpl implements OriginalLinkService<OriginalLink> {

    private final OriginalLinkDAO originalLinkDAO;

    @Override
    public OriginalLink add(OriginalLink originalLink) {
        return originalLinkDAO.save(originalLink);
    }

    @Override
    @Cacheable(value = "original_original", key = "#originalURL", sync = true)
    public OriginalLink findByOriginalLink(String originalURL) {
        return originalLinkDAO.findByOriginalLink(originalURL);
    }
}
