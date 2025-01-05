package fmi.enroll.mappers;

import fmi.enroll.domain.Announcement;
import fmi.enroll.dto.AnnouncementResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class AnnouncementMapper {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public AnnouncementResponse toResponse(Announcement announcement) {
        if (announcement == null) {
            return null;
        }

        return AnnouncementResponse.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .important(announcement.getImportant())
                .date(announcement.getDate())
                .createdAt(announcement.getCreatedAt())
                .updatedAt(announcement.getUpdatedAt())
                .build();
    }

    private String formatDate(LocalDateTime date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    public List<AnnouncementResponse> toResponseList(List<Announcement> announcements) {
        if (announcements == null) {
            return Collections.emptyList();
        }

        return announcements.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<AnnouncementResponse> toResponsePage(Page<Announcement> announcementPage) {
        if (announcementPage == null) {
            return Page.empty();
        }

        return announcementPage.map(this::toResponse);
    }
}