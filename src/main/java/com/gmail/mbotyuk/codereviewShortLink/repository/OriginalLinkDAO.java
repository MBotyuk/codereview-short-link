package com.gmail.mbotyuk.codereviewShortLink.repository;

import com.gmail.mbotyuk.codereviewShortLink.model.OriginalLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OriginalLinkDAO extends JpaRepository<OriginalLink, Long> {

    @Query(value = "SELECT * FROM original_link WHERE original = :original_url", nativeQuery = true)
    OriginalLink findByOriginalLink(@Param("original_url") String originalURL);
}
