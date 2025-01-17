package fmi.enroll.domain;

import fmi.enroll.enums.discipline.AssessmentType;
import fmi.enroll.enums.discipline.DisciplineType;
import fmi.enroll.enums.discipline.TeachingLanguage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @OrderColumn(name = "teaching_activity_order")
    private List<TeachingActivity> teachingActivities = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "time_allocation_id")
    private TimeAllocation timeAllocation;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weekly_hours_id")
    private WeeklyHours weeklyHours;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentModule> courseContent = new ArrayList<>();

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentModule> seminarContent = new ArrayList<>();

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentModule> laboratoryContent = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bibliography_id")
    private Bibliography bibliography;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "prerequisites_id")
    private Prerequisites prerequisites;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, fetch = FetchType.EAGER,  orphanRemoval = true)
    private List<LearningOutcome> learningOutcomes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "evaluation_system_id")
    private EvaluationSystem evaluationSystem;

    private String packetId;
    private Integer maxEnrollmentSpots;
    private Integer currentEnrollmentCount;
    private Integer waitlistLimit;

}

