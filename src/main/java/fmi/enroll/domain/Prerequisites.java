package fmi.enroll.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "prerequisites")
public class Prerequisites extends BaseEntity {
    @ElementCollection
    @CollectionTable(name = "required_disciplines")
    private List<RequiredDiscipline> requiredDisciplines;

    @ElementCollection
    @CollectionTable(name = "required_skills")
    @Column(name = "skill")
    private List<String> requiredSkills;

    @ElementCollection
    @CollectionTable(name = "recommendations")
    @Column(name = "recommendation")
    private List<String> recommendations;
}

