package fmi.enroll.domain;

import fmi.enroll.enums.EnrollmentPeriodStatus;
import fmi.enroll.enums.EnrollmentPeriodType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "enrollment_periods")
public class EnrollmentPeriod extends BaseEntity {
    // The type of enrollment period determines the available options and rules
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentPeriodType type;

    // Time boundaries for the enrollment period
    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    // Academic context information
    @Column(nullable = false)
    private Integer semester;

    @Column(nullable = false)
    private Integer yearOfStudy;

    @Column(nullable = false)
    private String academicYear;

    @Column(nullable = false)
    private Integer targetYearOfStudy;

    @Column(nullable = false)
    private Integer targetSemester;

    // Status tracking
    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentPeriodStatus status;

    // Optional progress tracking for ongoing periods
    private Integer progress;

    // The available discipline packets for this period
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "enrollment_period_id")
    private List<DisciplinePacket> packets;

    // Target specializations that can participate in this enrollment
    @ElementCollection
    @CollectionTable(name = "enrollment_period_specializations")
    @Column(name = "specialization")
    private List<String> targetSpecializations;
}

