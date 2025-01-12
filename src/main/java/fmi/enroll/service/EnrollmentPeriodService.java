package fmi.enroll.service;

import java.util.List;
import java.util.stream.Collectors;

import fmi.enroll.domain.EnrollmentPeriod;
import fmi.enroll.dto.EnrollmentPeriodResponse;
import fmi.enroll.mappers.EnrollmentPeriodMapper;
import fmi.enroll.repository.EnrollmentPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentPeriodService {
    private static final Logger log = LoggerFactory.getLogger(EnrollmentPeriodService.class);

    private final EnrollmentPeriodRepository enrollmentPeriodRepository;
    private final EnrollmentPeriodMapper enrollmentPeriodMapper;

    public List<EnrollmentPeriodResponse> findEligibleEnrollmentPeriods(
            Integer targetYearOfStudy,
            Integer targetSemester,
            String targetSpecialization
    ) {
        List<EnrollmentPeriod> eligiblePeriods = enrollmentPeriodRepository.findEligibleEnrollmentPeriods(
                targetYearOfStudy,
                targetSemester,
                targetSpecialization
        );
        log.info("Found {} eligible periods for year={}, semester={}, specialization={}",
                eligiblePeriods.size(), targetYearOfStudy, targetSemester, targetSpecialization);

        eligiblePeriods.forEach(period -> {
            log.info("Period details: id={}, type={}, specializations={}",
                    period.getId(),
                    period.getType(),
                    period.getTargetSpecializations()
            );
        });

        List<EnrollmentPeriodResponse> responses = eligiblePeriods.stream()
                .map(enrollmentPeriodMapper::toResponse)
                .collect(Collectors.toList());

        log.info("Returning {} response DTOs", responses.size());
        return responses;
    }
}