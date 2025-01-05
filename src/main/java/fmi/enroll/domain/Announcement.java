package fmi.enroll.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "announcements")
public class Announcement extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime date;

    // Using TEXT type for potentially long content
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Boolean important;
}
