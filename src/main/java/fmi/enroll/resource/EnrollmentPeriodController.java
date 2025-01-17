package fmi.enroll.resource;

import fmi.enroll.dto.ElectivePacketsResponse;
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

    @GetMapping("/{periodId}/elective-disciplines")
    public ResponseEntity<EnrollmentPeriodResponse> getElectiveDisciplinesPeriod(
            @PathVariable Long periodId
    ) {
        log.info("Fetching elective disciplines period with id: {}", periodId);
        EnrollmentPeriodResponse period = enrollmentPeriodService.findElectiveDisciplinesPeriod(periodId);
        log.info("Found elective disciplines period: type={}, packets={}",
                period.getType(), period.getPackets().size());
        return ResponseEntity.ok(period);
    }

    @GetMapping("/{periodId}/packets")
    public ResponseEntity<ElectivePacketsResponse> getElectivePackets(
            @PathVariable Long periodId
    ) {
        log.info("Fetching packets with disciplines for period id: {}", periodId);
        ElectivePacketsResponse packetsResponse = enrollmentPeriodService.findElectivePackets(periodId);
        log.info("Found {} packets with their disciplines", packetsResponse.getPackets().size());
        return ResponseEntity.ok(packetsResponse);
    }
}