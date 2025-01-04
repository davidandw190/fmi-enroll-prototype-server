package fmi.enroll.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DisciplinePacketResponse {
    private String id;
    private String name;
    private String description;

    // when the disciplines will be studied
    private Integer yearOfStudy;
    private Integer semester;

    // eligibility requirements
    private Integer targetYearOfStudy;
    private Integer targetSemester;

    private Integer maxChoices;
    private List<String> disciplines;
    private Integer totalCredits;
    private String category;
    private PrerequisitesResponse prerequisites;
}
