package fmi.enroll.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyHoursResponse {
    private Integer course;
    private Integer seminar;
    private Integer laboratory;
    private Integer project;
    private Integer total;
}