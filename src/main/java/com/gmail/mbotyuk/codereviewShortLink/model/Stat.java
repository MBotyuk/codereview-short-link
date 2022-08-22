package com.gmail.mbotyuk.codereviewShortLink.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stat")
@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Stat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "original", nullable = false, unique = true, length = 1000)
    @JsonProperty("original")
    @NonNull
    private String original;

    @Column(name = "short_link", nullable = false, unique = true)
    @JsonProperty("short_link")
    @NonNull
    private String shortLink;

    @Column(name = "count", nullable = false)
    @JsonProperty("count")
    @NonNull
    @Setter
    private Long count;
}
