package fmi.enroll.dto;

import fmi.enroll.enums.discipline.AssessmentType;
import fmi.enroll.enums.discipline.DisciplineType;
import fmi.enroll.enums.discipline.TeachingLanguage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineResponse {
    private String id;
    private String code;
    private String name;
    private String description;
    private DisciplineType type;
    private Integer semester;
    private Integer yearOfStudy;
    private Integer credits;
    private AssessmentType assessmentType;
    private TeachingLanguage language;
    private List<TeachingActivityResponse> teachingActivities;
    private TimeAllocationResponse timeAllocation;
    private WeeklyHoursResponse weeklyHours;
    private List<ContentModuleResponse> courseContent;
    private List<ContentModuleResponse> seminarContent;
    private List<ContentModuleResponse> laboratoryContent;
    private BibliographyResponse bibliography;
    private PrerequisitesResponse prerequisites;
    private List<LearningOutcomeResponse> learningOutcomes;
    private EvaluationSystemResponse evaluationSystem;
    private Integer maxEnrollmentSpots;
    private Integer currentEnrollmentCount;
    private Integer waitlistLimit;
}
