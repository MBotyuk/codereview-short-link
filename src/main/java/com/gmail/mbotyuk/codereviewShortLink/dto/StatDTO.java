package com.gmail.mbotyuk.codereviewShortLink.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class StatDTO {

    @NonNull
    private final String link;

    @NonNull
    private final String original;

    private final int rank;

    private final long count;
}
