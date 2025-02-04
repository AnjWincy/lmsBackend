package com.example.demo12.Service.Details;

import com.example.demo12.Model.details.Manager;
import com.example.demo12.Model.details.Student;
import com.example.demo12.Model.details.Trainer;
import com.example.demo12.Repository.DetailsRepository.ManagerRepository;
import com.example.demo12.Repository.DetailsRepository.StudentRepository;
import com.example.demo12.Repository.DetailsRepository.TrainerRepository;
import com.example.demo12.Request.Details.LoginRequest;
import com.example.demo12.Response.Details.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;



@Service
public class LoginService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final Random random = new Random();

    public String generateVerificationCode() {
        int code = 100000 + random.nextInt(900000); // Generates a 6-digit code
        return String.valueOf(code);
    }

    // Send verification code via email
    public void sendVerificationCode(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Code");
        message.setText("Use the following code to reset your password: " + verificationCode);
        mailSender.send(message);
    }




    public LoginResponse validateManager(LoginRequest loginRequest) throws Exception {
        Manager user = managerRepository.validateManager(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new Exception("Invalid email or password");
        }

        LoginResponse response = new LoginResponse();
        response.setMsg("Login successful");
        return response;
    }

    public LoginResponse validateTrainer(LoginRequest loginRequest) throws Exception {
        Trainer user = trainerRepository.validateTrainer(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new Exception("Invalid email or password");
        }
        LoginResponse response = new LoginResponse();
        response.setMsg("Login successful");
        return response;
    }

    public Student findByEmailStudent(String email) {
        return studentRepository.findByEmail(email);
    }

    public Manager findByEmailManager(String email) {
        return managerRepository.findByEmail(email);
    }

    public Trainer findByEmailTrainer(String email) {
        return trainerRepository.findByEmail(email);
    }

    public void updateStudentPassword(Student Student, String newPassword) {
        Student.setPassword(newPassword); // You should hash the password here before saving it.
        studentRepository.save(Student);
    }

    public void updateManagerPassword(Manager Manager, String newPassword) {
        Manager.setPassword(newPassword); // You should hash the password here before saving it.
        managerRepository.save(Manager);
    }

    public void updateTrainerPassword(Trainer Trainer, String newPassword) {
        Trainer.setPassword(newPassword); // You should hash the password here before saving it.
        trainerRepository.save(Trainer);
    }








}
