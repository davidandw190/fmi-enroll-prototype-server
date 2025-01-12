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
public class MockAnnouncementInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(MockAnnouncementInitializer.class);
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
        announcement1.setDate(LocalDateTime.of(2025, 1, 10, 9, 20));

        Announcement announcement2 = new Announcement();
        announcement2.setTitle("Elective Courses Available");
        announcement2.setContent("You can now check out the new elective courses for the upcoming semester.");
        announcement2.setImportant(false);
        announcement2.setDate(LocalDateTime.of(2025, 1, 4, 14, 30));

        Announcement announcement3 = getAnnouncement();

        Announcement announcement4 = new Announcement();
        announcement4.setTitle("Another Mock Annoucement");
        announcement4.setContent("You can now check out the new elective courses for the upcoming semester. Lorem ipsum odor amet, consectetuer adipiscing elit. Dignissim penatibus justo volutpat vitae himenaeos sollicitudin maximus semper. Congue dignissim turpis scelerisque facilisi libero ridiculus erat. Natoque ultrices leo euismod egestas sociosqu. Potenti felis donec lacus mauris torquent sodales orci. Odio molestie porttitor suscipit efficitur aliquam vivamus fusce. Senectus convallis malesuada neque congue dictum facilisi leo. Finibus scelerisque parturient dignissim; purus phasellus aenean dictum.");
        announcement4.setImportant(true);
        announcement4.setDate(LocalDateTime.of(2024, 9, 7, 14, 33));

        Announcement announcement5 = new Announcement();
        announcement5.setTitle("Yet Another Mock Annoucement");
        announcement5.setContent("You can now check out the new elective courses for the upcoming semester. Lorem ipsum odor amet, consectetuer adipiscing elit. Dignissim penatibus justo volutpat vitae himenaeos sollicitudin maximus semper. Congue dignissim turpis scelerisque facilisi libero ridiculus erat. Natoque ultrices leo euismod egestas sociosqu. Potenti felis donec lacus mauris torquent sodales orci. Odio molestie porttitor suscipit efficitur aliquam vivamus fusce. Senectus convallis malesuada neque congue dictum facilisi leo. Finibus scelerisque parturient dignissim; purus phasellus aenean dictum.");
        announcement5.setImportant(true);
        announcement5.setDate(LocalDateTime.of(2024, 8, 3, 11, 20));

        return Arrays.asList(announcement1, announcement2, announcement3, announcement4, announcement5);
    }

    private static Announcement getAnnouncement() {
        Announcement announcement3 = new Announcement();
        announcement3.setTitle("Thesis Topics Available");
        announcement3.setContent("You can now check out the new elective courses for the upcoming semester. Lorem ipsum odor amet, consectetuer adipiscing elit. Dignissim penatibus justo volutpat vitae himenaeos sollicitudin maximus semper. Congue dignissim turpis scelerisque facilisi libero ridiculus erat. Natoque ultrices leo euismod egestas sociosqu. Potenti felis donec lacus mauris torquent sodales orci. Odio molestie porttitor suscipit efficitur aliquam vivamus fusce. Senectus convallis malesuada neque congue dictum facilisi leo. Finibus scelerisque parturient dignissim; purus phasellus aenean dictum.");
        announcement3.setImportant(false);
        announcement3.setDate(LocalDateTime.of(2024, 12, 17, 14, 30));
        return announcement3;
    }
}