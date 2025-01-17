package fmi.enroll.mappers;

import fmi.enroll.domain.TeachingActivity;
import fmi.enroll.dto.TeachingActivityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeachingActivityMapper {
    @Mapping(target = "type", expression = "java(activity.getType().toString())")
    TeachingActivityResponse toResponse(TeachingActivity activity);

    default List<TeachingActivityResponse> toResponseList(List<TeachingActivity> activities) {
        if (activities == null) return new ArrayList<>();
        return activities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}