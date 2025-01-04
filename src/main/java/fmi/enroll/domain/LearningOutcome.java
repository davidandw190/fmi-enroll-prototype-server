package fmi.enroll.domain;

import fmi.enroll.enums.LearningOutcomeCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "learning_outcomes")
public class LearningOutcome extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LearningOutcomeCategory category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "learning_outcome_details")
    @Column(name = "outcome")
    private List<String> outcomes;
}
