package fmi.enroll.repository;


import fmi.enroll.domain.DisciplinePacket;
import fmi.enroll.domain.EnrollmentPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentPeriodRepository extends JpaRepository<EnrollmentPeriod, Long> {
    @Query("""
        SELECT DISTINCT e FROM EnrollmentPeriod e
        LEFT JOIN e.targetSpecializations specs
        WHERE e.targetYearOfStudy = :targetYearOfStudy
        AND e.targetSemester = :targetSemester
        AND (
            :specialization IS NULL
            OR EXISTS (
                SELECT 1 FROM e.targetSpecializations s
                WHERE LOWER(s) = LOWER(:specialization)
                OR s = 'All Specializations'
            )
        )
    """)
    List<EnrollmentPeriod> findEligibleEnrollmentPeriods(
            @Param("targetYearOfStudy") Integer targetYearOfStudy,
            @Param("targetSemester") Integer targetSemester,
            @Param("specialization") String specialization
    );

    @Query("""
        SELECT e FROM EnrollmentPeriod e
        JOIN FETCH e.packets p
        LEFT JOIN FETCH p.prerequisites
        WHERE e.id = :periodId
        AND e.type = 'ELECTIVE_DISCIPLINES'
    """)
    Optional<EnrollmentPeriod> findPacketsWithDisciplines(@Param("periodId") Long periodId);

    @Query("""
        SELECT e FROM EnrollmentPeriod e
        LEFT JOIN FETCH e.packets p
        LEFT JOIN FETCH p.prerequisites
        WHERE e.id = :periodId
        AND e.type = 'ELECTIVE_DISCIPLINES'
    """)
    Optional<EnrollmentPeriod> findElectiveDisciplinesPeriod(@Param("periodId") Long periodId);
}