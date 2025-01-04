package fmi.enroll.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "discipline_packets")
public class DisciplinePacket extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer semester;

    @Column(nullable = false)
    private Integer yearOfStudy;

    @Column(nullable = false)
    private Integer targetYearOfStudy;

    @Column(nullable = false)
    private Integer targetSemester;

    @Column(nullable = false)
    private Integer maxChoices;

    @ElementCollection
    @CollectionTable(name = "packet_disciplines")
    @Column(name = "discipline_id")
    private List<String> disciplines;

    @Column(nullable = false)
    private Integer totalCredits;

    private String category;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "prerequisites_id")
    private Prerequisites prerequisites;
}

