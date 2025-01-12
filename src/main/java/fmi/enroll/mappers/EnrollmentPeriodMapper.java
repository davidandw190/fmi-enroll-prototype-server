package fmi.enroll.mappers;

import fmi.enroll.domain.EnrollmentPeriod;
import fmi.enroll.dto.EnrollmentPeriodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DisciplinePacketMapper.class})
public interface EnrollmentPeriodMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(enrollmentPeriod.getId()))")
    EnrollmentPeriodResponse toResponse(EnrollmentPeriod enrollmentPeriod);
}
