package fmi.enroll.dto;

import fmi.enroll.domain.ContentModule;
import fmi.enroll.domain.Discipline;
import fmi.enroll.domain.LearningOutcome;
import fmi.enroll.domain.TeachingActivity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class DisciplineWithDetails {
    private Discipline discipline;
    private List<ContentModule> courseContent;
    private List<ContentModule> seminarContent;
    private List<ContentModule> laboratoryContent;
    private List<TeachingActivity> teachingActivities;
    private Set<LearningOutcome> learningOutcomes;
}