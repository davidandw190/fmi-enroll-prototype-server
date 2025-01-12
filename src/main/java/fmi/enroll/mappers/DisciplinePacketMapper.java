package fmi.enroll.mappers;

import fmi.enroll.domain.DisciplinePacket;
import fmi.enroll.dto.DisciplinePacketResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PrerequisitesMapper.class})
public interface DisciplinePacketMapper {
    @Mapping(target = "id", source = "id")
    DisciplinePacketResponse toResponse(DisciplinePacket disciplinePacket);
}
