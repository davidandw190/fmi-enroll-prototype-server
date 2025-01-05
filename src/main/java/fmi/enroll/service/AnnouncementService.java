package fmi.enroll.service;

import fmi.enroll.domain.Announcement;
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

    public Page<Announcement> findAnnouncements(Boolean importantOnly, Pageable pageable) {
        if (Boolean.TRUE.equals(importantOnly)) {
            return announcementRepository.findByImportantTrueOrderByDateDesc(pageable);
        }
        return announcementRepository.findAllByOrderByDateDesc(pageable);
    }
}
