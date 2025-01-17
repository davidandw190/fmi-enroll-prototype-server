package fmi.enroll.service;

import java.util.List;
import java.util.stream.Collectors;

import fmi.enroll.domain.Discipline;
import fmi.enroll.domain.DisciplinePacket;
import fmi.enroll.domain.EnrollmentPeriod;
import fmi.enroll.dto.DisciplineResponse;
import fmi.enroll.dto.ElectivePacketsResponse;
import fmi.enroll.dto.EnrollmentPeriodResponse;
import fmi.enroll.dto.PacketWithDisciplinesResponse;
import fmi.enroll.exception.ResourceNotFoundException;
import fmi.enroll.mappers.EnrollmentPeriodMapper;
import fmi.enroll.mappers.PacketWithDisciplinesMapper;
import fmi.enroll.repository.DisciplineRepository;
import fmi.enroll.repository.EnrollmentPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentPeriodService {
    private static final Logger log = LoggerFactory.getLogger(EnrollmentPeriodService.class);

    private final EnrollmentPeriodRepository enrollmentPeriodRepository;
    private final EnrollmentPeriodMapper enrollmentPeriodMapper;
    private final DisciplineService disciplineService;

    @Qualifier("packetWithDisciplinesMapperImpl")
    private final PacketWithDisciplinesMapper packetMapper;

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

    public EnrollmentPeriodResponse findElectiveDisciplinesPeriod(Long periodId) {
        EnrollmentPeriod period = enrollmentPeriodRepository.findElectiveDisciplinesPeriod(periodId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Elective disciplines period not found with id: %d", periodId)
                ));

        log.info("Found elective period: id={}, type={}, specializations={}",
                period.getId(), period.getType(), period.getTargetSpecializations());

        return enrollmentPeriodMapper.toResponse(period);
    }

    public ElectivePacketsResponse findElectivePackets(Long periodId) {
        EnrollmentPeriod enrollmentPeriod = enrollmentPeriodRepository
                .findPacketsWithDisciplines(periodId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Elective disciplines period not found with id: %d", periodId)
                ));

        List<PacketWithDisciplinesResponse> packetResponses = enrollmentPeriod.getPackets().stream()
                .map(packet -> {
                    List<Long> disciplineIds = packet.getDisciplines().stream()
                            .filter(id -> id.matches("\\d+"))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());

                    List<DisciplineResponse> disciplineResponses =
                            disciplineService.findDisciplinesWithDetails(disciplineIds);

                    return packetMapper.mapToResponse(packet, disciplineResponses);
                })
                .collect(Collectors.toList());

        return new ElectivePacketsResponse(packetResponses);
    }
}