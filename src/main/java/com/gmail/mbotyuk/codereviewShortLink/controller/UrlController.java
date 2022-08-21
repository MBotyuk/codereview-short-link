package com.gmail.mbotyuk.codereviewShortLink.controller;

import com.gmail.mbotyuk.codereviewShortLink.dto.ShortLinkDTO;
import com.gmail.mbotyuk.codereviewShortLink.dto.StatDTO;
import com.gmail.mbotyuk.codereviewShortLink.exception.FieldIsNotFilledCorrectlyException;
import com.gmail.mbotyuk.codereviewShortLink.exception.FindInDatabaseException;
import com.gmail.mbotyuk.codereviewShortLink.model.OriginalLink;
import com.gmail.mbotyuk.codereviewShortLink.service.UrlService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortLinkDTO> generate(@RequestBody OriginalLink originalLink) {
        log.debug("RequestBody OriginalLink = {}", originalLink);
        if (StringUtils.isBlank(originalLink.getOriginal())) {
            log.error("The field is not filled");
            throw new FieldIsNotFilledCorrectlyException("The field is not filled");
        }
        final ShortLinkDTO shortLinkDTO = urlService.generate(originalLink);
        log.debug("ResponseEntity ShortLinkDTO = {}", shortLinkDTO);
        return ResponseEntity.ok(shortLinkDTO);
    }

    @ExceptionHandler(FieldIsNotFilledCorrectlyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleFieldNotFilledException(FieldIsNotFilledCorrectlyException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @GetMapping(value = "/l/{shortUrl}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl) {
        log.debug("PathVariable shortUrl = {}", shortUrl);
        final String url = urlService.getOriginalUrl(shortUrl);
        log.debug("ResponseEntity url = {}", url);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    @GetMapping(value = "/stats/{shortUrl}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatDTO> stats(@PathVariable String shortUrl) {
        log.debug("PathVariable shortUrl = {}", shortUrl);
        final StatDTO statDTO = urlService.getStat(shortUrl);
        log.debug("ResponseEntity StatDTO = {}", statDTO);
        return ResponseEntity.ok(statDTO);
    }

    @ExceptionHandler(FindInDatabaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleFindByShortLinkException(FindInDatabaseException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StatDTO>> stats(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int count) {
        log.debug("Page = {}", page);
        log.debug("Count = {}", count);
        final List<StatDTO> statDTOList = urlService.getStats(page, count);
        log.debug("ResponseEntity StatDTO size = {}", statDTOList.size());
        return ResponseEntity.ok(statDTOList);
    }
}
