package fmi.enroll.enums.enrollment;

import lombok.Getter;

@Getter
public enum EnrollmentPeriodType {
    ELECTIVE_DISCIPLINES("ELECTIVE DISCIPLINES"),
    COMPLEMENTARY_DISCIPLINES("COMPLEMENTARY DISCIPLINES"),
    THESIS_REGISTRATION("THESIS REGISTRATION");

    private final String value;

    private EnrollmentPeriodType(String value) {
        this.value = value;
    }
}