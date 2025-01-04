package fmi.enroll.domain;

import fmi.enroll.enums.TeachingActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "evaluation_components")
public class EvaluationComponent extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeachingActivityType type;

    @ElementCollection
    @CollectionTable(name = "evaluation_criteria")
    @Column(name = "criterion")
    private List<String> evaluationCriteria;

    @ElementCollection
    @CollectionTable(name = "evaluation_methods")
    @Column(name = "method")
    private List<String> evaluationMethods;

    @Column(nullable = false)
    private Integer weightInFinalGrade;

    private Integer minimumGrade;

    @Column(columnDefinition = "TEXT")
    private String description;
}
