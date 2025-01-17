package fmi.enroll.mappers;

import fmi.enroll.domain.Bibliography;
import fmi.enroll.domain.BibliographyEntry;
import fmi.enroll.dto.BibliographyEntryResponse;
import fmi.enroll.dto.BibliographyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BibliographyMapper {
    BibliographyResponse toResponse(Bibliography bibliography);

    @Mapping(target = "type", expression = "java(entry.getType().toString())")
    BibliographyEntryResponse toEntryResponse(BibliographyEntry entry);

    default List<BibliographyEntryResponse> toEntryResponseList(List<BibliographyEntry> entries) {
        if (entries == null) return new ArrayList<>();
        return entries.stream()
                .map(this::toEntryResponse)
                .collect(Collectors.toList());
    }
}
