package fmi.enroll.domain;

import fmi.enroll.enums.discipline.ContentModuleType;
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
    private List<BibliographyEntry> bibliographyEntries;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentModuleType type;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

}
