package com.example.demo12.ServiceTest;


import com.example.demo12.Model.details.Manager;
import com.example.demo12.Model.details.Student;
import com.example.demo12.Model.details.Trainer;
import com.example.demo12.Repository.DetailsRepository.ManagerRepository;
import com.example.demo12.Repository.DetailsRepository.StudentRepository;
import com.example.demo12.Repository.DetailsRepository.TrainerRepository;
import com.example.demo12.Request.Details.LoginRequest;
import com.example.demo12.Response.Details.LoginResponse;
import com.example.demo12.Service.Details.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private LoginService loginService;

    private LoginRequest loginRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");
    }

    @Test
    public void testGenerateVerificationCode() {
        String code = loginService.generateVerificationCode();
        assertTrue(code.matches("\\d{6}"));
    }


    @Test
    public void testSendVerificationCode() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        loginService.sendVerificationCode("test@example.com", "123456");
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }


    @Test
    public void testUpdateStudentPassword() {
        Student mockStudent = new Student();
        mockStudent.setEmail("student@example.com");
        mockStudent.setPassword("oldPassword");

        when(studentRepository.findByEmail("student@example.com")).thenReturn(mockStudent);
        loginService.updateStudentPassword(mockStudent, "newPassword");

        verify(studentRepository, times(1)).save(mockStudent);
        assertEquals("newPassword", mockStudent.getPassword());
    }

    @Test
    public void testUpdateManagerPassword() {
        Manager mockManager = new Manager();
        mockManager.setEmail("manager@example.com");
        mockManager.setPassword("oldPassword");

        when(managerRepository.findByEmail("manager@example.com")).thenReturn(mockManager);

        loginService.updateManagerPassword(mockManager, "newPassword");

        verify(managerRepository, times(1)).save(mockManager);
        assertEquals("newPassword", mockManager.getPassword());
    }

    @Test
    public void testUpdateTrainerPassword() {
        Trainer mockTrainer = new Trainer();
        mockTrainer.setEmail("trainer@example.com");
        mockTrainer.setPassword("oldPassword");

        when(trainerRepository.findByEmail("trainer@example.com")).thenReturn(mockTrainer);

        loginService.updateTrainerPassword(mockTrainer, "newPassword");

        verify(trainerRepository, times(1)).save(mockTrainer);
        assertEquals("newPassword", mockTrainer.getPassword());
    }


}

