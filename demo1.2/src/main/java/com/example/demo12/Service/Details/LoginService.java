package com.example.demo12.Service.Details;

import com.example.demo12.Model.details.Manager;
import com.example.demo12.Model.details.Student;
import com.example.demo12.Model.details.Trainer;
import com.example.demo12.Repository.DetailsRepository.ManagerRepository;
import com.example.demo12.Repository.DetailsRepository.StudentRepository;
import com.example.demo12.Repository.DetailsRepository.TrainerRepository;


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
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }


    public void sendVerificationCode(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Code");
        message.setText("Use the following code to reset your password: " + verificationCode);
        mailSender.send(message);
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
        Student.setPassword(newPassword);
        studentRepository.save(Student);
    }

    public void updateManagerPassword(Manager Manager, String newPassword) {
        Manager.setPassword(newPassword);
        managerRepository.save(Manager);
    }

    public void updateTrainerPassword(Trainer Trainer, String newPassword) {
        Trainer.setPassword(newPassword);
        trainerRepository.save(Trainer);
    }








}
