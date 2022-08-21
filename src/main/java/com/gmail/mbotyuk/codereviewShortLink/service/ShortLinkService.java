package com.gmail.mbotyuk.codereviewShortLink.service;

public interface ShortLinkService<T> {

    T add(T shortLink);

    T findByOriginalLink(String originalURL);

    T findByShortLink(String shortURL);
}
