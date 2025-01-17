package fmi.enroll.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BibliographyResponse {
    private List<BibliographyEntryResponse> required;
    private List<BibliographyEntryResponse> recommended;
    private List<BibliographyEntryResponse> online;
}