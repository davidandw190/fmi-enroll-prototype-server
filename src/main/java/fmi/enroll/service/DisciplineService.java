package fmi.enroll.service;

import fmi.enroll.domain.Discipline;
import fmi.enroll.dto.DisciplineResponse;
import fmi.enroll.mappers.DisciplineMapper;
import fmi.enroll.repository.DisciplineRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DisciplineService {
    private static final Logger log = LoggerFactory.getLogger(DisciplineService.class);

    private final DisciplineRepository disciplineRepository;
    private final DisciplineMapper disciplineMapper;


    public List<DisciplineResponse> findDisciplinesWithDetails(List<Long> disciplineIds) {
        if (disciplineIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Discipline> disciplines = disciplineRepository.findAllByIdWithDetails(disciplineIds);

        log.debug("Fetched {} disciplines with details", disciplines.size());

        return disciplines.stream()
                .map(disciplineMapper::toResponse)
                .collect(Collectors.toList());
    }

}