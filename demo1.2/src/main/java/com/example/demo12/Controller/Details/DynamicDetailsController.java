package com.example.demo12.Controller.Details;


import com.example.demo12.Model.Mark.Marks;
import com.example.demo12.Model.details.Student;
import com.example.demo12.Request.Details.LoginRequest;
import com.example.demo12.Response.Details.LoginResponse;
import com.example.demo12.Response.Details.DetailsResponse;
import com.example.demo12.Service.Details.DynamicDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class DynamicDetailsController {

    @Autowired
    DynamicDetailsService dynamicDetailsService;

    @PostMapping("/validate_student")
    public ResponseEntity<?> validateU(@RequestBody LoginRequest loginRequest) throws Exception {
        if (loginRequest.getEmail() == null || !loginRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        try {
            DetailsResponse detailsResponse = dynamicDetailsService.getStudentDetails(loginRequest);
            return ResponseEntity.ok(detailsResponse);
        } catch (Exception e) {
            DetailsResponse response = new DetailsResponse();
            response.setStudents(null); // Or set an empty list if needed
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/validate_manager")
    public ResponseEntity<?> validateM(@RequestBody LoginRequest loginRequest) throws Exception {
        if (loginRequest.getEmail() == null || !loginRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        try {
            DetailsResponse detailsResponse = dynamicDetailsService.getManagerDetails(loginRequest);
            return ResponseEntity.ok(detailsResponse);
        } catch (Exception e) {
            DetailsResponse response = new DetailsResponse();
            response.setManagers(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);        }
    }
    @PostMapping("/validate_trainer")
    public ResponseEntity<?> validateT(@RequestBody LoginRequest loginRequest) throws Exception {
        if (loginRequest.getEmail() == null || !loginRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        try {
            DetailsResponse detailsResponse = dynamicDetailsService.getTrainerDetails(loginRequest);
            return ResponseEntity.ok(detailsResponse);
        } catch (Exception e) {
            DetailsResponse response = new DetailsResponse();
            response.setTrainers(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);        }
    }



    @GetMapping("/getid")
    public ResponseEntity<LoginResponse> getId() {
        LoginResponse response = new LoginResponse();
        List<String> ids = dynamicDetailsService.getStuId();
        response.setIds(ids);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/Trainer/{trainerId}")
    public LoginResponse getTopicsByCourseId(@PathVariable String trainerId) {
        List<String> stu = dynamicDetailsService.getStudentByTrainerId(trainerId);
        LoginResponse response = new LoginResponse();
        response.setMsg("Student retrieved successfully by Trainer ID: " + trainerId);
        response.setIds(stu);

        return response;    }


    @GetMapping("/students/{studentId}")
    public LoginResponse getMarkByStudentId(@PathVariable String studentId) {
        List<Marks> mark = dynamicDetailsService.getMarksByStudentId(studentId);
        LoginResponse response = new LoginResponse();
        response.setMsg("Marks retrieved successfully for Student ID: " + studentId);

        response.setMark(mark);

        return response;    }


    @GetMapping("/students_mark")
    public DetailsResponse getAllMark() {
        List<Student> stu = dynamicDetailsService.getAllMarks();
        DetailsResponse response = new DetailsResponse();
        response.setStudents(stu);

        return response;
    }
}
