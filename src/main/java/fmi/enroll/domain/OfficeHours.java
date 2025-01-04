package fmi.enroll.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "office_hours")
public class OfficeHours extends BaseEntity {
    @Column(nullable = false)
    private String dayOfWeek;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private String location;

    private Boolean online;

    private String meetingLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}