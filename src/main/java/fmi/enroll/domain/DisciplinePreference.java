package fmi.enroll.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "discipline_preferences")
public class DisciplinePreference extends BaseEntity {
    @Column(nullable = false)
    private String disciplineId;

    @Column(nullable = false)
    private Integer priority;

    @Column(columnDefinition = "TEXT")
    private String motivation;
}
