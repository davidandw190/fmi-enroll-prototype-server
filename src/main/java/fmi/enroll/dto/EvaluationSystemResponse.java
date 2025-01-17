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
public class EvaluationSystemResponse {
    private List<EvaluationComponentResponse> components;
    private List<String> minimumRequirements;
    private String additionalNotes;
    private List<String> makeupExamConditions;
}