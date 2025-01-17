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
public class TeachingConditionsResponse {
    private String location;
    private List<String> requirements;
    private List<TeachingPlatformResponse> platforms;
}