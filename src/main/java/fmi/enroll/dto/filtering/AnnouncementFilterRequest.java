package fmi.enroll.dto.filtering;

import lombok.Data;

@Data
public class AnnouncementFilterRequest {
    private Integer page = 0;
    private Integer size = 10;
    private String sort = "date,desc";
    private Boolean importantOnly = false;
}
