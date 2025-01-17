package fmi.enroll.mappers;


import fmi.enroll.domain.TeachingConditions;
import fmi.enroll.dto.TeachingConditionsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeachingConditionsMapper {
    TeachingConditionsResponse toResponse(TeachingConditions conditions);
}