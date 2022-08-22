package com.gmail.mbotyuk.codereviewShortLink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CodereviewShortLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodereviewShortLinkApplication.class, args);
    }

}
