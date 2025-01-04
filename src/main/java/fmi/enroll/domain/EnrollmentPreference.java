package fmi.enroll.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "enrollment_preferences")
public class EnrollmentPreference extends BaseEntity {
    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String packetId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "enrollment_preference_id")
    private List<DisciplinePreference> preferences;

    @Column(nullable = false)
    private LocalDateTime submittedAt;
}
