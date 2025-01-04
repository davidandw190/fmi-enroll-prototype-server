package fmi.enroll.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "content_modules")
public class ContentModule extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "content_module_teaching_methods")
    @Column(name = "method")
    private List<String> teachingMethods;

    @Column(nullable = false)
    private Integer hours;

    private Integer weekNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "content_module_id")
    private List<BibliographyEntry> references;
}
