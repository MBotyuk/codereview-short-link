package com.gmail.mbotyuk.codereviewShortLink.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.mbotyuk.codereviewShortLink.model.OriginalLink;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class UrlControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenValidOriginalLinkReturnIsOkTest() throws Exception {
        OriginalLink originalLink = new OriginalLink("http://localhost:9090/");
        mockMvc.perform(post("/generate")
                        .content(objectMapper.writeValueAsString(originalLink))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("test123"));
    }

    @Test
    void givenOriginalLinkWithEmptyLinkReturnIsBadRequestTest() throws Exception {
        OriginalLink originalLink = new OriginalLink("");
        mockMvc.perform(post("/generate")
                        .content(objectMapper.writeValueAsString(originalLink))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidShortLinkReturnIsFoundTest() throws Exception {
        mockMvc.perform(get("/l/{shortUrl}", "test123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost:9090/"));
    }

    @Test
    void givenInvalidShortLinkReturnIsBadRequestTest() throws Exception {
        mockMvc.perform(get("/l/{shortUrl}", "test1231")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidShortLinkInStatsReturnIsOkTest() throws Exception {
        mockMvc.perform(get("/stats/{shortUrl}", "test123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("test123"))
                .andExpect(jsonPath("$.original").value("http://localhost:9090/"))
                .andExpect(jsonPath("$.rank").value(1))
                .andExpect(jsonPath("$.count").value(121));
    }

    @Test
    void givenInvalidShortLinkInStatsReturnIsBadRequestTest() throws Exception {
        mockMvc.perform(get("/stats/{shortUrl}", "test1231")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenParamsInStatsReturnIsOkTest() throws Exception {
        mockMvc.perform(get("/stats")
                        .param("1", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}