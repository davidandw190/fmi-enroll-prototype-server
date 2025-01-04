package fmi.enroll.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class RequiredDiscipline {
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;
}