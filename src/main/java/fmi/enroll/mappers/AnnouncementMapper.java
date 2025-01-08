package fmi.enroll.mappers;

import fmi.enroll.domain.Announcement;
import fmi.enroll.dto.AnnouncementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "important", source = "important")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    AnnouncementResponse toResponse(Announcement announcement);
}