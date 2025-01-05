package fmi.enroll.dto;

import fmi.enroll.enums.enrollment.EnrollmentPeriodType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EnrollmentPeriodResponse {
    private Long id;
    private EnrollmentPeriodType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // when the disciplines will be studied
    private Integer yearOfStudy;
    private Integer semester;
    private String academicYear;

    // eligibility requirements
    private Integer targetYearOfStudy;
    private Integer targetSemester;

    private Boolean isActive;
    private String status;
    private Integer progress;
    private List<DisciplinePacketResponse> packets;
    private List<String> targetSpecializations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
