package com.gmail.mbotyuk.codereviewShortLink.service;

import com.gmail.mbotyuk.codereviewShortLink.dto.ShortLinkDTO;
import com.gmail.mbotyuk.codereviewShortLink.dto.StatDTO;
import com.gmail.mbotyuk.codereviewShortLink.model.OriginalLink;

import java.util.List;

public interface UrlService {

    ShortLinkDTO generate(OriginalLink originalLink);

    String getOriginalUrl(String shortUrl);

    StatDTO getStat(String shortUrl);

    List<StatDTO> getStats(int page, int count);
}
