package fmi.enroll.dto;

import fmi.enroll.enums.enrollment.EnrollmentPeriodStatus;
import fmi.enroll.enums.enrollment.EnrollmentPeriodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentPeriodResponse {
    private String id;
    private EnrollmentPeriodType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer semester;
    private Integer yearOfStudy;
    private String academicYear;
    private Integer targetYearOfStudy;
    private Integer targetSemester;
    private Boolean isActive;
    private EnrollmentPeriodStatus status;
    private Integer progress;
    private List<DisciplinePacketResponse> packets;
    private List<String> targetSpecializations;
}