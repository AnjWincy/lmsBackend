package com.example.demo12.ServiceTest;

import com.example.demo12.Model.Mark.Marks;
import com.example.demo12.Model.details.Student;
import com.example.demo12.Repository.DetailsRepository.StudentRepository;
import com.example.demo12.Service.Details.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StuService {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setRn_id("12345");
        student.setSkills("Java");
        student.setEmail("doe@gmail.com");
        student.setT_id("TR001");
        student.setMarks(List.of(new Marks()));
        student.setStudent_name("John Doe");
        student.setProfile("data:image/jpeg;base64,abc123");
    }


    @Test
    public void testDeleteStudent() {
        studentService.deleteStudent("12345");

        verify(studentRepository, times(1)).deleteById("12345");
    }


    @Test
    public void testGetStudentById() {

        when(studentRepository.getStudentByRnId("12345")).thenReturn(student);

        Student result = studentService.getStudentById("12345");

        assertNotNull(result);
        assertEquals("John Doe", result.getStudent_name());
        verify(studentRepository, times(1)).getStudentByRnId("12345");
    }

    @Test
    public void testSaveStudent() throws IOException {

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.saveStudent(student);

        assertNotNull(result);
        assertEquals("http://localhost:8080/images/student_12345.jpg", result.getProfile());
        verify(studentRepository, times(1)).save(student);
    }


    @Test
    public void testSaveOrUpdateStudent() throws IOException {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.saveOrUpdateStudent(student);

        assertNotNull(result);
        assertEquals("http://localhost:8080/images/student_12345.jpg", result.getProfile());
        verify(studentRepository, times(1)).save(student);
    }


    @Test
    public void testGetTotalStudentCount() {
        when(studentRepository.count()).thenReturn(10L);

        long count = studentService.getTotalStudentCount();

        assertEquals(10L, count);
        verify(studentRepository, times(1)).count();
    }

    @Test
    public void testSaveStudentWithNullRnId() throws IOException {
        student.setRn_id(null);

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.saveStudent(student);
        });

        verify(studentRepository, times(0)).save(any(Student.class));
    }




    // 11. **Test Save Student with Empty Email**
    @Test
    public void testSaveStudentWithEmptyEmail() throws IOException {
        student.setEmail(""); // Empty email

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.saveStudent(student);

        assertNotNull(result);
        assertEquals("", result.getEmail()); // Ensure email is empty
        verify(studentRepository, times(1)).save(student);
    }


}
