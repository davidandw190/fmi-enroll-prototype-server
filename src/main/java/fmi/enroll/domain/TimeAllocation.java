package fmi.enroll.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "time_allocations")
public class TimeAllocation extends BaseEntity {
    @Column(nullable = false)
    private Integer individualStudyHours;

    @Column(nullable = false)
    private Integer documentationHours;

    @Column(nullable = false)
    private Integer preparationHours;

    @Column(nullable = false)
    private Integer tutoringHours;

    @Column(nullable = false)
    private Integer examinationHours;

    @Column(nullable = false)
    private Integer otherActivitiesHours;

    @Column(nullable = false)
    private Integer totalSemesterHours;
}