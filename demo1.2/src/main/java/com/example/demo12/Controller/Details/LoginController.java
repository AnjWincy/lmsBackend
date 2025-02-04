package com.example.demo12.Controller.Details;

import com.example.demo12.Model.details.Manager;
import com.example.demo12.Model.details.Student;
import com.example.demo12.Model.details.Trainer;

import com.example.demo12.Request.Details.LoginRequest;
import com.example.demo12.Response.Details.LoginResponse;
import com.example.demo12.Service.Details.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    @Autowired
    LoginService loginService;




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
        Student user = loginService.findByEmailStudent(email);
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

    // Endpoint to update Student password
    @PostMapping("/update_student_password")
    public ResponseEntity<LoginResponse> updatePassword(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String newPassword = request.getPassword();
        Student user = loginService.findByEmailStudent(email);

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


    // Endpoint to verify Manager email
    @PostMapping("/manager_verify")
    public ResponseEntity<LoginResponse> verifyEmailm(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        Manager user = loginService.findByEmailManager(email);
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

    // Endpoint to update Manager password
    @PostMapping("/update_manager_password")
    public ResponseEntity<LoginResponse> updatePasswordm(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String newPassword = request.getPassword();
        Manager user = loginService.findByEmailManager(email);

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
        Trainer user = loginService.findByEmailTrainer(email);
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

    // Endpoint to update Manager password
    @PostMapping("/update_trainer_password")
    public ResponseEntity<LoginResponse> updatePasswordt(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String newPassword = request.getPassword();
        Trainer user = loginService.findByEmailTrainer(email);

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




    private final Map<String, LoginRequest> verificationCodes = new HashMap<>();

    @PostMapping("/request")
    public ResponseEntity<?> requestPasswordReset(@RequestBody LoginRequest request) throws Exception {
        String email = request.getEmail();
        String role = request.getRole();  // Get role (e.g., Student, Manager, Trainer)
        LoginResponse response = new LoginResponse();

        // Check if the email exists based on the role
        if (isUserExist(email, role)) {
            // Generate the verification code and store it in the map with timestamp
            String verificationCode = loginService.generateVerificationCode();
            request.setCode(verificationCode);
            request.setTimestamp(System.currentTimeMillis());  // Store the timestamp
            verificationCodes.put(email, request);

            // Send the verification code via email
            loginService.sendVerificationCode(email, verificationCode);

            response.setMsg("Verification code sent to your email.");
            return ResponseEntity.ok(response);
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    private boolean isUserExist(String email, String role) {
        switch (role.toLowerCase()) {
            case "student":
                return loginService.findByEmailStudent(email) != null;
            case "manager":
                return loginService.findByEmailManager(email) != null;
            case "trainer":
                return loginService.findByEmailTrainer(email) != null;
            default:
                return false;
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String inputCode = request.getCode();
        LoginResponse response = new LoginResponse();

        // Retrieve the stored verification code object for the email
        LoginRequest storedRequest = verificationCodes.get(email);

        if (storedRequest != null) {
            // Check if the verification code has expired
            long expiryTime = 5 * 60 * 1000; // 5 minutes in milliseconds
            if (System.currentTimeMillis() - storedRequest.getTimestamp() > expiryTime) {
                verificationCodes.remove(email); // Remove expired code
                response.setMsg("Verification code expired.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Check if the entered code matches the stored code
            if (storedRequest.getCode().equals(inputCode)) {
                response.setMsg("Code verified. You can now reset your password.");
                return ResponseEntity.ok(response);
            } else {
                response.setMsg("Invalid verification code.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } else {
            response.setMsg("Verification code not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}


