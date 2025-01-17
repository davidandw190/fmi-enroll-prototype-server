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
public class ContentModuleResponse {
    private String title;
    private String description;
    private List<String> teachingMethods;
    private Integer hours;
    private Integer weekNumber;
    private List<BibliographyEntryResponse> bibliographyEntries;
    private String type;
}