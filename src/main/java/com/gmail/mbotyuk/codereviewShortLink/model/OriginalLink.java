package com.gmail.mbotyuk.codereviewShortLink.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "original_link")
@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class OriginalLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "original", nullable = false, unique = true, length = 1000)
    @JsonProperty("original")
    @NonNull
    private String original;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OriginalLink that = (OriginalLink) o;

        return original.equals(that.original);
    }

    @Override
    public int hashCode() {
        return original.hashCode();
    }
}
