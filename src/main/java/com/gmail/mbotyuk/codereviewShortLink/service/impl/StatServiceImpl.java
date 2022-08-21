package com.gmail.mbotyuk.codereviewShortLink.service.impl;

import com.gmail.mbotyuk.codereviewShortLink.model.Stat;
import com.gmail.mbotyuk.codereviewShortLink.repository.StatDAO;
import com.gmail.mbotyuk.codereviewShortLink.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService<Stat> {

    private final StatDAO statDAO;

    @Override
    public Stat add(Stat stat) {
        return statDAO.save(stat);
    }

    @Override
    public Stat findByStat(String shortURL) {
        return statDAO.findByStat(shortURL);
    }

    @Override
    public Page<Stat> findAllWithPageable(Pageable pageable) {
        return statDAO.findAll(pageable);
    }

    @Override
    public List<Stat> findAllWithSort(Sort sort) {
        return statDAO.findAll(sort);
    }
}
