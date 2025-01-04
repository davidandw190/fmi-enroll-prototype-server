package fmi.enroll.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Embeddable
@Getter
@Setter
public class TeachingConditions {
    @Column(nullable = false)
    private String location;

    @ElementCollection
    @CollectionTable(name = "teaching_condition_requirements")
    @Column(name = "requirement")
    private List<String> requirements;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "teaching_conditions_id")
    private List<TeachingPlatform> platforms;
}