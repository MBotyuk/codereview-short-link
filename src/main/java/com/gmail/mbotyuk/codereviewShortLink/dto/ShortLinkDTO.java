package com.gmail.mbotyuk.codereviewShortLink.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ShortLinkDTO {

    @NonNull
    private final String link;
}
