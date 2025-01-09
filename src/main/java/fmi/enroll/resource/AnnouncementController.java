package fmi.enroll.resource;

import fmi.enroll.dto.AnnouncementResponse;
import fmi.enroll.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<Page<AnnouncementResponse>> getAnnouncements(
            @RequestParam(required = false) Boolean importantOnly,
            @PageableDefault(
                    size = 3,
                    sort = "date",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        Page<AnnouncementResponse> announcements = announcementService
                .findAnnouncements(importantOnly, pageable);

        return ResponseEntity.ok(announcements);
    }
}