package fmi.enroll.service;

import fmi.enroll.dto.StudentResponse;
import fmi.enroll.dto.StudentPersonalInfoResponse;
import fmi.enroll.enums.StudentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    public StudentResponse getStudentProfile(Jwt jwt) {
        log.debug("Getting student profile for user: {}", jwt.getSubject());

        Map<String, Object> claims = jwt.getClaims();
        Map<String, List<String>> attributes = (Map<String, List<String>>) claims.getOrDefault("attributes", Map.of());

        String firstName = (String) claims.getOrDefault("given_name", "");
        String lastName = (String) claims.getOrDefault("family_name", "");
        String email = (String) claims.getOrDefault("email", "");

        String universityId = getFirstAttribute(attributes, "universityId", "Unknown");
        String group = getFirstAttribute(attributes, "group", "1");
        int subgroup = Integer.parseInt(getFirstAttribute(attributes, "subgroup", "1"));
        String specialization = getFirstAttribute(attributes, "specialization", "Computer Science");
        int yearOfStudy = Integer.parseInt(getFirstAttribute(attributes, "yearOfStudy", "1"));
        int semester = Integer.parseInt(getFirstAttribute(attributes, "semester", "1"));
        StudentStatus status = StudentStatus.valueOf(getFirstAttribute(attributes, "status", "ENROLLED"));

        // Personal info
        String cnp = getFirstAttribute(attributes, "cnp", null);
        String birthDateStr = getFirstAttribute(attributes, "birthDate", null);
        LocalDate birthDate = birthDateStr != null
                ? LocalDate.parse(birthDateStr)
                : LocalDate.now();
        String phoneNumber = getFirstAttribute(attributes, "phoneNumber", null);
        String address = getFirstAttribute(attributes, "address", null);
        String citizenship = getFirstAttribute(attributes, "citizenship", "Romanian");

        StudentPersonalInfoResponse personalInfo = StudentPersonalInfoResponse.builder()
                .cnp(cnp)
                .birthDate(birthDate)
                .phoneNumber(phoneNumber)
                .address(address)
                .citizenship(citizenship)
                .build();

        return StudentResponse.builder()
                .id(jwt.getSubject())
                .firstName(firstName)
                .lastName(lastName)
                .universityId(universityId)
                .email(email)
                .group(group)
                .subgroup(subgroup)
                .specialization(specialization)
                .yearOfStudy(yearOfStudy)
                .semester(semester)
                .status(status)
                .personalInfo(personalInfo)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    private String getFirstAttribute(Map<String, List<String>> attributes, String key, String defaultValue) {
        List<String> values = attributes.get(key);
        if (values == null || values.isEmpty()) {
            return defaultValue;
        }
        return values.get(0);
    }
}