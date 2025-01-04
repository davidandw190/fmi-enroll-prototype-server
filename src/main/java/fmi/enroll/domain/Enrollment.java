package fmi.enroll.domain;

import fmi.enroll.enums.enrollment.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "enrollments")
public class Enrollment extends BaseEntity {
    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String disciplineId;

    @Column(nullable = false)
    private String packetId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;

    @Column(nullable = false)
    private LocalDateTime enrollmentDate;

    @Column(nullable = false)
    private Integer preference;

    private Integer waitlistPosition;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "enrollment_id")
    private List<EnrollmentStatusChange> statusHistory;
}
