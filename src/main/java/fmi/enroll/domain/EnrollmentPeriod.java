package fmi.enroll.domain;

import fmi.enroll.enums.enrollment.EnrollmentPeriodStatus;
import fmi.enroll.enums.enrollment.EnrollmentPeriodType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "enrollment_periods")
public class EnrollmentPeriod extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentPeriodType type;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

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

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentPeriodStatus status;

    private Integer progress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "enrollment_period_id")
    private List<DisciplinePacket> packets;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "enrollment_period_specializations",
            joinColumns = @JoinColumn(name = "enrollment_period_id")
    )
    @Column(name = "specialization")
    private Set<String> targetSpecializations = new HashSet<>();
}

