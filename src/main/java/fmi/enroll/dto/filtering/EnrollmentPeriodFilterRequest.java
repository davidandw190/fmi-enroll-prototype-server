package fmi.enroll.dto.filtering;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class EnrollmentPeriodFilterRequest {
    @NotNull(message = "Target year of study is required")
    private Integer targetYearOfStudy;

    @NotNull(message = "Target semester is required")
    @Range(min = 1, max = 2, message = "Semester must be either 1 or 2")
    private Integer targetSemester;

    @NotNull(message = "Specialization is required")
    private String specialization;
}
