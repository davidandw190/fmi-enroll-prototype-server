package fmi.enroll.resource;

import fmi.enroll.dto.EnrollmentPeriodResponse;
import fmi.enroll.service.EnrollmentPeriodService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/enrollment-periods")
@RequiredArgsConstructor
public class EnrollmentPeriodController {
    private static final Logger log = LoggerFactory.getLogger(EnrollmentPeriodController.class);
    private final EnrollmentPeriodService enrollmentPeriodService;

    @GetMapping
    public ResponseEntity<List<EnrollmentPeriodResponse>> getEligibleEnrollmentPeriods(
            @RequestParam Integer targetYearOfStudy,
            @RequestParam Integer targetSemester,
            @RequestParam(required = false) String specialization
    ) {
        log.info("Received request for periods: year={}, semester={}, specialization={}",
                targetYearOfStudy, targetSemester, specialization);

        List<EnrollmentPeriodResponse> periods = enrollmentPeriodService
                .findEligibleEnrollmentPeriods(targetYearOfStudy, targetSemester, specialization);

        log.info("Preparing response with {} periods", periods.size());
        return ResponseEntity.ok(periods);
    }
}