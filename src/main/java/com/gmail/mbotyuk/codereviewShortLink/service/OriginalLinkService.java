package com.gmail.mbotyuk.codereviewShortLink.service;

public interface OriginalLinkService<T> {

    T add(T originalLink);

    T findByOriginalLink(String originalURL);
}
