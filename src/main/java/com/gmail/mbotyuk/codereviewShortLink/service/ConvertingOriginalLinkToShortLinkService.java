package com.gmail.mbotyuk.codereviewShortLink.service;

import com.gmail.mbotyuk.codereviewShortLink.model.OriginalLink;
import com.gmail.mbotyuk.codereviewShortLink.model.ShortLink;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ConvertingOriginalLinkToShortLinkService {

    private final static String[] CHARS = {"a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y",
            "z", "1", "2", "3", "4", "5", "6", "7", "8",
            "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public ShortLink convert(OriginalLink originalLink) {
        String shortLink = LocalDate.now().getYear() % 100 +
                CHARS[LocalDate.now().getMonthValue()] +
                CHARS[LocalDate.now().getDayOfMonth()] +
                CHARS[LocalDateTime.now().getHour()] +
                CHARS[LocalDateTime.now().getMinute()] +
                LocalDateTime.now().getNano() +
                Thread.currentThread().getName().substring(Thread.currentThread().getName().length() - 1);

        return new ShortLink(shortLink, originalLink);
    }
}
