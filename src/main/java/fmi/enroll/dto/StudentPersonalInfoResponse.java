package fmi.enroll.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentPersonalInfoResponse {
    private String cnp;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
    private String citizenship;
}