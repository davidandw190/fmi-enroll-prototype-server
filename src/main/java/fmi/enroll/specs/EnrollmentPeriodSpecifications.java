package fmi.enroll.specs;

import fmi.enroll.domain.EnrollmentPeriod;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EnrollmentPeriodSpecifications {

    public Specification<EnrollmentPeriod> hasTargetYearOfStudy(Integer yearOfStudy) {
        return (root, query, cb) ->
                cb.equal(root.get("targetYearOfStudy"), yearOfStudy);
    }

    public Specification<EnrollmentPeriod> hasTargetSemester(Integer semester) {
        return (root, query, cb) ->
                cb.equal(root.get("targetSemester"), semester);
    }

    public Specification<EnrollmentPeriod> hasTargetSpecialization(String specialization) {
        return (root, query, cb) -> {
            Join<EnrollmentPeriod, String> specializationsJoin =
                    root.join("targetSpecializations", JoinType.LEFT);

            return cb.or(
                    cb.equal(specializationsJoin, specialization),
                    cb.equal(specializationsJoin, "All Specializations")
            );
        };
    }

    public Specification<EnrollmentPeriod> isNotEnded() {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("endDate"), LocalDateTime.now());
    }
}