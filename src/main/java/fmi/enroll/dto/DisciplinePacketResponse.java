package fmi.enroll.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinePacketResponse {
    private String id;
    private String name;
    private String description;
    private Integer semester;
    private Integer yearOfStudy;
    private Integer targetYearOfStudy;
    private Integer targetSemester;
    private Integer maxChoices;
    private List<String> disciplines;
    private Integer totalCredits;
    private String category;
    private PrerequisitesResponse prerequisites;
}
