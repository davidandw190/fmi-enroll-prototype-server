package fmi.enroll.domain;

import fmi.enroll.enums.BibliographyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bibliography_entries")
public class BibliographyEntry extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authors;

    private Integer year;

    private String isbn;

    private String url;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BibliographyType type;
}