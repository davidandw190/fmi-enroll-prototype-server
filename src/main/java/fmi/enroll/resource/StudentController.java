package fmi.enroll.resource;

import fmi.enroll.dto.StudentResponse;
import fmi.enroll.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/profile")
    public StudentResponse getStudentProfile(@AuthenticationPrincipal Jwt jwt) {
        return studentService.getStudentProfile(jwt);
    }
}