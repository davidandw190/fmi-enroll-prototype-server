package fmi.enroll.mappers;

import fmi.enroll.domain.LearningOutcome;
import fmi.enroll.dto.LearningOutcomeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LearningOutcomeMapper {
    @Mapping(target = "category", expression = "java(outcome.getCategory().name())")
    LearningOutcomeResponse toResponse(LearningOutcome outcome);

    default List<LearningOutcomeResponse> toResponseList(List<LearningOutcome> outcomes) {
        if (outcomes == null) return new ArrayList<>();
        return outcomes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}