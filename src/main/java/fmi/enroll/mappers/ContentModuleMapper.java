package fmi.enroll.mappers;

import fmi.enroll.domain.ContentModule;
import fmi.enroll.dto.ContentModuleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ContentModuleMapper {
    @Mapping(target = "type", expression = "java(module.getType().toString())")
    ContentModuleResponse toResponse(ContentModule module);

    default List<ContentModuleResponse> toResponseList(List<ContentModule> modules) {
        if (modules == null) return new ArrayList<>();
        return modules.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}