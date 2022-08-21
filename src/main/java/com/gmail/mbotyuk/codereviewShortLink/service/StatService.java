package com.gmail.mbotyuk.codereviewShortLink.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface StatService<T> {

    T add(T stat);

    T findByStat(String shortURL);

    Page<T> findAllWithPageable(Pageable pageable);

    List<T> findAllWithSort(Sort sort);
}
