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
        student.setMarks(List.of(new Marks())); // Assuming Marks is a valid class
        student.setStudent_name("John Doe");
        student.setProfile("data:image/jpeg;base64,abc123");
    }


    @Test
    public void testDeleteStudent() {
        // Act
        studentService.deleteStudent("12345");

        // Assert
        verify(studentRepository, times(1)).deleteById("12345");
    }


    @Test
    public void testGetStudentById() {
        // Arrange
        when(studentRepository.getStudentByRnId("12345")).thenReturn(student);

        // Act
        Student result = studentService.getStudentById("12345");

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getStudent_name());
        verify(studentRepository, times(1)).getStudentByRnId("12345");
    }

    @Test
    public void testSaveStudent() throws IOException {
        // Arrange
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        Student result = studentService.saveStudent(student);

        // Assert
        assertNotNull(result);
        assertEquals("http://localhost:8080/images/student_12345.jpg", result.getProfile());
        verify(studentRepository, times(1)).save(student);
    }


    @Test
    public void testSaveOrUpdateStudent() throws IOException {
        // Arrange
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        Student result = studentService.saveOrUpdateStudent(student);

        // Assert
        assertNotNull(result);
        assertEquals("http://localhost:8080/images/student_12345.jpg", result.getProfile());
        verify(studentRepository, times(1)).save(student);
    }


    @Test
    public void testGetTotalStudentCount() {
        // Arrange
        when(studentRepository.count()).thenReturn(10L);

        // Act
        long count = studentService.getTotalStudentCount();

        // Assert
        assertEquals(10L, count);
        verify(studentRepository, times(1)).count();
    }

    @Test
    public void testSaveStudentWithNullRnId() throws IOException {
        // Set rn_id to null to simulate an invalid state
        student.setRn_id(null);

        // Expecting an exception to be thrown
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.saveStudent(student); // This should throw an exception
        });

        // Verify that save was never called on the repository because the student is invalid
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
