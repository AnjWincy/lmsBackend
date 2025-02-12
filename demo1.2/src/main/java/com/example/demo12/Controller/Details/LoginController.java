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


    @PostMapping("/student_verify")
    public ResponseEntity<LoginResponse> verifyEmail(@RequestBody LoginRequest request) {
        if (request.getEmail() == null || !request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        String email = request.getEmail();
        Student user = loginService.findByEmailStudent(email);
        LoginResponse response = new LoginResponse();

        if (user != null) {
            response.setMsg("Email exists, you can reset the password.");
            response.setEmail(email);
            return ResponseEntity.ok(response);
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/update_student_password")
    public ResponseEntity<LoginResponse> updatePassword(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String newPassword = request.getPassword();
        Student user = loginService.findByEmailStudent(email);

        LoginResponse response = new LoginResponse();
        if (user != null) {
            loginService.updateStudentPassword(user, newPassword);
            response.setMsg("Password updated successfully.");
            response.setEmail(email);
            return ResponseEntity.ok(response);
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PostMapping("/manager_verify")
    public ResponseEntity<LoginResponse> verifyEmailm(@RequestBody LoginRequest request) {
        if (request.getEmail() == null || !request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        String email = request.getEmail();
        Manager user = loginService.findByEmailManager(email);
        LoginResponse response = new LoginResponse();

        if (user != null) {
            response.setMsg("Email exists, you can reset the password.");
            response.setEmail(email);
            return ResponseEntity.ok(response);
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/update_manager_password")
    public ResponseEntity<LoginResponse> updatePasswordm(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String newPassword = request.getPassword();
        Manager user = loginService.findByEmailManager(email);

        LoginResponse response = new LoginResponse();
        if (user != null) {
            loginService.updateManagerPassword(user, newPassword);
            response.setMsg("Password updated successfully.");
            response.setEmail(email);
            return ResponseEntity.ok(response);
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PostMapping("/trainer_verify")
    public ResponseEntity<LoginResponse> verifyEmailt(@RequestBody LoginRequest request) {
        if (request.getEmail() == null || !request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        String email = request.getEmail();
        Trainer user = loginService.findByEmailTrainer(email);
        LoginResponse response = new LoginResponse();

        if (user != null) {
            response.setMsg("Email exists, you can reset the password.");
            response.setEmail(email);
            return ResponseEntity.ok(response);
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/update_trainer_password")
    public ResponseEntity<LoginResponse> updatePasswordt(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String newPassword = request.getPassword();
        Trainer user = loginService.findByEmailTrainer(email);

        LoginResponse response = new LoginResponse();
        if (user != null) {
            loginService.updateTrainerPassword(user, newPassword);
            response.setMsg("Password updated successfully.");
            response.setEmail(email);
            return ResponseEntity.ok(response);
        } else {
            response.setMsg("Email not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }




    private final Map<String, LoginRequest> verificationCodes = new HashMap<>();

    @PostMapping("/request")
    public ResponseEntity<?> requestPasswordReset(@RequestBody LoginRequest request) throws Exception {
        if (request.getEmail() == null || !request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        String email = request.getEmail();
        String role = request.getRole();
        LoginResponse response = new LoginResponse();

        if (isUserExist(email, role)) {
            String verificationCode = loginService.generateVerificationCode();
            request.setCode(verificationCode);
            request.setTimestamp(System.currentTimeMillis());
            verificationCodes.put(email, request);

            loginService.sendVerificationCode(email, verificationCode);

            response.setMsg("Verification code sent to your email.");
            response.getPassword();
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

        LoginRequest storedRequest = verificationCodes.get(email);

        if (storedRequest != null) {
            long expiryTime = 5 * 60 * 1000;
            if (System.currentTimeMillis() - storedRequest.getTimestamp() > expiryTime) {
                verificationCodes.remove(email);
                response.setMsg("Verification code expired.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

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


