package fmi.enroll.service;

import fmi.enroll.domain.Announcement;
import fmi.enroll.dto.AnnouncementResponse;
import fmi.enroll.mappers.AnnouncementMapper;
import fmi.enroll.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;

    public Page<AnnouncementResponse> findAnnouncements(Boolean importantOnly, Pageable pageable) {
        Page<Announcement> announcementPage;
        if (Boolean.TRUE.equals(importantOnly)) {
            announcementPage = announcementRepository.findByImportantTrueOrderByDateDesc(pageable);
        } else {
            announcementPage = announcementRepository.findAllByOrderByDateDesc(pageable);
        }

        return announcementPage.map(announcementMapper::toResponse);
    }
}