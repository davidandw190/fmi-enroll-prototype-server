package fmi.enroll.mappers;

import fmi.enroll.domain.Discipline;
import fmi.enroll.dto.DisciplineResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        TeachingActivityMapper.class,
        TeacherMapper.class,
        TeachingConditionsMapper.class,
        ContentModuleMapper.class,
        BibliographyMapper.class,
        EvaluationSystemMapper.class
})
public interface DisciplineMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(discipline.getId()))")
    @Mapping(target = "teachingActivities", source = "teachingActivities")
    @Mapping(target = "timeAllocation", source = "timeAllocation")
    @Mapping(target = "weeklyHours", source = "weeklyHours")
    @Mapping(target = "courseContent", source = "courseContent")
    @Mapping(target = "seminarContent", source = "seminarContent")
    @Mapping(target = "laboratoryContent", source = "laboratoryContent")
    @Mapping(target = "bibliography", source = "bibliography")
    @Mapping(target = "prerequisites", source = "prerequisites")
    @Mapping(target = "learningOutcomes", source = "learningOutcomes")
    @Mapping(target = "evaluationSystem", source = "evaluationSystem")
    DisciplineResponse toResponse(Discipline discipline);
}