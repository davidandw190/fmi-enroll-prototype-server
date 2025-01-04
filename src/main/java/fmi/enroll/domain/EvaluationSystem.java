package fmi.enroll.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "evaluation_systems")
public class EvaluationSystem extends BaseEntity {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "evaluation_system_id")
    private List<EvaluationComponent> components;

    @ElementCollection
    @CollectionTable(name = "minimum_requirements")
    @Column(name = "requirement")
    private List<String> minimumRequirements;

    @Column(columnDefinition = "TEXT")
    private String additionalNotes;

    @ElementCollection
    @CollectionTable(name = "makeup_exam_conditions")
    @Column(name = "condition")
    private List<String> makeupExamConditions;
}

