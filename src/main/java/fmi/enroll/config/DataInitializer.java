package fmi.enroll.config;

import fmi.enroll.domain.Announcement;
import fmi.enroll.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("!prod")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final AnnouncementRepository announcementRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (announcementRepository.count() == 0) {
            log.info("Initializing announcement data...");
            List<Announcement> initialAnnouncements = createInitialAnnouncements();
            announcementRepository.saveAll(initialAnnouncements);
            log.info("Finished initializing {} announcements", initialAnnouncements.size());
        } else {
            log.info("Announcement data already exists, skipping initialization");
        }
    }

    private List<Announcement> createInitialAnnouncements() {
        Announcement announcement1 = new Announcement();
        announcement1.setTitle("Spring Enrollment Period Update");
        announcement1.setContent("The enrollment period for the next semester will start on May 1st, 2024.");
        announcement1.setImportant(true);
        announcement1.setDate(LocalDateTime.of(2024, 4, 12, 10, 0));

        Announcement announcement2 = new Announcement();
        announcement2.setTitle("Elective Courses Available");
        announcement2.setContent("You can now check out the new elective courses for the upcoming semester.");
        announcement2.setImportant(false);
        announcement2.setDate(LocalDateTime.of(2024, 3, 10, 14, 30));

        return Arrays.asList(announcement1, announcement2);
    }
}