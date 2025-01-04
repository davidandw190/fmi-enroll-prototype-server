package fmi.enroll.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnrollmentPeriodType {
    ELECTIVE_DISCIPLINES("ELECTIVE DISCIPLINES"),
    COMPLEMENTARY_DISCIPLINES("COMPLEMENTARY DISCIPLINES"),
    THESIS_REGISTRATION("THESIS REGISTRATION");

    private final String value;
}
