package fmi.enroll.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PrerequisitesResponse {
    private List<String> requiredSkills;
    private List<String> recommendations;
}