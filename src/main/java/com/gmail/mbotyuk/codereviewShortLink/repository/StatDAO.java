package com.gmail.mbotyuk.codereviewShortLink.repository;

import com.gmail.mbotyuk.codereviewShortLink.model.Stat;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatDAO extends JpaRepository<Stat, Long> {

    @Query(value = "SELECT s.id, s.original, s.short_link, s.count FROM stat s WHERE short_link = :short_url", nativeQuery = true)
    Stat findByStat(@Param("short_url") String shortURL);

    @NonNull Page<Stat> findAll(@NonNull Pageable pageable);

    @NonNull List<Stat> findAll(@NonNull Sort sort);
}
