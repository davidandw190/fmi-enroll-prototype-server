package fmi.enroll.repository;

import fmi.enroll.domain.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    @Query("""
        SELECT DISTINCT d FROM Discipline d
        LEFT JOIN FETCH d.teachingActivities ta
        LEFT JOIN FETCH ta.teacher
        LEFT JOIN FETCH d.weeklyHours
        LEFT JOIN FETCH d.prerequisites
        WHERE d.id IN :disciplineIds
    """)
    List<Discipline> findAllByIdWithDetails(@Param("disciplineIds") List<Long> disciplineIds);
}