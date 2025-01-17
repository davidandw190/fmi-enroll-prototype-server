package fmi.enroll.domain;

import fmi.enroll.enums.discipline.BibliographyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bibliography_entries")
public class BibliographyEntry extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authors;

    @Column(name = "publication_year")
    private Integer publicationYear;

    private String isbn;

    private String url;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BibliographyType type;
}