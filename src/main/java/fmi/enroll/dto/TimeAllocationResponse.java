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
public class TimeAllocationResponse {
    private Integer individualStudyHours;
    private Integer documentationHours;
    private Integer preparationHours;
    private Integer tutoringHours;
    private Integer examinationHours;
    private Integer otherActivitiesHours;
    private Integer totalSemesterHours;
}
