package com.example.demo12.Controller.Details;

import com.example.demo12.Model.Mark.marks;
import com.example.demo12.Model.details.manager;
import com.example.demo12.Model.details.student;
import com.example.demo12.Model.details.trainer;

import com.example.demo12.Request.Details.LoginRequest;
import com.example.demo12.Response.Details.LoginResponse;
import com.example.demo12.Response.DetailsResponse;
import com.example.demo12.Service.Details.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    @Autowired
    LoginService loginService;


    @PostMapping("/validate_student")
    public ResponseEntity<?> validateU(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            DetailsResponse detailsResponse = loginService.getStudentDetails(loginRequest);
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
            DetailsResponse detailsResponse = loginService.getManagerDetails(loginRequest);
            return ResponseEntity.ok(detailsResponse);
        } catch (Exception e) {
            DetailsResponse response = new DetailsResponse();
            response.setStudents(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);        }
    }
    @PostMapping("/validate_trainer")
    public ResponseEntity<?> validateT(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            DetailsResponse detailsResponse = loginService.getTrainerDetails(loginRequest);
            return ResponseEntity.ok(detailsResponse);
        } catch (Exception e) {
            DetailsResponse response = new DetailsResponse();
            response.setStudents(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);        }
    }

//    @PostMapping("/validate_trainer")
//    public ResponseEntity<?> validateT(@RequestBody LoginRequest loginRequest) throws Exception {
//        try {
//            LoginResponse loginResponse = loginService.validateTrainer(loginRequest);
//            return ResponseEntity.ok(loginResponse);
//        } catch (Exception e) {
//            LoginResponse response = new LoginResponse();
//            response.setMsg(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);        }
//    }



//    @PostMapping("/student_verify")
//    public ResponseEntity<String> verifyEmail(@RequestBody LoginRequest request) {
//        String email = request.getEmail();
//        Login user = loginService.findByEmail(email);
//        if (user != null) {
//            return ResponseEntity.ok("Email exists, you can reset the password.");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
//        }
//    }

    @PostMapping("/student_verify")
    public ResponseEntity<LoginResponse> verifyEmail(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        student user = loginService.findByEmailStudent(email);
        LoginResponse response = new LoginResponse();

        if (user != null) {
            response.setMsg("Email exists, you can reset the password.");
            response.setEmail(email);
            return ResponseEntity.ok(response);  // Return response as JSON
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);  // Return response as JSON
        }
    }

    // Endpoint to update student password
    @PostMapping("/update_student_password")
    public ResponseEntity<LoginResponse> updatePassword(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String newPassword = request.getPassword();
        student user = loginService.findByEmailStudent(email);

        LoginResponse response = new LoginResponse();  // Create response object
        if (user != null) {
            loginService.updateStudentPassword(user, newPassword);
            response.setMsg("Password updated successfully.");
            response.setEmail(email);  // Optionally include email in the response
            return ResponseEntity.ok(response);  // Return as JSON
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);  // Return as JSON
        }
    }


    // Endpoint to verify manager email
    @PostMapping("/manager_verify")
    public ResponseEntity<LoginResponse> verifyEmailm(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        manager user = loginService.findByEmailManager(email);
        LoginResponse response = new LoginResponse();

        if (user != null) {
            response.setMsg("Email exists, you can reset the password.");
            response.setEmail(email);
            return ResponseEntity.ok(response);  // Return response as JSON
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);  // Return response as JSON
        }
    }

    // Endpoint to update manager password
    @PostMapping("/update_manager_password")
    public ResponseEntity<LoginResponse> updatePasswordm(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String newPassword = request.getPassword();
        manager user = loginService.findByEmailManager(email);

        LoginResponse response = new LoginResponse();  // Create response object
        if (user != null) {
            loginService.updateManagerPassword(user, newPassword);
            response.setMsg("Password updated successfully.");
            response.setEmail(email);  // Optionally include email in the response
            return ResponseEntity.ok(response);  // Return as JSON
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);  // Return as JSON
        }
    }


    @PostMapping("/trainer_verify")
    public ResponseEntity<LoginResponse> verifyEmailt(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        trainer user = loginService.findByEmailTrainer(email);
        LoginResponse response = new LoginResponse();

        if (user != null) {
            response.setMsg("Email exists, you can reset the password.");
            response.setEmail(email);
            return ResponseEntity.ok(response);  // Return response as JSON
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);  // Return response as JSON
        }
    }

    // Endpoint to update manager password
    @PostMapping("/update_trainer_password")
    public ResponseEntity<LoginResponse> updatePasswordt(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String newPassword = request.getPassword();
        trainer user = loginService.findByEmailTrainer(email);

        LoginResponse response = new LoginResponse();  // Create response object
        if (user != null) {
            loginService.updateTrainerPassword(user, newPassword);
            response.setMsg("Password updated successfully.");
            response.setEmail(email);  // Optionally include email in the response
            return ResponseEntity.ok(response);  // Return as JSON
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);  // Return as JSON
        }
    }




    @GetMapping("/getid")
    public ResponseEntity<LoginResponse> getId() {
        // Create a response object
        LoginResponse response = new LoginResponse();

        // Retrieve a list of student IDs from the service
        List<String> ids = loginService.getStuId();  // Assuming student IDs are of type Long

        // Set the IDs into the response object
        response.setIds(ids);

        // Return the response entity with the list of IDs
        return ResponseEntity.ok(response);
    }


    @GetMapping("/trainer/{trainerId}")
    public LoginResponse getTopicsByCourseId(@PathVariable String trainerId) {
        List<String> stu = loginService.getStudentByTrainerId(trainerId);

        // Create a response object
        LoginResponse response = new LoginResponse();
        response.setMsg("Student retrieved successfully by trainer ID: " + trainerId);

        // You can also add the list of topics or any other details as needed in the response
        response.setIds(stu);  // Assuming you added a `topics` field in `LoginResponse`

        return response;    }


    @GetMapping("/students/{studentId}")
    public LoginResponse getMarkByStudentId(@PathVariable String studentId) {
        List<marks> mark = loginService.getMarksByStudentId(studentId);

        // Create a response object
        LoginResponse response = new LoginResponse();
        response.setMsg("Marks retrieved successfully for student ID: " + studentId);

        response.setMarks(mark);

        return response;    }


    @GetMapping("/students_mark")
    public DetailsResponse getAllMark() {
        List<student> stu = loginService.getAllMarks();

        // Create a response object
        DetailsResponse response = new DetailsResponse();
        response.setStudents(stu);

        return response;
    }

}


