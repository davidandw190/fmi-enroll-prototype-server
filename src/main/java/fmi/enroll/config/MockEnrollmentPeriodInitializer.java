package fmi.enroll.config;

import fmi.enroll.domain.*;
import fmi.enroll.enums.discipline.*;
import fmi.enroll.enums.enrollment.EnrollmentPeriodStatus;
import fmi.enroll.enums.enrollment.EnrollmentPeriodType;
import fmi.enroll.repository.DisciplineRepository;
import fmi.enroll.repository.EnrollmentPeriodRepository;
import fmi.enroll.repository.TeacherRepository;
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
    private final TeacherRepository teacherRepository;
    private final EntityManager entityManager;
    private final DisciplineRepository disciplineRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (enrollmentPeriodRepository.count() == 0) {
            log.info("Starting enrollment period initialization...");
            try {
                List<Teacher> teachers = createAndSaveTeachers();
                log.info("Created {} mock teachers", teachers.size());

                Discipline distributedSystems = createAndSaveDistributedSystemsDiscipline(teachers.get(0));
                Discipline softwareArchitecture = createAndSaveSoftwareArchitectureDiscipline(teachers.get(1));
                Discipline progressiveWebApps = createAndSaveProgressiveWebAppsDiscipline(teachers.get(2));
                Discipline devopsAndStuff = createAndSaveDevopsAndStuffDiscipline(teachers.get(1));
                Discipline anotherExample = createAndSaveAnotherExampleDiscipline(teachers.get(0));
                Discipline example1 = createAndSaveExample1Discipline(teachers.get(2));
                Discipline example2 = createAndSaveExample2Discipline(teachers.get(0));
                Discipline example3 = createAndSaveExample3Discipline(teachers.get(2));
                Discipline example4 = createAndSaveExample4Discipline(teachers.get(1));



                createAndSaveEnrollmentPeriod(
                        createElectivePeriod(teachers),
                        createElectivePackets(distributedSystems.getId().toString(),
                                softwareArchitecture.getId().toString(),
                                progressiveWebApps.getId().toString(),
                                devopsAndStuff.getId().toString(),
                                anotherExample.getId().toString(),
                                example1.getId().toString(),
                                example2.getId().toString(),
                                example3.getId().toString(),
                                example4.getId().toString()
                        )
                );

                createAndSaveEnrollmentPeriod(
                        createComplementaryPeriod(),
                        createComplementaryPackets()
                );

                createAndSaveEnrollmentPeriod(
                        createThesisPeriod(),
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

    private Discipline createAndSaveSoftwareArchitectureDiscipline(Teacher teacher) {
        Discipline discipline = createSoftwareArchitectureDiscipline(teacher);
        return disciplineRepository.save(discipline);
    }

    private Discipline createAndSaveProgressiveWebAppsDiscipline(Teacher teacher) {
        Discipline discipline = createProgressiveWebAppsDiscipline(teacher);
        return disciplineRepository.save(discipline);
    }

    private Discipline createAndSaveDevopsAndStuffDiscipline(Teacher teacher) {
        Discipline discipline = createDevopsAndStuffDiscipline(teacher);
        return disciplineRepository.save(discipline);
    }

    private Discipline createAndSaveAnotherExampleDiscipline(Teacher teacher) {
        Discipline discipline = createAnotherExampleDiscipline(teacher);
        return disciplineRepository.save(discipline);
    }

    private Discipline createAndSaveExample1Discipline(Teacher teacher) {
        Discipline discipline = createExample1Discipline(teacher);
        return disciplineRepository.save(discipline);
    }


    private Discipline createAndSaveExample2Discipline(Teacher teacher) {
        Discipline discipline = createExample2Discipline(teacher);
        return disciplineRepository.save(discipline);
    }

    private Discipline createAndSaveExample3Discipline(Teacher teacher) {
        Discipline discipline = createExample3Discipline(teacher);
        return disciplineRepository.save(discipline);
    }

    private Discipline createAndSaveExample4Discipline(Teacher teacher) {
        Discipline discipline = createExample4Discipline(teacher);
        return disciplineRepository.save(discipline);
    }


    private Discipline createAndSaveDistributedSystemsDiscipline(Teacher teacher) {
        Discipline discipline = createDistributedSystemsDiscipline(teacher);

        Discipline savedDiscipline = disciplineRepository.save(discipline);
        log.info("Saved discipline: {} with ID: {}", savedDiscipline.getName(), savedDiscipline.getId());

        return savedDiscipline;
    }

    private Discipline createDistributedSystemsDiscipline(Teacher teacher) {
        Discipline discipline = new Discipline();
        discipline.setCode("IE420");
        discipline.setName("Distributed Systems");
        discipline.setDescription("Learn modern web development techniques and frameworks, distributed systems architecture, and scalable application development. Focus on practical implementation of distributed systems concepts.");
        discipline.setType(DisciplineType.OPTIONAL);
        discipline.setSemester(1);
        discipline.setYearOfStudy(3);
        discipline.setCredits(2);
        discipline.setAssessmentType(AssessmentType.EXAM);
        discipline.setLanguage(TeachingLanguage.EN);
        discipline.setMaxEnrollmentSpots(120);
        discipline.setCurrentEnrollmentCount(65);
        discipline.setWaitlistLimit(10);

        List<TeachingActivity> activities = createTeachingActivities(teacher, discipline);
        activities.forEach(activity -> {
            activity.setDiscipline(discipline);

            TeachingConditions conditions = new TeachingConditions();
            if (activity.getType() == TeachingActivityType.COURSE) {
                conditions.setLocation("Room A03");
                conditions.setRequirements(Arrays.asList(
                        "Laptop required",
                        "Basic programming environment setup"
                ));
            } else {
                conditions.setLocation("Lab L01");
                conditions.setRequirements(Arrays.asList(
                        "Development environment setup",
                        "Git installed",
                        "Docker installed"
                ));
            }

            conditions.setPlatforms(new ArrayList<>());
            activity.setConditions(conditions);
        });
        discipline.setTeachingActivities(activities);

        WeeklyHours weeklyHours = new WeeklyHours();
        weeklyHours.setCourse(2);
        weeklyHours.setLaboratory(2);
        weeklyHours.setSeminar(0);
        weeklyHours.setProject(0);
        weeklyHours.setTotal(4);
        discipline.setWeeklyHours(weeklyHours);

        TimeAllocation timeAllocation = new TimeAllocation();
        timeAllocation.setIndividualStudyHours(28);
        timeAllocation.setDocumentationHours(14);
        timeAllocation.setPreparationHours(14);
        timeAllocation.setTutoringHours(7);
        timeAllocation.setExaminationHours(4);
        timeAllocation.setOtherActivitiesHours(5);
        timeAllocation.setTotalSemesterHours(100);
        discipline.setTimeAllocation(timeAllocation);

        List<ContentModule> courseContent = createCourseContent(discipline);
        courseContent.forEach(content -> {
            content.setDiscipline(discipline);
            content.setType(ContentModuleType.COURSE);
            content.setBibliographyEntries(new ArrayList<>());
        });
        discipline.setCourseContent(courseContent);

        List<ContentModule> labContent = createLabContent(discipline);
        labContent.forEach(content -> {
            content.setDiscipline(discipline);
            content.setType(ContentModuleType.LABORATORY);
            content.setBibliographyEntries(new ArrayList<>()); // Initialize empty list
        });
        discipline.setLaboratoryContent(labContent);

        Bibliography bibliography = new Bibliography();

        List<BibliographyEntry> requiredEntries = new ArrayList<>();
        BibliographyEntry modernWeb = new BibliographyEntry();
        modernWeb.setTitle("Modern Web Development");
        modernWeb.setAuthors("John Doe");
        modernWeb.setPublicationYear(2023);
        modernWeb.setType(BibliographyType.BOOK);
        modernWeb.setIsbn("978-0-123456-78-9");
        requiredEntries.add(modernWeb);

        BibliographyEntry distributedSystems = new BibliographyEntry();
        distributedSystems.setTitle("Distributed Systems");
        distributedSystems.setAuthors("Andrew S. Tanenbaum");
        distributedSystems.setPublicationYear(2023);
        distributedSystems.setType(BibliographyType.BOOK);
        distributedSystems.setIsbn("978-0-135678-91-2");
        requiredEntries.add(distributedSystems);

        List<BibliographyEntry> recommendedEntries = new ArrayList<>();
        BibliographyEntry reactPatterns = new BibliographyEntry();
        reactPatterns.setTitle("React Design Patterns");
        reactPatterns.setAuthors("Jane Smith");
        reactPatterns.setPublicationYear(2023);
        reactPatterns.setType(BibliographyType.BOOK);
        recommendedEntries.add(reactPatterns);

        List<BibliographyEntry> onlineEntries = new ArrayList<>();
        BibliographyEntry reactDocs = new BibliographyEntry();
        reactDocs.setTitle("React Documentation");
        reactDocs.setAuthors("React Team");
        reactDocs.setType(BibliographyType.ONLINE);
        reactDocs.setUrl("https://react.dev");
        onlineEntries.add(reactDocs);

        bibliography.setRequired(requiredEntries);
        bibliography.setRecommended(recommendedEntries);
        bibliography.setOnline(onlineEntries);

        discipline.setBibliography(bibliography);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(Arrays.asList(
                "Basic programming knowledge",
                "Understanding of web protocols",
                "JavaScript fundamentals"
        ));
        prerequisites.setRecommendations(Arrays.asList(
                "Previous web development experience",
                "Basic understanding of distributed systems"
        ));
        discipline.setPrerequisites(prerequisites);

        List<LearningOutcome> outcomes = createLearningOutcomes();
        outcomes.forEach(outcome -> outcome.setDiscipline(discipline));
        discipline.setLearningOutcomes(outcomes);

        EvaluationSystem evaluationSystem = createEvaluationSystem();
        discipline.setEvaluationSystem(evaluationSystem);

        return discipline;
    }

    private Discipline createSoftwareArchitectureDiscipline(Teacher teacher) {
        Discipline discipline = new Discipline();
        discipline.setCode("IE864");
        discipline.setName("Software Architecture and Design Patterns");
        discipline.setDescription("Master modern software architecture principles, design patterns, and architectural patterns. Focus on creating scalable, maintainable, and flexible software systems through practical implementation of enterprise patterns.");
        discipline.setType(DisciplineType.OPTIONAL);
        discipline.setSemester(1);
        discipline.setYearOfStudy(3);
        discipline.setCredits(2);
        discipline.setAssessmentType(AssessmentType.COLLOQUIUM);
        discipline.setLanguage(TeachingLanguage.EN);
        discipline.setMaxEnrollmentSpots(120);
        discipline.setCurrentEnrollmentCount(97);
        discipline.setWaitlistLimit(10);

        List<TeachingActivity> activities = createTeachingActivities(teacher, discipline);
        discipline.setTeachingActivities(activities);

        WeeklyHours weeklyHours = new WeeklyHours();
        weeklyHours.setCourse(2);
        weeklyHours.setLaboratory(2);
        weeklyHours.setSeminar(0);
        weeklyHours.setProject(0);
        weeklyHours.setTotal(4);
        discipline.setWeeklyHours(weeklyHours);

        TimeAllocation timeAllocation = new TimeAllocation();
        timeAllocation.setIndividualStudyHours(30);
        timeAllocation.setDocumentationHours(20);
        timeAllocation.setPreparationHours(20);
        timeAllocation.setTutoringHours(8);
        timeAllocation.setExaminationHours(4);
        timeAllocation.setOtherActivitiesHours(6);
        timeAllocation.setTotalSemesterHours(100);
        discipline.setTimeAllocation(timeAllocation);

        List<ContentModule> courseContent = createCourseContent(discipline);
        discipline.setCourseContent(courseContent);

        List<ContentModule> labContent = createLabContent(discipline);
        discipline.setLaboratoryContent(labContent);

        discipline.setCourseContent(courseContent);

        Bibliography bibliography = new Bibliography();

        List<BibliographyEntry> requiredEntries = new ArrayList<>();

        BibliographyEntry cleanArchitecture = new BibliographyEntry();
        cleanArchitecture.setTitle("Clean Architecture: A Craftsman's Guide to Software Structure and Design");
        cleanArchitecture.setAuthors("Robert C. Martin");
        cleanArchitecture.setPublicationYear(2023);
        cleanArchitecture.setType(BibliographyType.BOOK);
        cleanArchitecture.setIsbn("978-0-134-49416-6");
        requiredEntries.add(cleanArchitecture);

        BibliographyEntry patternEnterprise = new BibliographyEntry();
        patternEnterprise.setTitle("Patterns of Enterprise Application Architecture");
        patternEnterprise.setAuthors("Martin Fowler");
        patternEnterprise.setPublicationYear(2023);
        patternEnterprise.setType(BibliographyType.BOOK);
        patternEnterprise.setIsbn("978-0-321-12742-6");
        requiredEntries.add(patternEnterprise);


        List<BibliographyEntry> recommendedEntries = new ArrayList<>();

        BibliographyEntry designPatterns = new BibliographyEntry();
        designPatterns.setTitle("Design Patterns: Elements of Reusable Object-Oriented Software");
        designPatterns.setAuthors("Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides");
        designPatterns.setPublicationYear(2024);
        designPatterns.setType(BibliographyType.BOOK);
        recommendedEntries.add(designPatterns);

        BibliographyEntry domainDriven = new BibliographyEntry();
        domainDriven.setTitle("Domain-Driven Design: Tackling Complexity in the Heart of Software");
        domainDriven.setAuthors("Eric Evans");
        domainDriven.setPublicationYear(2023);
        domainDriven.setType(BibliographyType.BOOK);
        recommendedEntries.add(domainDriven);

        List<BibliographyEntry> onlineEntries = new ArrayList<>();

        BibliographyEntry martinFowler = new BibliographyEntry();
        martinFowler.setTitle("Martin Fowler's Architecture Guide");
        martinFowler.setAuthors("Martin Fowler");
        martinFowler.setType(BibliographyType.ONLINE);
        martinFowler.setUrl("https://martinfowler.com/architecture/");
        onlineEntries.add(martinFowler);

        BibliographyEntry refactoring = new BibliographyEntry();
        refactoring.setTitle("Refactoring Guru - Design Patterns");
        refactoring.setAuthors("Alexander Shvets");
        refactoring.setType(BibliographyType.ONLINE);
        refactoring.setUrl("https://refactoring.guru/design-patterns");
        onlineEntries.add(refactoring);

        bibliography.setRequired(requiredEntries);
        bibliography.setRecommended(recommendedEntries);
        bibliography.setOnline(onlineEntries);

        discipline.setBibliography(bibliography);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(Arrays.asList(
                "Basic programming knowledge",
                "Understanding of web protocols",
                "JavaScript fundamentals"
        ));
        prerequisites.setRecommendations(Arrays.asList(
                "Previous web development experience",
                "Basic understanding of distributed systems"
        ));
        discipline.setPrerequisites(prerequisites);

        List<LearningOutcome> outcomes = createLearningOutcomes();
        outcomes.forEach(outcome -> outcome.setDiscipline(discipline));
        discipline.setLearningOutcomes(outcomes);

        EvaluationSystem evaluationSystem = createEvaluationSystem();
        discipline.setEvaluationSystem(evaluationSystem);

        return discipline;
    }

    private Discipline createProgressiveWebAppsDiscipline(Teacher teacher) {
        Discipline discipline = new Discipline();
        discipline.setCode("IE428");
        discipline.setName("Progressive Web Applications");
        discipline.setDescription("Learn to build modern Progressive Web Applications (PWAs) that work offline, support push notifications, and provide native-like experiences. Focus on performance optimization and modern web capabilities.");
        discipline.setType(DisciplineType.OPTIONAL);
        discipline.setSemester(1);
        discipline.setYearOfStudy(3);
        discipline.setCredits(2);
        discipline.setAssessmentType(AssessmentType.EXAM);
        discipline.setLanguage(TeachingLanguage.EN);
        discipline.setMaxEnrollmentSpots(70);
        discipline.setCurrentEnrollmentCount(69);
        discipline.setWaitlistLimit(10);
        List<TeachingActivity> activities = createTeachingActivities(teacher, discipline);
        discipline.setTeachingActivities(activities);

        WeeklyHours weeklyHours = new WeeklyHours();
        weeklyHours.setCourse(2);
        weeklyHours.setLaboratory(2);
        weeklyHours.setSeminar(0);
        weeklyHours.setProject(0);
        weeklyHours.setTotal(2);
        discipline.setWeeklyHours(weeklyHours);

        TimeAllocation timeAllocation = new TimeAllocation();
        timeAllocation.setIndividualStudyHours(28);
        timeAllocation.setDocumentationHours(15);
        timeAllocation.setPreparationHours(15);
        timeAllocation.setTutoringHours(8);
        timeAllocation.setExaminationHours(4);
        timeAllocation.setOtherActivitiesHours(6);
        timeAllocation.setTotalSemesterHours(90);
        discipline.setTimeAllocation(timeAllocation);

        List<ContentModule> courseContent = createCourseContent(discipline);
        discipline.setCourseContent(courseContent);

        List<ContentModule> labContent = createLabContent(discipline);
        discipline.setLaboratoryContent(labContent);

        Bibliography bibliography = new Bibliography();

        List<BibliographyEntry> requiredEntries = new ArrayList<>();

        BibliographyEntry buildingPWA = new BibliographyEntry();
        buildingPWA.setTitle("Building Progressive Web Applications: Bringing the Power of Native to the Browser");
        buildingPWA.setAuthors("Tal Ater");
        buildingPWA.setPublicationYear(2023);
        buildingPWA.setType(BibliographyType.BOOK);
        buildingPWA.setIsbn("978-1-491-96165-0");
        requiredEntries.add(buildingPWA);

        BibliographyEntry pwaInAction = new BibliographyEntry();
        pwaInAction.setTitle("Progressive Web Apps in Action");
        pwaInAction.setAuthors("Dean Alan Hume");
        pwaInAction.setPublicationYear(2024);
        pwaInAction.setType(BibliographyType.BOOK);
        pwaInAction.setIsbn("978-1-617-29465-3");
        requiredEntries.add(pwaInAction);

        List<BibliographyEntry> recommendedEntries = new ArrayList<>();

        BibliographyEntry highPerformance = new BibliographyEntry();
        highPerformance.setTitle("High Performance Mobile Web");
        highPerformance.setAuthors("Maximiliano Firtman");
        highPerformance.setPublicationYear(2023);
        highPerformance.setType(BibliographyType.BOOK);
        recommendedEntries.add(highPerformance);

        BibliographyEntry webPerformance = new BibliographyEntry();
        webPerformance.setTitle("Web Performance in Action");
        webPerformance.setAuthors("Jeremy Wagner");
        webPerformance.setPublicationYear(2023);
        webPerformance.setType(BibliographyType.BOOK);
        recommendedEntries.add(webPerformance);

        List<BibliographyEntry> onlineEntries = new ArrayList<>();

        BibliographyEntry googlePWA = new BibliographyEntry();
        googlePWA.setTitle("Progressive Web Apps Training");
        googlePWA.setAuthors("Google Developers");
        googlePWA.setType(BibliographyType.ONLINE);
        googlePWA.setUrl("https://web.dev/learn/pwa/");
        onlineEntries.add(googlePWA);

        BibliographyEntry mozillaPWA = new BibliographyEntry();
        mozillaPWA.setTitle("Progressive Web Apps - MDN Web Docs");
        mozillaPWA.setAuthors("Mozilla Developer Network");
        mozillaPWA.setType(BibliographyType.ONLINE);
        mozillaPWA.setUrl("https://developer.mozilla.org/en-US/docs/Web/Progressive_web_apps");
        onlineEntries.add(mozillaPWA);

        BibliographyEntry workboxDocs = new BibliographyEntry();
        workboxDocs.setTitle("Workbox Documentation");
        workboxDocs.setAuthors("Google Chrome Team");
        workboxDocs.setType(BibliographyType.ONLINE);
        workboxDocs.setUrl("https://developers.google.com/web/tools/workbox");
        onlineEntries.add(workboxDocs);

        bibliography.setRequired(requiredEntries);
        bibliography.setRecommended(recommendedEntries);
        bibliography.setOnline(onlineEntries);

        discipline.setBibliography(bibliography);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(Arrays.asList(
                "Basic programming knowledge",
                "Understanding of web protocols",
                "JavaScript fundamentals"
        ));
        prerequisites.setRecommendations(Arrays.asList(
                "Previous web development experience",
                "Basic understanding of distributed systems"
        ));
        discipline.setPrerequisites(prerequisites);

        List<LearningOutcome> outcomes = createLearningOutcomes();
        outcomes.forEach(outcome -> outcome.setDiscipline(discipline));
        discipline.setLearningOutcomes(outcomes);

        EvaluationSystem evaluationSystem = createEvaluationSystem();
        discipline.setEvaluationSystem(evaluationSystem);

        return discipline;
    }

    private Discipline createAnotherExampleDiscipline(Teacher teacher) {
        Discipline discipline = new Discipline();
        discipline.setCode("IE256");
        discipline.setName("Another Example");
        discipline.setDescription("Learn modern web development techniques and frameworks, distributed systems architecture, and scalable application development. Focus on practical implementation of distributed systems concepts.");
        discipline.setType(DisciplineType.OPTIONAL);
        discipline.setSemester(1);
        discipline.setYearOfStudy(3);
        discipline.setCredits(2);
        discipline.setAssessmentType(AssessmentType.COLLOQUIUM);
        discipline.setLanguage(TeachingLanguage.EN);
        discipline.setMaxEnrollmentSpots(50);
        discipline.setCurrentEnrollmentCount(14);
        discipline.setWaitlistLimit(10);

        List<TeachingActivity> activities = createTeachingActivities(teacher, discipline);
        activities.forEach(activity -> {
            activity.setDiscipline(discipline);

            TeachingConditions conditions = new TeachingConditions();
            if (activity.getType() == TeachingActivityType.COURSE) {
                conditions.setLocation("Room A03");
                conditions.setRequirements(Arrays.asList(
                        "Laptop required",
                        "Basic programming environment setup"
                ));
            } else {
                conditions.setLocation("Lab L01");
                conditions.setRequirements(Arrays.asList(
                        "Development environment setup",
                        "Git installed",
                        "Docker installed"
                ));
            }

            conditions.setPlatforms(new ArrayList<>());
            activity.setConditions(conditions);
        });
        discipline.setTeachingActivities(activities);

        WeeklyHours weeklyHours = new WeeklyHours();
        weeklyHours.setCourse(2);
        weeklyHours.setLaboratory(2);
        weeklyHours.setSeminar(0);
        weeklyHours.setProject(0);
        weeklyHours.setTotal(4);
        discipline.setWeeklyHours(weeklyHours);

        TimeAllocation timeAllocation = new TimeAllocation();
        timeAllocation.setIndividualStudyHours(28);
        timeAllocation.setDocumentationHours(14);
        timeAllocation.setPreparationHours(14);
        timeAllocation.setTutoringHours(7);
        timeAllocation.setExaminationHours(4);
        timeAllocation.setOtherActivitiesHours(5);
        timeAllocation.setTotalSemesterHours(100);
        discipline.setTimeAllocation(timeAllocation);

        List<ContentModule> courseContent = createCourseContent(discipline);
        courseContent.forEach(content -> {
            content.setDiscipline(discipline);
            content.setType(ContentModuleType.COURSE);
            content.setBibliographyEntries(new ArrayList<>());
        });
        discipline.setCourseContent(courseContent);

        List<ContentModule> labContent = createLabContent(discipline);
        labContent.forEach(content -> {
            content.setDiscipline(discipline);
            content.setType(ContentModuleType.LABORATORY);
            content.setBibliographyEntries(new ArrayList<>()); // Initialize empty list
        });
        discipline.setLaboratoryContent(labContent);

        Bibliography bibliography = new Bibliography();

        List<BibliographyEntry> requiredEntries = new ArrayList<>();
        BibliographyEntry modernWeb = new BibliographyEntry();
        modernWeb.setTitle("Modern Web Development");
        modernWeb.setAuthors("John Doe");
        modernWeb.setPublicationYear(2023);
        modernWeb.setType(BibliographyType.BOOK);
        modernWeb.setIsbn("978-0-123456-78-9");
        requiredEntries.add(modernWeb);

        BibliographyEntry distributedSystems = new BibliographyEntry();
        distributedSystems.setTitle("Distributed Systems");
        distributedSystems.setAuthors("Andrew S. Tanenbaum");
        distributedSystems.setPublicationYear(2023);
        distributedSystems.setType(BibliographyType.BOOK);
        distributedSystems.setIsbn("978-0-135678-91-2");
        requiredEntries.add(distributedSystems);

        List<BibliographyEntry> recommendedEntries = new ArrayList<>();
        BibliographyEntry reactPatterns = new BibliographyEntry();
        reactPatterns.setTitle("React Design Patterns");
        reactPatterns.setAuthors("Jane Smith");
        reactPatterns.setPublicationYear(2023);
        reactPatterns.setType(BibliographyType.BOOK);
        recommendedEntries.add(reactPatterns);

        List<BibliographyEntry> onlineEntries = new ArrayList<>();
        BibliographyEntry reactDocs = new BibliographyEntry();
        reactDocs.setTitle("React Documentation");
        reactDocs.setAuthors("React Team");
        reactDocs.setType(BibliographyType.ONLINE);
        reactDocs.setUrl("https://react.dev");
        onlineEntries.add(reactDocs);

        bibliography.setRequired(requiredEntries);
        bibliography.setRecommended(recommendedEntries);
        bibliography.setOnline(onlineEntries);

        discipline.setBibliography(bibliography);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(Arrays.asList(
                "Basic programming knowledge",
                "Understanding of web protocols",
                "JavaScript fundamentals"
        ));
        prerequisites.setRecommendations(Arrays.asList(
                "Previous web development experience",
                "Basic understanding of distributed systems"
        ));
        discipline.setPrerequisites(prerequisites);

        List<LearningOutcome> outcomes = createLearningOutcomes();
        outcomes.forEach(outcome -> outcome.setDiscipline(discipline));
        discipline.setLearningOutcomes(outcomes);

        EvaluationSystem evaluationSystem = createEvaluationSystem();
        discipline.setEvaluationSystem(evaluationSystem);

        return discipline;
    }

    private Discipline createDevopsAndStuffDiscipline(Teacher teacher) {
        Discipline discipline = new Discipline();
        discipline.setCode("IE427");
        discipline.setName("Devops and Stuff");
        discipline.setDescription("Master modern software architecture principles, design patterns, and architectural patterns. Focus on creating scalable, maintainable, and flexible software systems through practical implementation of enterprise patterns.");
        discipline.setType(DisciplineType.OPTIONAL);
        discipline.setSemester(1);
        discipline.setYearOfStudy(3);
        discipline.setCredits(2);
        discipline.setAssessmentType(AssessmentType.COLLOQUIUM);
        discipline.setLanguage(TeachingLanguage.EN);
        discipline.setMaxEnrollmentSpots(80);
        discipline.setCurrentEnrollmentCount(69);
        discipline.setWaitlistLimit(10);

        List<TeachingActivity> activities = createTeachingActivities(teacher, discipline);
        discipline.setTeachingActivities(activities);

        WeeklyHours weeklyHours = new WeeklyHours();
        weeklyHours.setCourse(2);
        weeklyHours.setLaboratory(2);
        weeklyHours.setSeminar(0);
        weeklyHours.setProject(0);
        weeklyHours.setTotal(4);
        discipline.setWeeklyHours(weeklyHours);

        TimeAllocation timeAllocation = new TimeAllocation();
        timeAllocation.setIndividualStudyHours(30);
        timeAllocation.setDocumentationHours(20);
        timeAllocation.setPreparationHours(20);
        timeAllocation.setTutoringHours(8);
        timeAllocation.setExaminationHours(4);
        timeAllocation.setOtherActivitiesHours(6);
        timeAllocation.setTotalSemesterHours(100);
        discipline.setTimeAllocation(timeAllocation);

        List<ContentModule> courseContent = createCourseContent(discipline);
        discipline.setCourseContent(courseContent);

        List<ContentModule> labContent = createLabContent(discipline);
        discipline.setLaboratoryContent(labContent);

        discipline.setCourseContent(courseContent);

        Bibliography bibliography = new Bibliography();

        List<BibliographyEntry> requiredEntries = new ArrayList<>();

        BibliographyEntry cleanArchitecture = new BibliographyEntry();
        cleanArchitecture.setTitle("Clean Architecture: A Craftsman's Guide to Software Structure and Design");
        cleanArchitecture.setAuthors("Robert C. Martin");
        cleanArchitecture.setPublicationYear(2023);
        cleanArchitecture.setType(BibliographyType.BOOK);
        cleanArchitecture.setIsbn("978-0-134-49416-6");
        requiredEntries.add(cleanArchitecture);

        BibliographyEntry patternEnterprise = new BibliographyEntry();
        patternEnterprise.setTitle("Patterns of Enterprise Application Architecture");
        patternEnterprise.setAuthors("Martin Fowler");
        patternEnterprise.setPublicationYear(2023);
        patternEnterprise.setType(BibliographyType.BOOK);
        patternEnterprise.setIsbn("978-0-321-12742-6");
        requiredEntries.add(patternEnterprise);

        List<BibliographyEntry> recommendedEntries = new ArrayList<>();

        BibliographyEntry designPatterns = new BibliographyEntry();
        designPatterns.setTitle("Design Patterns: Elements of Reusable Object-Oriented Software");
        designPatterns.setAuthors("Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides");
        designPatterns.setPublicationYear(2024);
        designPatterns.setType(BibliographyType.BOOK);
        recommendedEntries.add(designPatterns);

        BibliographyEntry domainDriven = new BibliographyEntry();
        domainDriven.setTitle("Domain-Driven Design: Tackling Complexity in the Heart of Software");
        domainDriven.setAuthors("Eric Evans");
        domainDriven.setPublicationYear(2023);
        domainDriven.setType(BibliographyType.BOOK);
        recommendedEntries.add(domainDriven);

        List<BibliographyEntry> onlineEntries = new ArrayList<>();

        BibliographyEntry martinFowler = new BibliographyEntry();
        martinFowler.setTitle("Martin Fowler's Architecture Guide");
        martinFowler.setAuthors("Martin Fowler");
        martinFowler.setType(BibliographyType.ONLINE);
        martinFowler.setUrl("https://martinfowler.com/architecture/");
        onlineEntries.add(martinFowler);

        BibliographyEntry refactoring = new BibliographyEntry();
        refactoring.setTitle("Refactoring Guru - Design Patterns");
        refactoring.setAuthors("Alexander Shvets");
        refactoring.setType(BibliographyType.ONLINE);
        refactoring.setUrl("https://refactoring.guru/design-patterns");
        onlineEntries.add(refactoring);

        bibliography.setRequired(requiredEntries);
        bibliography.setRecommended(recommendedEntries);
        bibliography.setOnline(onlineEntries);

        discipline.setBibliography(bibliography);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(Arrays.asList(
                "Basic programming knowledge",
                "Understanding of web protocols",
                "JavaScript fundamentals"
        ));
        prerequisites.setRecommendations(Arrays.asList(
                "Previous web development experience",
                "Basic understanding of distributed systems"
        ));
        discipline.setPrerequisites(prerequisites);

        List<LearningOutcome> outcomes = createLearningOutcomes();
        outcomes.forEach(outcome -> outcome.setDiscipline(discipline));
        discipline.setLearningOutcomes(outcomes);

        EvaluationSystem evaluationSystem = createEvaluationSystem();
        discipline.setEvaluationSystem(evaluationSystem);

        return discipline;
    }

    private Discipline createExample2Discipline(Teacher teacher) {
        Discipline discipline = new Discipline();
        discipline.setCode("IE144");
        discipline.setName("Example2 Discipline");
        discipline.setDescription("Learn to build modern Progressive Web Applications (PWAs) that work offline, support push notifications, and provide native-like experiences. Focus on performance optimization and modern web capabilities.");
        discipline.setType(DisciplineType.OPTIONAL);
        discipline.setSemester(2);
        discipline.setYearOfStudy(3);
        discipline.setCredits(4);
        discipline.setAssessmentType(AssessmentType.EXAM);
        discipline.setLanguage(TeachingLanguage.EN);
        discipline.setMaxEnrollmentSpots(75);
        discipline.setCurrentEnrollmentCount(58);
        discipline.setWaitlistLimit(10);
        List<TeachingActivity> activities = createTeachingActivities(teacher, discipline);
        discipline.setTeachingActivities(activities);

        WeeklyHours weeklyHours = new WeeklyHours();
        weeklyHours.setCourse(2);
        weeklyHours.setLaboratory(2);
        weeklyHours.setSeminar(0);
        weeklyHours.setProject(0);
        weeklyHours.setTotal(4);
        discipline.setWeeklyHours(weeklyHours);

        TimeAllocation timeAllocation = new TimeAllocation();
        timeAllocation.setIndividualStudyHours(28);
        timeAllocation.setDocumentationHours(15);
        timeAllocation.setPreparationHours(15);
        timeAllocation.setTutoringHours(8);
        timeAllocation.setExaminationHours(4);
        timeAllocation.setOtherActivitiesHours(6);
        timeAllocation.setTotalSemesterHours(90);
        discipline.setTimeAllocation(timeAllocation);

        List<ContentModule> courseContent = createCourseContent(discipline);
        discipline.setCourseContent(courseContent);

        List<ContentModule> labContent = createLabContent(discipline);
        discipline.setLaboratoryContent(labContent);

        Bibliography bibliography = new Bibliography();

        List<BibliographyEntry> requiredEntries = new ArrayList<>();

        BibliographyEntry buildingPWA = new BibliographyEntry();
        buildingPWA.setTitle("Building Progressive Web Applications: Bringing the Power of Native to the Browser");
        buildingPWA.setAuthors("Tal Ater");
        buildingPWA.setPublicationYear(2023);
        buildingPWA.setType(BibliographyType.BOOK);
        buildingPWA.setIsbn("978-1-491-96165-0");
        requiredEntries.add(buildingPWA);

        BibliographyEntry pwaInAction = new BibliographyEntry();
        pwaInAction.setTitle("Progressive Web Apps in Action");
        pwaInAction.setAuthors("Dean Alan Hume");
        pwaInAction.setPublicationYear(2024);
        pwaInAction.setType(BibliographyType.BOOK);
        pwaInAction.setIsbn("978-1-617-29465-3");
        requiredEntries.add(pwaInAction);

        List<BibliographyEntry> recommendedEntries = new ArrayList<>();

        BibliographyEntry highPerformance = new BibliographyEntry();
        highPerformance.setTitle("High Performance Mobile Web");
        highPerformance.setAuthors("Maximiliano Firtman");
        highPerformance.setPublicationYear(2023);
        highPerformance.setType(BibliographyType.BOOK);
        recommendedEntries.add(highPerformance);

        BibliographyEntry webPerformance = new BibliographyEntry();
        webPerformance.setTitle("Web Performance in Action");
        webPerformance.setAuthors("Jeremy Wagner");
        webPerformance.setPublicationYear(2023);
        webPerformance.setType(BibliographyType.BOOK);
        recommendedEntries.add(webPerformance);

        List<BibliographyEntry> onlineEntries = new ArrayList<>();

        BibliographyEntry googlePWA = new BibliographyEntry();
        googlePWA.setTitle("Progressive Web Apps Training");
        googlePWA.setAuthors("Google Developers");
        googlePWA.setType(BibliographyType.ONLINE);
        googlePWA.setUrl("https://web.dev/learn/pwa/");
        onlineEntries.add(googlePWA);

        BibliographyEntry mozillaPWA = new BibliographyEntry();
        mozillaPWA.setTitle("Progressive Web Apps - MDN Web Docs");
        mozillaPWA.setAuthors("Mozilla Developer Network");
        mozillaPWA.setType(BibliographyType.ONLINE);
        mozillaPWA.setUrl("https://developer.mozilla.org/en-US/docs/Web/Progressive_web_apps");
        onlineEntries.add(mozillaPWA);

        BibliographyEntry workboxDocs = new BibliographyEntry();
        workboxDocs.setTitle("Workbox Documentation");
        workboxDocs.setAuthors("Google Chrome Team");
        workboxDocs.setType(BibliographyType.ONLINE);
        workboxDocs.setUrl("https://developers.google.com/web/tools/workbox");
        onlineEntries.add(workboxDocs);

        bibliography.setRequired(requiredEntries);
        bibliography.setRecommended(recommendedEntries);
        bibliography.setOnline(onlineEntries);

        discipline.setBibliography(bibliography);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(Arrays.asList(
                "Basic programming knowledge",
                "Understanding of web protocols",
                "JavaScript fundamentals"
        ));
        prerequisites.setRecommendations(Arrays.asList(
                "Previous web development experience",
                "Basic understanding of distributed systems"
        ));
        discipline.setPrerequisites(prerequisites);

        List<LearningOutcome> outcomes = createLearningOutcomes();
        outcomes.forEach(outcome -> outcome.setDiscipline(discipline));
        discipline.setLearningOutcomes(outcomes);

        EvaluationSystem evaluationSystem = createEvaluationSystem();
        discipline.setEvaluationSystem(evaluationSystem);

        return discipline;
    }

    private Discipline createExample1Discipline(Teacher teacher) {
        Discipline discipline = new Discipline();
        discipline.setCode("IE724");
        discipline.setName("Example1 Discipline");
        discipline.setDescription("Learn to build modern Progressive Web Applications (PWAs) that work offline, support push notifications, and provide native-like experiences. Focus on performance optimization and modern web capabilities.");
        discipline.setType(DisciplineType.OPTIONAL);
        discipline.setSemester(2);
        discipline.setYearOfStudy(3);
        discipline.setCredits(4);
        discipline.setAssessmentType(AssessmentType.EXAM);
        discipline.setLanguage(TeachingLanguage.EN);
        discipline.setMaxEnrollmentSpots(75);
        discipline.setCurrentEnrollmentCount(70);
        discipline.setWaitlistLimit(10);
        List<TeachingActivity> activities = createTeachingActivities(teacher, discipline);
        discipline.setTeachingActivities(activities);

        WeeklyHours weeklyHours = new WeeklyHours();
        weeklyHours.setCourse(2);
        weeklyHours.setLaboratory(2);
        weeklyHours.setSeminar(0);
        weeklyHours.setProject(0);
        weeklyHours.setTotal(4);
        discipline.setWeeklyHours(weeklyHours);

        TimeAllocation timeAllocation = new TimeAllocation();
        timeAllocation.setIndividualStudyHours(28);
        timeAllocation.setDocumentationHours(15);
        timeAllocation.setPreparationHours(15);
        timeAllocation.setTutoringHours(8);
        timeAllocation.setExaminationHours(4);
        timeAllocation.setOtherActivitiesHours(6);
        timeAllocation.setTotalSemesterHours(90);
        discipline.setTimeAllocation(timeAllocation);

        List<ContentModule> courseContent = createCourseContent(discipline);
        discipline.setCourseContent(courseContent);

        List<ContentModule> labContent = createLabContent(discipline);
        discipline.setLaboratoryContent(labContent);

        Bibliography bibliography = new Bibliography();

        List<BibliographyEntry> requiredEntries = new ArrayList<>();

        BibliographyEntry buildingPWA = new BibliographyEntry();
        buildingPWA.setTitle("Building Progressive Web Applications: Bringing the Power of Native to the Browser");
        buildingPWA.setAuthors("Tal Ater");
        buildingPWA.setPublicationYear(2023);
        buildingPWA.setType(BibliographyType.BOOK);
        buildingPWA.setIsbn("978-1-491-96165-0");
        requiredEntries.add(buildingPWA);

        BibliographyEntry pwaInAction = new BibliographyEntry();
        pwaInAction.setTitle("Progressive Web Apps in Action");
        pwaInAction.setAuthors("Dean Alan Hume");
        pwaInAction.setPublicationYear(2024);
        pwaInAction.setType(BibliographyType.BOOK);
        pwaInAction.setIsbn("978-1-617-29465-3");
        requiredEntries.add(pwaInAction);

        List<BibliographyEntry> recommendedEntries = new ArrayList<>();

        BibliographyEntry highPerformance = new BibliographyEntry();
        highPerformance.setTitle("High Performance Mobile Web");
        highPerformance.setAuthors("Maximiliano Firtman");
        highPerformance.setPublicationYear(2023);
        highPerformance.setType(BibliographyType.BOOK);
        recommendedEntries.add(highPerformance);

        BibliographyEntry webPerformance = new BibliographyEntry();
        webPerformance.setTitle("Web Performance in Action");
        webPerformance.setAuthors("Jeremy Wagner");
        webPerformance.setPublicationYear(2023);
        webPerformance.setType(BibliographyType.BOOK);
        recommendedEntries.add(webPerformance);

        List<BibliographyEntry> onlineEntries = new ArrayList<>();

        BibliographyEntry googlePWA = new BibliographyEntry();
        googlePWA.setTitle("Progressive Web Apps Training");
        googlePWA.setAuthors("Google Developers");
        googlePWA.setType(BibliographyType.ONLINE);
        googlePWA.setUrl("https://web.dev/learn/pwa/");
        onlineEntries.add(googlePWA);

        BibliographyEntry mozillaPWA = new BibliographyEntry();
        mozillaPWA.setTitle("Progressive Web Apps - MDN Web Docs");
        mozillaPWA.setAuthors("Mozilla Developer Network");
        mozillaPWA.setType(BibliographyType.ONLINE);
        mozillaPWA.setUrl("https://developer.mozilla.org/en-US/docs/Web/Progressive_web_apps");
        onlineEntries.add(mozillaPWA);

        BibliographyEntry workboxDocs = new BibliographyEntry();
        workboxDocs.setTitle("Workbox Documentation");
        workboxDocs.setAuthors("Google Chrome Team");
        workboxDocs.setType(BibliographyType.ONLINE);
        workboxDocs.setUrl("https://developers.google.com/web/tools/workbox");
        onlineEntries.add(workboxDocs);

        bibliography.setRequired(requiredEntries);
        bibliography.setRecommended(recommendedEntries);
        bibliography.setOnline(onlineEntries);

        discipline.setBibliography(bibliography);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(Arrays.asList(
                "Basic programming knowledge",
                "Understanding of web protocols",
                "JavaScript fundamentals"
        ));
        prerequisites.setRecommendations(Arrays.asList(
                "Previous web development experience",
                "Basic understanding of distributed systems"
        ));
        discipline.setPrerequisites(prerequisites);

        List<LearningOutcome> outcomes = createLearningOutcomes();
        outcomes.forEach(outcome -> outcome.setDiscipline(discipline));
        discipline.setLearningOutcomes(outcomes);

        EvaluationSystem evaluationSystem = createEvaluationSystem();
        discipline.setEvaluationSystem(evaluationSystem);

        return discipline;
    }

    private EnrollmentPeriod createElectivePeriod(List<Teacher> teachers) {
        EnrollmentPeriod period = new EnrollmentPeriod();
        period.setType(EnrollmentPeriodType.ELECTIVE_DISCIPLINES);
        period.setStartDate(LocalDateTime.of(2025, 1, 1, 0, 0));
        period.setEndDate(LocalDateTime.of(2025, 2, 1, 23, 59));
        period.setSemester(2);
        period.setYearOfStudy(3);
        period.setTargetYearOfStudy(3);
        period.setTargetSemester(1);
        period.setAcademicYear("2024-2025");
        period.setProgress(45);
        period.setIsActive(true);
        period.setStatus(determineStatus(period.getStartDate(), period.getEndDate()));
        period.getTargetSpecializations().addAll(Set.of("Computer Science", "Informatics"));
        return period;
    }

    private Discipline createExample3Discipline(Teacher teacher) {
        Discipline discipline = new Discipline();
        discipline.setCode("IE074");
        discipline.setName("Example3 Discipline");
        discipline.setDescription("Learn to build modern Progressive Web Applications (PWAs) that work offline, support push notifications, and provide native-like experiences. Focus on performance optimization and modern web capabilities.");
        discipline.setType(DisciplineType.OPTIONAL);
        discipline.setSemester(2);
        discipline.setYearOfStudy(3);
        discipline.setCredits(4);
        discipline.setAssessmentType(AssessmentType.EXAM);
        discipline.setLanguage(TeachingLanguage.EN);
        discipline.setMaxEnrollmentSpots(75);
        discipline.setCurrentEnrollmentCount(58);
        discipline.setWaitlistLimit(10);
        List<TeachingActivity> activities = createTeachingActivities(teacher, discipline);
        discipline.setTeachingActivities(activities);

        WeeklyHours weeklyHours = new WeeklyHours();
        weeklyHours.setCourse(2);
        weeklyHours.setLaboratory(2);
        weeklyHours.setSeminar(0);
        weeklyHours.setProject(0);
        weeklyHours.setTotal(2);
        discipline.setWeeklyHours(weeklyHours);

        TimeAllocation timeAllocation = new TimeAllocation();
        timeAllocation.setIndividualStudyHours(28);
        timeAllocation.setDocumentationHours(15);
        timeAllocation.setPreparationHours(15);
        timeAllocation.setTutoringHours(8);
        timeAllocation.setExaminationHours(4);
        timeAllocation.setOtherActivitiesHours(6);
        timeAllocation.setTotalSemesterHours(90);
        discipline.setTimeAllocation(timeAllocation);

        List<ContentModule> courseContent = createCourseContent(discipline);
        discipline.setCourseContent(courseContent);

        List<ContentModule> labContent = createLabContent(discipline);
        discipline.setLaboratoryContent(labContent);

        Bibliography bibliography = new Bibliography();

        List<BibliographyEntry> requiredEntries = new ArrayList<>();

        BibliographyEntry buildingPWA = new BibliographyEntry();
        buildingPWA.setTitle("Building Progressive Web Applications: Bringing the Power of Native to the Browser");
        buildingPWA.setAuthors("Tal Ater");
        buildingPWA.setPublicationYear(2023);
        buildingPWA.setType(BibliographyType.BOOK);
        buildingPWA.setIsbn("978-1-491-96165-0");
        requiredEntries.add(buildingPWA);

        BibliographyEntry pwaInAction = new BibliographyEntry();
        pwaInAction.setTitle("Progressive Web Apps in Action");
        pwaInAction.setAuthors("Dean Alan Hume");
        pwaInAction.setPublicationYear(2024);
        pwaInAction.setType(BibliographyType.BOOK);
        pwaInAction.setIsbn("978-1-617-29465-3");
        requiredEntries.add(pwaInAction);

        List<BibliographyEntry> recommendedEntries = new ArrayList<>();

        BibliographyEntry highPerformance = new BibliographyEntry();
        highPerformance.setTitle("High Performance Mobile Web");
        highPerformance.setAuthors("Maximiliano Firtman");
        highPerformance.setPublicationYear(2023);
        highPerformance.setType(BibliographyType.BOOK);
        recommendedEntries.add(highPerformance);

        BibliographyEntry webPerformance = new BibliographyEntry();
        webPerformance.setTitle("Web Performance in Action");
        webPerformance.setAuthors("Jeremy Wagner");
        webPerformance.setPublicationYear(2023);
        webPerformance.setType(BibliographyType.BOOK);
        recommendedEntries.add(webPerformance);

        List<BibliographyEntry> onlineEntries = new ArrayList<>();

        BibliographyEntry googlePWA = new BibliographyEntry();
        googlePWA.setTitle("Progressive Web Apps Training");
        googlePWA.setAuthors("Google Developers");
        googlePWA.setType(BibliographyType.ONLINE);
        googlePWA.setUrl("https://web.dev/learn/pwa/");
        onlineEntries.add(googlePWA);

        BibliographyEntry mozillaPWA = new BibliographyEntry();
        mozillaPWA.setTitle("Progressive Web Apps - MDN Web Docs");
        mozillaPWA.setAuthors("Mozilla Developer Network");
        mozillaPWA.setType(BibliographyType.ONLINE);
        mozillaPWA.setUrl("https://developer.mozilla.org/en-US/docs/Web/Progressive_web_apps");
        onlineEntries.add(mozillaPWA);

        BibliographyEntry workboxDocs = new BibliographyEntry();
        workboxDocs.setTitle("Workbox Documentation");
        workboxDocs.setAuthors("Google Chrome Team");
        workboxDocs.setType(BibliographyType.ONLINE);
        workboxDocs.setUrl("https://developers.google.com/web/tools/workbox");
        onlineEntries.add(workboxDocs);

        bibliography.setRequired(requiredEntries);
        bibliography.setRecommended(recommendedEntries);
        bibliography.setOnline(onlineEntries);

        discipline.setBibliography(bibliography);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(Arrays.asList(
                "Basic programming knowledge",
                "Understanding of web protocols",
                "JavaScript fundamentals"
        ));
        prerequisites.setRecommendations(Arrays.asList(
                "Previous web development experience",
                "Basic understanding of distributed systems"
        ));
        discipline.setPrerequisites(prerequisites);

        List<LearningOutcome> outcomes = createLearningOutcomes();
        outcomes.forEach(outcome -> outcome.setDiscipline(discipline));
        discipline.setLearningOutcomes(outcomes);

        EvaluationSystem evaluationSystem = createEvaluationSystem();
        discipline.setEvaluationSystem(evaluationSystem);

        return discipline;
    }

    private Discipline createExample4Discipline(Teacher teacher) {
        Discipline discipline = new Discipline();
        discipline.setCode("IE621");
        discipline.setName("Example4 Discipline");
        discipline.setDescription("Learn to build modern Progressive Web Applications (PWAs) that work offline, support push notifications, and provide native-like experiences. Focus on performance optimization and modern web capabilities.");
        discipline.setType(DisciplineType.OPTIONAL);
        discipline.setSemester(2);
        discipline.setYearOfStudy(3);
        discipline.setCredits(4);
        discipline.setAssessmentType(AssessmentType.COLLOQUIUM);
        discipline.setLanguage(TeachingLanguage.EN);
        discipline.setMaxEnrollmentSpots(75);
        discipline.setCurrentEnrollmentCount(58);
        discipline.setWaitlistLimit(10);
        List<TeachingActivity> activities = createTeachingActivities(teacher, discipline);
        discipline.setTeachingActivities(activities);

        WeeklyHours weeklyHours = new WeeklyHours();
        weeklyHours.setCourse(2);
        weeklyHours.setLaboratory(2);
        weeklyHours.setSeminar(0);
        weeklyHours.setProject(0);
        weeklyHours.setTotal(2);
        discipline.setWeeklyHours(weeklyHours);

        TimeAllocation timeAllocation = new TimeAllocation();
        timeAllocation.setIndividualStudyHours(28);
        timeAllocation.setDocumentationHours(15);
        timeAllocation.setPreparationHours(15);
        timeAllocation.setTutoringHours(8);
        timeAllocation.setExaminationHours(4);
        timeAllocation.setOtherActivitiesHours(6);
        timeAllocation.setTotalSemesterHours(90);
        discipline.setTimeAllocation(timeAllocation);

        List<ContentModule> courseContent = createCourseContent(discipline);
        discipline.setCourseContent(courseContent);

        List<ContentModule> labContent = createLabContent(discipline);
        discipline.setLaboratoryContent(labContent);

        Bibliography bibliography = new Bibliography();

        List<BibliographyEntry> requiredEntries = new ArrayList<>();

        BibliographyEntry buildingPWA = new BibliographyEntry();
        buildingPWA.setTitle("Building Progressive Web Applications: Bringing the Power of Native to the Browser");
        buildingPWA.setAuthors("Tal Ater");
        buildingPWA.setPublicationYear(2023);
        buildingPWA.setType(BibliographyType.BOOK);
        buildingPWA.setIsbn("978-1-491-96165-0");
        requiredEntries.add(buildingPWA);

        BibliographyEntry pwaInAction = new BibliographyEntry();
        pwaInAction.setTitle("Progressive Web Apps in Action");
        pwaInAction.setAuthors("Dean Alan Hume");
        pwaInAction.setPublicationYear(2024);
        pwaInAction.setType(BibliographyType.BOOK);
        pwaInAction.setIsbn("978-1-617-29465-3");
        requiredEntries.add(pwaInAction);

        List<BibliographyEntry> recommendedEntries = new ArrayList<>();

        BibliographyEntry highPerformance = new BibliographyEntry();
        highPerformance.setTitle("High Performance Mobile Web");
        highPerformance.setAuthors("Maximiliano Firtman");
        highPerformance.setPublicationYear(2023);
        highPerformance.setType(BibliographyType.BOOK);
        recommendedEntries.add(highPerformance);

        BibliographyEntry webPerformance = new BibliographyEntry();
        webPerformance.setTitle("Web Performance in Action");
        webPerformance.setAuthors("Jeremy Wagner");
        webPerformance.setPublicationYear(2023);
        webPerformance.setType(BibliographyType.BOOK);
        recommendedEntries.add(webPerformance);

        List<BibliographyEntry> onlineEntries = new ArrayList<>();

        BibliographyEntry googlePWA = new BibliographyEntry();
        googlePWA.setTitle("Progressive Web Apps Training");
        googlePWA.setAuthors("Google Developers");
        googlePWA.setType(BibliographyType.ONLINE);
        googlePWA.setUrl("https://web.dev/learn/pwa/");
        onlineEntries.add(googlePWA);

        BibliographyEntry mozillaPWA = new BibliographyEntry();
        mozillaPWA.setTitle("Progressive Web Apps - MDN Web Docs");
        mozillaPWA.setAuthors("Mozilla Developer Network");
        mozillaPWA.setType(BibliographyType.ONLINE);
        mozillaPWA.setUrl("https://developer.mozilla.org/en-US/docs/Web/Progressive_web_apps");
        onlineEntries.add(mozillaPWA);

        BibliographyEntry workboxDocs = new BibliographyEntry();
        workboxDocs.setTitle("Workbox Documentation");
        workboxDocs.setAuthors("Google Chrome Team");
        workboxDocs.setType(BibliographyType.ONLINE);
        workboxDocs.setUrl("https://developers.google.com/web/tools/workbox");
        onlineEntries.add(workboxDocs);

        bibliography.setRequired(requiredEntries);
        bibliography.setRecommended(recommendedEntries);
        bibliography.setOnline(onlineEntries);

        discipline.setBibliography(bibliography);

        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(Arrays.asList(
                "Basic programming knowledge",
                "Understanding of web protocols",
                "JavaScript fundamentals"
        ));
        prerequisites.setRecommendations(Arrays.asList(
                "Previous web development experience",
                "Basic understanding of distributed systems"
        ));
        discipline.setPrerequisites(prerequisites);

        List<LearningOutcome> outcomes = createLearningOutcomes();
        outcomes.forEach(outcome -> outcome.setDiscipline(discipline));
        discipline.setLearningOutcomes(outcomes);

        EvaluationSystem evaluationSystem = createEvaluationSystem();
        discipline.setEvaluationSystem(evaluationSystem);

        return discipline;
    }

    private List<DisciplinePacket> createElectivePackets(String distributedSystemsId,
                                                         String softwareArchitectureId,
                                                         String progressiveWebAppsId,
                                                         String devopsAndStuffId,
                                                         String anotherExampleId,
                                                         String example1Id,
                                                         String example2Id,
                                                         String example3Id,
                                                         String example4Id
    ) {
        DisciplinePacket packet1 = createPacket(
                "Elective Packet 1",
                "Advanced development techniques and frameworks",
                2,
                Arrays.asList(
                        distributedSystemsId,
                        softwareArchitectureId,
                        progressiveWebAppsId,
                        devopsAndStuffId,
                        anotherExampleId,
                        example3Id,
                        example4Id
                ),
                createPrerequisites(
                        Arrays.asList(
                                "Basic programming",
                                "JavaScript fundamewntals",
                                "Understanding of software design principles"
                        ),
                        Arrays.asList(
                                "Previous web development experience",
                                "Familiarity with architectural patterns"
                        )
                ),
                "Technical"
        );

        DisciplinePacket packet2 = createPacket(
                "Elective Packet 2",
                "Cloud and DevOps technologies",
                1,
                Arrays.asList(example1Id, example2Id),
                createPrerequisites(
                        Arrays.asList("Networking basics", "Operating Systems"),
                        Arrays.asList("Cloud computing knowledge")
                ),
                "Technical"
        );

        return Arrays.asList(packet1, packet2);
    }

    private DisciplinePacket createPacket(
            String name,
            String description,
            Integer maxChoices,
            List<String> disciplines,
            Prerequisites prerequisites,
            String category
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
        packet.setCategory(category);
        packet.setPrerequisites(prerequisites);
        return packet;
    }

    private Prerequisites createPrerequisites(List<String> skills, List<String> recommendations) {
        Prerequisites prerequisites = new Prerequisites();
        prerequisites.setRequiredSkills(skills);
        prerequisites.setRecommendations(recommendations);
        return prerequisites;
    }

    private List<Teacher> createAndSaveTeachers() {
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Nicusor");
        teacher1.setLastName("Dan");
        teacher1.setEmail("nicusor.dan@e-uvt.ro");
        teacher1.setDepartment("Computer Science");

        AcademicTitle title1 = new AcademicTitle();
        title1.setTitle("Profesor Doctor");
        title1.setAbbreviation("Prof. Dr.");
        teacher1.setAcademicTitle(title1);

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Adrian");
        teacher2.setLastName("Spataru");
        teacher2.setEmail("adi.spataru@e-uvt.ro");
        teacher2.setDepartment("Computer Science");

        AcademicTitle title2 = new AcademicTitle();
        title2.setTitle("Lector Doctor");
        title2.setAbbreviation("Lect. Dr.");
        teacher2.setAcademicTitle(title2);

        Teacher teacher3 = new Teacher();
        teacher3.setFirstName("Gabriel");
        teacher3.setLastName("Iuhasz");
        teacher3.setEmail("gabriel.iuhasz@e-uvt.ro");
        teacher3.setDepartment("Computer Science");

        AcademicTitle title3 = new AcademicTitle();
        title3.setTitle("Lector Doctor");
        title3.setAbbreviation("Lect. Dr.");
        teacher3.setAcademicTitle(title3);

        return teacherRepository.saveAll(Arrays.asList(teacher1, teacher2, teacher3));
    }

    private void createAndSaveEnrollmentPeriod(
            EnrollmentPeriod period,
            List<DisciplinePacket> packets
    ) {
        period.setPackets(packets);
        EnrollmentPeriod saved = enrollmentPeriodRepository.save(period);
        log.info("Created enrollment period: type={}, specializations={}",
                saved.getType(), saved.getTargetSpecializations());
    }

    private EnrollmentPeriodStatus determineStatus(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startDate)) return EnrollmentPeriodStatus.UPCOMING;
        if (now.isAfter(endDate)) return EnrollmentPeriodStatus.ENDED;
        return EnrollmentPeriodStatus.ACTIVE;
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

    private EnrollmentPeriod createComplementaryPeriod() {
        EnrollmentPeriod period = new EnrollmentPeriod();
        period.setType(EnrollmentPeriodType.COMPLEMENTARY_DISCIPLINES);
        period.setStartDate(LocalDateTime.of(2025, 2, 2, 0, 0));
        period.setEndDate(LocalDateTime.of(2025, 2, 15, 23, 59));
        period.setSemester(2);
        period.setYearOfStudy(3);
        period.setTargetYearOfStudy(3);
        period.setTargetSemester(1);
        period.setAcademicYear("2024-2025");
        period.setIsActive(false);
        period.setStatus(determineStatus(period.getStartDate(), period.getEndDate()));
        period.getTargetSpecializations().add("All Specializations");
        return period;
    }

    private List<DisciplinePacket> createComplementaryPackets() {
        DisciplinePacket packet1 = createPacket(
                "Humanities and Social Sciences",
                "Broaden your perspective with courses from humanities and social sciences",
                2,
                Arrays.asList("comp1", "comp2", "comp3"),
                createPrerequisites(
                        Collections.singletonList("None required"),
                        Collections.singletonList("Interest in interdisciplinary studies")
                ),
                "Complementary"
        );

        DisciplinePacket packet2 = createPacket(
                "Business and Entrepreneurship",
                "Develop business acumen and entrepreneurial skills",
                1,
                Arrays.asList("comp4", "comp5"),
                createPrerequisites(
                        Collections.singletonList("Basic economics understanding"),
                        Collections.singletonList("Interest in business development")
                ),
                "Complementary"
        );

        return Arrays.asList(packet1, packet2);
    }

    private EnrollmentPeriod createThesisPeriod() {
        EnrollmentPeriod period = new EnrollmentPeriod();
        period.setType(EnrollmentPeriodType.THESIS_REGISTRATION);
        period.setStartDate(LocalDateTime.of(2024, 12, 1, 0, 0));
        period.setEndDate(LocalDateTime.of(2024, 12, 15, 23, 59));
        period.setSemester(2);
        period.setYearOfStudy(3);
        period.setTargetYearOfStudy(3);
        period.setTargetSemester(1);
        period.setAcademicYear("2024-2025");
        period.setProgress(100);
        period.setIsActive(false);
        period.setStatus(determineStatus(period.getStartDate(), period.getEndDate()));
        period.getTargetSpecializations().addAll(Set.of("Computer Science", "Software Engineering"));
        return period;
    }

    private List<DisciplinePacket> createThesisPackets() {
        return Collections.singletonList(createPacket(
                "Software Engineering Research Topics",
                "Research projects in software engineering and development",
                1,
                Arrays.asList("thesis1", "thesis2", "thesis3"),
                createPrerequisites(
                        Arrays.asList("Strong programming background", "Research methodology"),
                        Collections.singletonList("Previous research experience")
                ),
                "Thesis"
        ));
    }

    private List<TeachingActivity> createTeachingActivities(Teacher teacher, Discipline discipline) {
        TeachingActivity course = new TeachingActivity();
        course.setType(TeachingActivityType.COURSE);
        course.setHoursPerWeek(2);
        course.setTotalHours(28);
        course.setTeacher(teacher);
        course.setDiscipline(discipline);  // Set the discipline reference
        course.setTeachingMethods(Arrays.asList(
                "Interactive lectures",
                "Design workshops",
                "Architecture analysis"
        ));

        TeachingConditions courseConditions = new TeachingConditions();
        courseConditions.setLocation("Room A204");
        courseConditions.setRequirements(Arrays.asList(
                "Laptop with development environment",
                "UML modeling tools installed"
        ));
        courseConditions.setPlatforms(new ArrayList<>());
        course.setConditions(courseConditions);

        TeachingActivity lab = new TeachingActivity();
        lab.setType(TeachingActivityType.LABORATORY);
        lab.setHoursPerWeek(2);
        lab.setTotalHours(28);
        lab.setTeacher(teacher);
        lab.setDiscipline(discipline);
        lab.setTeachingMethods(Arrays.asList(
                "Pattern implementation",
                "Architecture development",
                "Code reviews"
        ));

        TeachingConditions labConditions = new TeachingConditions();
        labConditions.setLocation("Software Lab");
        labConditions.setRequirements(Arrays.asList(
                "Git installed",
                "IDE with UML plugins"
        ));
        labConditions.setPlatforms(new ArrayList<>());
        lab.setConditions(labConditions);

        return Arrays.asList(course, lab);
    }


    private List<ContentModule> createCourseContent(Discipline discipline) {
        ContentModule module1 = new ContentModule();
        module1.setTitle("Architectural Patterns and Styles");
        module1.setDescription("Understanding architectural patterns, styles, and their applications");
        module1.setTeachingMethods(Arrays.asList("Lecture", "Case Studies"));
        module1.setHours(6);
        module1.setWeekNumber(1);
        module1.setDiscipline(discipline);
        module1.setType(ContentModuleType.COURSE);

        ContentModule module2 = new ContentModule();
        module2.setTitle("Enterprise Design Patterns");
        module2.setDescription("Implementation of enterprise-level design patterns");
        module2.setTeachingMethods(Arrays.asList("Workshop", "Code Review"));
        module2.setHours(8);
        module2.setWeekNumber(2);
        module2.setDiscipline(discipline);
        module2.setType(ContentModuleType.COURSE);

        return Arrays.asList(module1, module2);
    }

    private List<ContentModule> createLabContent(Discipline discipline) {
        ContentModule module = new ContentModule();
        module.setTitle("Setting up the Development Environment");
        module.setDescription("Installing and configuring necessary tools");
        module.setTeachingMethods(Arrays.asList("Guided practice", "Individual work"));
        module.setHours(4);
        module.setWeekNumber(1);
        module.setDiscipline(discipline);
        module.setType(ContentModuleType.LABORATORY);

        return Collections.singletonList(module);
    }

    private List<LearningOutcome> createLearningOutcomes() {
        LearningOutcome knowledge = new LearningOutcome();
        knowledge.setCategory(LearningOutcomeCategory.KNOWLEDGE);
        knowledge.setDescription("Understanding modern web development principles");
        knowledge.setOutcomes(Arrays.asList(
                "Understand key concepts of web development",
                "Know best practices in frontend development"
        ));

        LearningOutcome skills = new LearningOutcome();
        skills.setCategory(LearningOutcomeCategory.SKILLS);
        skills.setDescription("Practical web development skills");
        skills.setOutcomes(Arrays.asList(
                "Build responsive web applications",
                "Implement modern frontend architectures"
        ));

        return Arrays.asList(knowledge, skills);
    }

    private WeeklyHours createWeeklyHours() {
        WeeklyHours weeklyHours = new WeeklyHours();
        weeklyHours.setCourse(2);
        weeklyHours.setLaboratory(2);
        weeklyHours.setSeminar(0);
        weeklyHours.setProject(0);
        weeklyHours.setTotal(4);
        return weeklyHours;
    }

    private EvaluationSystem createEvaluationSystem() {
        EvaluationSystem system = new EvaluationSystem();

        EvaluationComponent courseEval = new EvaluationComponent();
        courseEval.setType(TeachingActivityType.COURSE);
        courseEval.setEvaluationCriteria(Arrays.asList(
                "Understanding of theoretical concepts",
                "Application of principles"
        ));
        courseEval.setEvaluationMethods(Collections.singletonList("Written exam"));
        courseEval.setWeightInFinalGrade(50);
        courseEval.setMinimumGrade(5);
        courseEval.setDescription("Final written examination");

        EvaluationComponent labEval = new EvaluationComponent();
        labEval.setType(TeachingActivityType.LABORATORY);
        labEval.setEvaluationCriteria(Arrays.asList(
                "Project implementation quality",
                "Code organization"
        ));
        labEval.setEvaluationMethods(Arrays.asList("Project evaluation", "Code review"));
        labEval.setWeightInFinalGrade(50);
        labEval.setMinimumGrade(5);
        labEval.setDescription("Project development and presentation");

        system.setComponents(Arrays.asList(courseEval, labEval));
        system.setMinimumRequirements(Arrays.asList(
                "Minimum grade of 5 for each evaluation component",
                "Attendance at 80% of laboratory sessions"
        ));
        system.setAdditionalNotes("Students must submit all required projects");
        system.setMakeupExamConditions(Arrays.asList(
                "Available for students who failed the regular examination",
                "Covers entire course content"
        ));

        return system;
    }
}

