package fmi.enroll.domain;

import fmi.enroll.enums.AssessmentType;
import fmi.enroll.enums.DisciplineType;
import fmi.enroll.enums.TeachingLanguage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "disciplines")
public class Discipline extends BaseEntity {
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DisciplineType type;

    @Column(nullable = false)
    private Integer semester;

    @Column(nullable = false)
    private Integer yearOfStudy;

    @Column(nullable = false)
    private Integer credits;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssessmentType assessmentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeachingLanguage language;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "discipline_id")
    private List<TeachingActivity> teachingActivities;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "time_allocation_id")
    private TimeAllocation timeAllocation;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weekly_hours_id")
    private WeeklyHours weeklyHours;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "discipline_id")
    private List<ContentModule> courseContent;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "discipline_id")
    private List<ContentModule> seminarContent;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "discipline_id")
    private List<ContentModule> laboratoryContent;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bibliography_id")
    private Bibliography bibliography;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "prerequisites_id")
    private Prerequisites prerequisites;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "discipline_id")
    private List<LearningOutcome> learningOutcomes;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "evaluation_system_id")
    private EvaluationSystem evaluationSystem;

    private String packetId;
    private Integer maxEnrollmentSpots;
    private Integer currentEnrollmentCount;
    private Integer waitlistLimit;
}

