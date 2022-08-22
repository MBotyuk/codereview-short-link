package com.gmail.mbotyuk.codereviewShortLink.service;

import com.gmail.mbotyuk.codereviewShortLink.model.OriginalLink;
import com.gmail.mbotyuk.codereviewShortLink.model.ShortLink;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvertingOriginalLinkToShortLinkServiceTest {

    @Test
    void twoIdenticalOriginalLinkReturnVariousShortLinkTest() {
        ConvertingOriginalLinkToShortLinkService convertService = new ConvertingOriginalLinkToShortLinkService();
        OriginalLink originalLink = new OriginalLink("https://www.google.com/search?q=java+%D0%B8%D0%BC%D1%8F+%D0%BF%D0%BE%D1%82%D0%BE%D0%BA%D0%B0&rlz=1C5GCEM_enRU997RU997&oq=java+%D0%B8%D0%BC%D1%8F+%D0%BF%D0%BE%D1%82%D0%BE%D0%BA%D0%B0&aqs=chrome..69i57.353730j0j7&sourceid=chrome&ie=UTF-8");
        ShortLink s1 = convertService.convert(originalLink);
        ShortLink s2 = convertService.convert(originalLink);

        assertNotEquals(s1, s2);
    }
}