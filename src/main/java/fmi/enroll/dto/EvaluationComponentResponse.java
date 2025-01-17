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
public class EvaluationComponentResponse {
    private String type;
    private List<String> evaluationCriteria;
    private List<String> evaluationMethods;
    private Integer weightInFinalGrade;
    private Integer minimumGrade;
    private String description;
}