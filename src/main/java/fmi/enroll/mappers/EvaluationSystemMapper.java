package fmi.enroll.mappers;

import fmi.enroll.domain.EvaluationComponent;
import fmi.enroll.domain.EvaluationSystem;
import fmi.enroll.dto.EvaluationComponentResponse;
import fmi.enroll.dto.EvaluationSystemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EvaluationSystemMapper {
    EvaluationSystemResponse toResponse(EvaluationSystem system);

    @Mapping(target = "type", expression = "java(component.getType().toString())")
    EvaluationComponentResponse toComponentResponse(EvaluationComponent component);
}