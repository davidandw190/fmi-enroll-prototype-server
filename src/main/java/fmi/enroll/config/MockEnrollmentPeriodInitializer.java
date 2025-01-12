package fmi.enroll.config;

import fmi.enroll.domain.DisciplinePacket;
import fmi.enroll.domain.EnrollmentPeriod;
import fmi.enroll.domain.Prerequisites;
import fmi.enroll.enums.enrollment.EnrollmentPeriodStatus;
import fmi.enroll.enums.enrollment.EnrollmentPeriodType;
import fmi.enroll.repository.EnrollmentPeriodRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Profile("!prod")
@RequiredArgsConstructor
public class MockEnrollmentPeriodInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(MockEnrollmentPeriodInitializer.class);

    private final EnrollmentPeriodRepository enrollmentPeriodRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        if (enrollmentPeriodRepository.count() == 0) {
            log.info("Starting enrollment period initialization...");
            try {
                // elective disciplines period
                createAndSaveEnrollmentPeriod(
                        EnrollmentPeriodType.ELECTIVE_DISCIPLINES,
                        3,
                        2,
                        3,
                        1,
                        Set.of("Computer Science", "Informatics"),
                        LocalDateTime.of(2025, 1, 1, 0, 0),
                        LocalDateTime.of(2025, 2, 1, 23, 59),
                        "2024-2025",
                        45,
                        createElectivePackets()
                );

                // complementary disciplines period
                createAndSaveEnrollmentPeriod(
                        EnrollmentPeriodType.COMPLEMENTARY_DISCIPLINES,
                        3,
                        2,
                        3,
                        1,
                        Set.of("All Specializations"),
                        LocalDateTime.of(2025, 2, 2, 0, 0),
                        LocalDateTime.of(2025, 2, 15, 23, 59),
                        "2024-2025",
                        null,
                        createComplementaryPackets()
                );

                // thesis registration period
                createAndSaveEnrollmentPeriod(
                        EnrollmentPeriodType.THESIS_REGISTRATION,
                        3,
                        2,
                        3,
                        1,
                        Set.of("Computer Science", "Informatics"),
                        LocalDateTime.of(2024, 12, 1, 0, 0),
                        LocalDateTime.of(2024, 12, 15, 23, 59),
                        "2024-2025",
                        100,
                        createThesisPackets()
                );

                entityManager.flush();
                log.info("Completed enrollment period initialization");
                verifyInitializedData();
            } catch (Exception e) {
                log.error("Failed to initialize enrollment periods", e);
                throw new RuntimeException("Enrollment period initialization failed", e);
            }
        } else {
            log.info("Enrollment periods already exist, skipping initialization");
            verifyInitializedData();
        }
    }

    private void createAndSaveEnrollmentPeriod(
            EnrollmentPeriodType type,
            int yearOfStudy,
            int semester,
            int targetYearOfStudy,
            int targetSemester,
            Set<String> specializations,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String academicYear,
            Integer progress,
            List<DisciplinePacket> packets
    ) {
        EnrollmentPeriod period = new EnrollmentPeriod();
        period.setType(type);
        period.setYearOfStudy(yearOfStudy);
        period.setSemester(semester);
        period.setTargetYearOfStudy(targetYearOfStudy);
        period.setTargetSemester(targetSemester);
        period.setStartDate(startDate);
        period.setEndDate(endDate);
        period.setAcademicYear(academicYear);
        period.setProgress(progress);
        period.setIsActive(isActive(startDate, endDate));
        period.setStatus(determineStatus(startDate, endDate));
        period.getTargetSpecializations().addAll(specializations);
        period.setPackets(packets);

        EnrollmentPeriod saved = enrollmentPeriodRepository.save(period);
        log.info("Created enrollment period: type={}, specializations={}",
                type, saved.getTargetSpecializations());
    }

    private List<DisciplinePacket> createElectivePackets() {
        DisciplinePacket packet1 = createPacket(
                "Advanced Development Techniques",
                "Modern frameworks and advanced programming concepts",
                2,
                Arrays.asList("1", "2", "3"),
                createPrerequisites(
                        Arrays.asList("Basic programming", "JavaScript fundamentals"),
                        Arrays.asList("Previous web development experience")
                )
        );

        DisciplinePacket packet2 = createPacket(
                "Cloud and DevOps",
                "Cloud platforms and DevOps practices",
                1,
                Arrays.asList("4", "5"),
                createPrerequisites(
                        Arrays.asList("Networking basics", "Operating Systems"),
                        List.of("Cloud computing knowledge")
                )
        );

        return Arrays.asList(packet1, packet2);
    }

    private List<DisciplinePacket> createComplementaryPackets() {
        DisciplinePacket packet = createPacket(
                "Humanities and Social Sciences",
                "Broaden your perspective with interdisciplinary courses",
                2,
                Arrays.asList("comp1", "comp2", "comp3"),
                createPrerequisites(
                        List.of("None required"),
                        List.of("Interest in interdisciplinary studies")
                )
        );

        return Collections.singletonList(packet);
    }

    private List<DisciplinePacket> createThesisPackets() {
        DisciplinePacket packet = createPacket(
                "Software Engineering Research Topics",
                "Research projects in software engineering",
                1,
                Arrays.asList("thesis1", "thesis2", "thesis3"),
                createPrerequisites(
                        Arrays.asList("Strong programming background", "Research methodology"),
                        List.of("Previous research experience")
                )
        );

        return Collections.singletonList(packet);
    }

    private DisciplinePacket createPacket(
            String name,
            String description,
            Integer maxChoices,
            List<String> disciplines,
            Prerequisites prerequisites
    ) {
        DisciplinePacket packet = new DisciplinePacket();
        packet.setName(name);
        packet.setDescription(description);
        packet.setSemester(2);
        packet.setYearOfStudy(3);
        packet.setTargetYearOfStudy(3);
        packet.setTargetSemester(1);
        packet.setMaxChoices(maxChoices);
        packet.setDisciplines(disciplines);
        packet.setTotalCredits(maxChoices * 2);
        packet.setPrerequisites(prerequisites);
        return packet;
    }

    private Prerequisites createPrerequisites(List<String> skills, List<String> recommendations) {
        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(skills);
        prerequisites.setRecommendations(recommendations);
        return prerequisites;
    }

    private EnrollmentPeriodStatus determineStatus(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startDate)) return EnrollmentPeriodStatus.UPCOMING;
        if (now.isAfter(endDate)) return EnrollmentPeriodStatus.ENDED;
        return EnrollmentPeriodStatus.ACTIVE;
    }

    private boolean isActive(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(startDate) && !now.isAfter(endDate);
    }

    private void verifyInitializedData() {
        log.info("Verifying initialized enrollment periods...");
        enrollmentPeriodRepository.findAll().forEach(period -> {
            log.info("Verified period: type={}, year={}, semester={}, specializations={}, packets={}",
                    period.getType(),
                    period.getTargetYearOfStudy(),
                    period.getTargetSemester(),
                    period.getTargetSpecializations(),
                    period.getPackets().size()
            );
        });
    }
}