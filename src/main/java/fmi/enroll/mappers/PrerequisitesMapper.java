package fmi.enroll.mappers;

import fmi.enroll.domain.Prerequisites;
import fmi.enroll.domain.RequiredDiscipline;
import fmi.enroll.dto.PrerequisitesResponse;
import fmi.enroll.dto.RequiredDisciplineResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrerequisitesMapper {
    PrerequisitesResponse toResponse(Prerequisites prerequisites);
    RequiredDisciplineResponse toResponse(RequiredDiscipline requiredDiscipline);
}