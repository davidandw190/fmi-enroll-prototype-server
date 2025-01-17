package fmi.enroll.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BibliographyEntryResponse {
    private String title;
    private String authors;
    private Integer publicationYear;
    private String isbn;
    private String url;
    private String type;
}