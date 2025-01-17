package fmi.enroll.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "weekly_hours")
public class WeeklyHours extends BaseEntity {
    @Column(nullable = false)
    private Integer course;

    @Column(nullable = false)
    private Integer seminar;

    @Column(nullable = false)
    private Integer laboratory;

    @Column(nullable = false)
    private Integer project;

    @Column(nullable = false)
    private Integer total;
}
