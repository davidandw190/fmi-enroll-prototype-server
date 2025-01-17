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
public class TeachingActivityResponse {
    private String type;
    private Integer hoursPerWeek;
    private Integer totalHours;
    private TeacherResponse teacher;
    private List<String> teachingMethods;
    private TeachingConditionsResponse conditions;
}