package com.example.demo12.Service.Details;

import com.example.demo12.Model.Mark.marks;
import com.example.demo12.Model.details.manager;
import com.example.demo12.Model.details.student;
import com.example.demo12.Model.details.trainer;
import com.example.demo12.Model.learning.topics;
import com.example.demo12.Repository.DetailsRepository.ManagerRepository;
import com.example.demo12.Repository.DetailsRepository.StudentRepository;
import com.example.demo12.Repository.DetailsRepository.TrainerRepository;
import com.example.demo12.Repository.Mark.MarksRepository;
import com.example.demo12.Request.Details.LoginRequest;
import com.example.demo12.Response.Details.LoginResponse;
import com.example.demo12.Response.DetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LoginService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    TrainerRepository trainerRepository;


    @Autowired
    MarksRepository marksRepository;

    public DetailsResponse getStudentDetails(LoginRequest loginRequest) throws Exception {
        student user = studentRepository.validateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new Exception("Invalid email or password");
        }

        // Assuming you have a method to fetch the student's details, such as `getStudentDetails`.
        Optional<student> optionalStudent = studentRepository.findById(user.getRn_id());

        if (!optionalStudent.isPresent()) {
            throw new Exception("Student not found");  // Handle case where student is not found
        }

        student studentDetails = optionalStudent.get();  // Retrieve the student from Optional

        // Prepare the response with the student details
        DetailsResponse response = new DetailsResponse();
        response.setStudents(List.of(studentDetails));  // Wrap the single student in a list
        return response;
    }


    public DetailsResponse getManagerDetails(LoginRequest loginRequest) throws Exception {
        // Validate the manager by email and password
        manager user = managerRepository.validateManager(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new Exception("Invalid email or password");
        }

        // Fetch the manager details by manager ID
        Optional<manager> optionalManager = managerRepository.findById(user.getM_id());

        if (!optionalManager.isPresent()) {
            throw new Exception("Manager not found");  // Handle case where manager is not found
        }

        manager managerDetails = optionalManager.get();  // Retrieve the manager from Optional

        // Prepare the response with the manager details
        DetailsResponse response = new DetailsResponse();
        response.setManagers(List.of(managerDetails));  // Wrap the single manager in a list
        return response;
    }

    public DetailsResponse getTrainerDetails(LoginRequest loginRequest) throws Exception {
        // Validate the manager by email and password
        trainer user = trainerRepository.validateTrainer(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new Exception("Invalid email or password");
        }

        // Fetch the manager details by manager ID
        Optional<trainer> optionalTrainer = trainerRepository.findById(user.getTrainer_id());

        if (!optionalTrainer.isPresent()) {
            throw new Exception("Manager not found");  // Handle case where manager is not found
        }

        trainer trainerDetails = optionalTrainer.get();  // Retrieve the manager from Optional

        // Prepare the response with the manager details
        DetailsResponse response = new DetailsResponse();
        response.setTrainers(List.of(trainerDetails));  // Wrap the single manager in a list
        return response;
    }



    public LoginResponse validateManager(LoginRequest loginRequest) throws Exception {
        manager user = managerRepository.validateManager(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new Exception("Invalid email or password");
        }

        LoginResponse response = new LoginResponse();
        response.setMsg("Login successful");
        return response;
    }

    public LoginResponse validateTrainer(LoginRequest loginRequest) throws Exception {
        trainer user = trainerRepository.validateTrainer(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new Exception("Invalid email or password");
        }
        LoginResponse response = new LoginResponse();
        response.setMsg("Login successful");
        return response;
    }

    public student findByEmailStudent(String email) {
        return studentRepository.findByEmail(email);
    }

    public manager findByEmailManager(String email) {
        return managerRepository.findByEmail(email);
    }

    public trainer findByEmailTrainer(String email) {
        return trainerRepository.findByEmail(email);
    }

//    // Method to update the user's password
    public void updateStudentPassword(student Student, String newPassword) {
        Student.setPassword(newPassword); // You should hash the password here before saving it.
        studentRepository.save(Student);
    }

    public void updateManagerPassword(manager Manager, String newPassword) {
        Manager.setPassword(newPassword); // You should hash the password here before saving it.
        managerRepository.save(Manager);
    }

    public void updateTrainerPassword(trainer Trainer, String newPassword) {
        Trainer.setPassword(newPassword); // You should hash the password here before saving it.
        trainerRepository.save(Trainer);
    }

    public List<String> getStuId() {
        // Call the repository to fetch the student IDs
        return studentRepository.getStudentIds();
    }


    public List<String> getStudentByTrainerId(String trainerId) {
        return studentRepository.findByTrainerId(trainerId);
    }

    public List<marks> getMarksByStudentId(String rn_id) {
        return marksRepository.findByMarksId(rn_id);
    }

    public List<student> getAllMarks() {
        return studentRepository.findAll();
    }
}
