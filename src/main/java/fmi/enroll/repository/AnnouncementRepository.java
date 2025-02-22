package fmi.enroll.repository;

import fmi.enroll.domain.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String> {

    Page<Announcement> findAllByOrderByDateDesc(Pageable pageable);

    Page<Announcement> findByImportantTrueOrderByDateDesc(Pageable pageable);
}