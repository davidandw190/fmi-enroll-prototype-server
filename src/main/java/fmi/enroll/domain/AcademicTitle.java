package fmi.enroll.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AcademicTitle {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String abbreviation;
}
