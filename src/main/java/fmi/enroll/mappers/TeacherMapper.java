package fmi.enroll.mappers;

import fmi.enroll.domain.Teacher;
import fmi.enroll.dto.TeacherResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherResponse toResponse(Teacher teacher);
}
