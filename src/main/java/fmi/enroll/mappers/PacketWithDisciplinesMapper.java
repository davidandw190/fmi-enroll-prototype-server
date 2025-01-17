package fmi.enroll.mappers;

import fmi.enroll.domain.Discipline;
import fmi.enroll.domain.DisciplinePacket;
import fmi.enroll.dto.DisciplineResponse;
import fmi.enroll.dto.PacketWithDisciplinesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DisciplineMapper.class, DisciplinePacketMapper.class})
public interface PacketWithDisciplinesMapper {
    @Mapping(target = "packet", source = "packet")
    @Mapping(target = "disciplines", source = "disciplineResponses")
    PacketWithDisciplinesResponse mapToResponse(
            DisciplinePacket packet,
            List<DisciplineResponse> disciplineResponses
    );
}