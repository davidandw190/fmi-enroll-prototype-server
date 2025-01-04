package fmi.enroll.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "teaching_platforms")
public class TeachingPlatform extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String url;

    private String details;

    @Column(nullable = false)
    private Boolean required;
}