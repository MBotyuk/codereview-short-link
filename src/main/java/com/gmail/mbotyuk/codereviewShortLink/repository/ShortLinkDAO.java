package com.gmail.mbotyuk.codereviewShortLink.repository;

import com.gmail.mbotyuk.codereviewShortLink.model.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShortLinkDAO extends JpaRepository<ShortLink, Long> {

    @Query(value = "SELECT sl.id, sl.link, sl.original_link_id FROM short_link sl JOIN original_link ol on sl.original_link_id = ol.id WHERE original = :original_url", nativeQuery = true)
    ShortLink findByOriginalLink(@Param("original_url") String originalURL);

    @Query(value = "SELECT sl.id, sl.link, sl.original_link_id FROM short_link sl JOIN original_link ol on sl.original_link_id = ol.id WHERE link = :short_url", nativeQuery = true)
    ShortLink findByShortLink(@Param("short_url") String shortURL);
}
