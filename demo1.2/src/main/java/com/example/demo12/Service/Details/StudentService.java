package com.example.demo12.Service.Details;

import com.example.demo12.Model.details.Student;
import com.example.demo12.Repository.DetailsRepository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    private final String FILE_PATH = "C:\\Users\\z046705\\Documents\\Dynamic Images";


    public List<Student> getAllMarks() {
        return studentRepository.findAll();
    }


    public  void deleteStudent(String rn_id) {
        studentRepository.deleteById(rn_id);
    }


    public Student getStudentById(String rn_id) {
        return studentRepository.getStudentByRnId(rn_id);
    }

    public Student saveStudent(Student student) throws IOException {
        if (student.getRn_id() == null) {
            throw new IllegalArgumentException("rn_id cannot be null");
        }

        if (student.getProfile() != null) {
            String base64Data = student.getProfile();
            String fileExtension = "jpg";  // Default to jpg

            // Check if the profile contains the base64 string and the image format prefix
            if (base64Data.startsWith("data:image/jpeg;base64,")) {
                base64Data = base64Data.substring("data:image/jpeg;base64,".length());
                fileExtension = "jpg";  // JPEG
            } else if (base64Data.startsWith("data:image/png;base64,")) {
                base64Data = base64Data.substring("data:image/png;base64,".length());
                fileExtension = "png";  // PNG
            }

            // Decode the base64 string into a byte array
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            // Generate a unique file name for the image (optional: use student ID or UUID)
            String fileName = "student_" + student.getRn_id() + "." + fileExtension;
            File file = new File(FILE_PATH, fileName);

            // Create the file and write the image data to it
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(imageBytes);
            }

            // Save the full URL to the profile (this will be returned to the frontend directly)
            student.setProfile("http://localhost:8080/images/" + fileName);
        }

        return studentRepository.save(student);  // Save the student to the DB
    }


    public Student saveOrUpdateStudent(Student student) throws IOException {

        if (student.getProfile() != null && !student.getProfile().startsWith("http://localhost:8080/images/student_" + student.getRn_id())) {
          System.out.println("geafgb");
            String base64Data = student.getProfile();
            String fileExtension = "jpg"; // Default to jpg

            if (base64Data.startsWith("data:image/jpeg;base64,")) {
                base64Data = base64Data.substring("data:image/jpeg;base64,".length());
                fileExtension = "jpg";  // JPEG
            } else if (base64Data.startsWith("data:image/png;base64,")) {
                base64Data = base64Data.substring("data:image/png;base64,".length());
                fileExtension = "png";  // PNG
            }

            // Decode the base64 string into a byte array
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            // Generate a unique file name for the image (optional: use student ID or UUID)
            String fileName = "student_" + student.getRn_id() + "." + fileExtension;
            File file = new File(FILE_PATH, fileName);

            // Create the file and write the image data to it
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(imageBytes);
            }

            // Save the full URL to the profile (this will be returned to the frontend directly)
            student.setProfile("http://localhost:8080/images/" + fileName);
        }

        // Save or update the student in the database
        return studentRepository.save(student);
    }


    public long getTotalStudentCount() {
        return studentRepository.count();
    }

}
