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
        // Create a response object
        LoginResponse response = new LoginResponse();

        // Retrieve a list of Student IDs from the service
        List<String> ids = dynamicDetailsService.getStuId();  // Assuming Student IDs are of type Long

        // Set the IDs into the response object
        response.setIds(ids);

        // Return the response entity with the list of IDs
        return ResponseEntity.ok(response);
    }


    @GetMapping("/Trainer/{trainerId}")
    public LoginResponse getTopicsByCourseId(@PathVariable String trainerId) {
        List<String> stu = dynamicDetailsService.getStudentByTrainerId(trainerId);

        // Create a response object
        LoginResponse response = new LoginResponse();
        response.setMsg("Student retrieved successfully by Trainer ID: " + trainerId);

        // You can also add the list of Topics or any other details as needed in the response
        response.setIds(stu);  // Assuming you added a `Topics` field in `LoginResponse`

        return response;    }


    @GetMapping("/students/{studentId}")
    public LoginResponse getMarkByStudentId(@PathVariable String studentId) {
        List<Marks> mark = dynamicDetailsService.getMarksByStudentId(studentId);

        // Create a response object
        LoginResponse response = new LoginResponse();
        response.setMsg("Marks retrieved successfully for Student ID: " + studentId);

        response.setMark(mark);

        return response;    }


    @GetMapping("/students_mark")
    public DetailsResponse getAllMark() {
        List<Student> stu = dynamicDetailsService.getAllMarks();

        // Create a response object
        DetailsResponse response = new DetailsResponse();
        response.setStudents(stu);

        return response;
    }
}
