package com.example.demo12.Controller.Details;

import com.example.demo12.Model.details.Student;
import com.example.demo12.Service.Details.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
    @Autowired
    StudentService studentService;


    @GetMapping("/allStudents")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllMarks();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

//    @GetMapping("/allStudents")
//    public ResponseEntity<List<Student>> getAllStudents() {
//        List<Student> students = studentService.getAllMarks();
//
//        // Modify the profile field to return an accessible URL if necessary
//        for (Student student : students) {
//            // Assuming profile is the file path like "C:/Users/.../student_RN21505.jpg"
//            String profileImagePath = student.getProfile();
//            if (profileImagePath != null) {
//                // Extract the filename and generate a URL that the frontend can access
//                String imageUrl = "http://localhost:8080/images/" + Paths.get(profileImagePath).getFileName().toString();
//                student.setProfile(imageUrl); // Set the profile URL for the frontend
//            }
//        }
//
//        return new ResponseEntity<>(students, HttpStatus.OK);
//    }

//    @GetMapping("/allStudents")
//    public ResponseEntity<List<Student>> getAllStudents() {
//        List<Student> students = studentService.getAllMarks();
//        // No need to modify the profile URL, it should already be full
//        return new ResponseEntity<>(students, HttpStatus.OK);
//    }


    @PostMapping("/add/student")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) throws IOException {
        Student savedStudent = studentService.saveStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);  // 201 for created resource
    }



    // Get student by ID (GET /students/{id})
    @GetMapping("/student/{rn_id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String rn_id) {
        Student student = studentService.getStudentById(rn_id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 if not found
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }


    // Save new student (POST /students)


    // Update student (PUT /students/{id})
    @PutMapping("/updateStudent/{rn_id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String rn_id, @RequestBody Student student)throws IOException {
        Student existingStudent = studentService.getStudentById(rn_id);  // Get the student by ID

        if (existingStudent != null) {
            student.setRn_id(rn_id);  // Set the 'rn_id' in the request body to the path variable

            // Save or update the student
            Student updatedStudent = studentService.saveOrUpdateStudent(student);
            return ResponseEntity.ok(updatedStudent);  // Return the updated student
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // If student not found, return 404
        }
    }


    // Delete student (DELETE /students/{id})
    @DeleteMapping("/deleteStudent/{rn_id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String rn_id) {
        Student existingStudent = studentService.getStudentById(rn_id);
        if (existingStudent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 if not found
        }
        studentService.deleteStudent(rn_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 for successful delete with no content
    }
}
