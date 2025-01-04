package fmi.enroll.domain;

import fmi.enroll.enums.discipline.TeachingActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "teaching_activities")
public class TeachingActivity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeachingActivityType type;

    @Column(nullable = false)
    private Integer hoursPerWeek;

    @Column(nullable = false)
    private Integer totalHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ElementCollection
    @CollectionTable(name = "teaching_activity_methods")
    @Column(name = "method")
    private List<String> teachingMethods;

    @Embedded
    private TeachingConditions conditions;
}