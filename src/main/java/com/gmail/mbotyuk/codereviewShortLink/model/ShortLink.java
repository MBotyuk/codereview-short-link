package com.gmail.mbotyuk.codereviewShortLink.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "short_link")
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class ShortLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;


    @Column(name = "link", nullable = false, unique = true)
    @JsonProperty("link")
    @NonNull
    private String link;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "original_link_id", referencedColumnName = "id")
    @JsonManagedReference
    @NonNull
    private OriginalLink originalLink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShortLink shortLink = (ShortLink) o;

        if (!link.equals(shortLink.link)) return false;
        return originalLink.getOriginal().equals(shortLink.originalLink.getOriginal());
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + originalLink.hashCode();
        return result;
    }
}
