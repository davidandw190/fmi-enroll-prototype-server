package fmi.enroll.dto;

import fmi.enroll.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String universityId;
    private String email;
    private String group;
    private int subgroup;
    private String specialization;
    private int yearOfStudy;
    private int semester;
    private StudentStatus status;
    private StudentPersonalInfoResponse personalInfo;
    private Date createdAt;
    private Date updatedAt;
}