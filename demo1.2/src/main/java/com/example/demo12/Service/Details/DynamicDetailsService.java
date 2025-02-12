package com.example.demo12.Service.Details;


import com.example.demo12.Model.Mark.Marks;
import com.example.demo12.Model.details.Manager;
import com.example.demo12.Model.details.Student;
import com.example.demo12.Model.details.Trainer;
import com.example.demo12.Repository.DetailsRepository.ManagerRepository;
import com.example.demo12.Repository.DetailsRepository.StudentRepository;
import com.example.demo12.Repository.DetailsRepository.TrainerRepository;
import com.example.demo12.Repository.Mark.MarksRepository;
import com.example.demo12.Request.Details.LoginRequest;
import com.example.demo12.Response.Details.DetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DynamicDetailsService {


    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    MarksRepository marksRepository;


    public DetailsResponse getStudentDetails(LoginRequest loginRequest) throws Exception {
        Student user = studentRepository.validateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new Exception("Invalid email or password");
        }
        Optional<Student> optionalStudent = studentRepository.findById(user.getRn_id());
        if (!optionalStudent.isPresent()) {
            throw new Exception("Student not found");
        }
        Student studentDetails = optionalStudent.get();
        DetailsResponse response = new DetailsResponse();
        response.setStudents(List.of(studentDetails));
        return response;
    }


    public DetailsResponse getManagerDetails(LoginRequest loginRequest) throws Exception {
        Manager user = managerRepository.validateManager(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new Exception("Invalid email or password");
        }
        Optional<Manager> optionalManager = managerRepository.findById(user.getM_id());

        if (!optionalManager.isPresent()) {
            throw new Exception("Manager not found");
        }

        Manager managerDetails = optionalManager.get();
        DetailsResponse response = new DetailsResponse();
        response.setManagers(List.of(managerDetails));
        return response;
    }

    public DetailsResponse getTrainerDetails(LoginRequest loginRequest) throws Exception {
        Trainer user = trainerRepository.validateTrainer(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new Exception("Invalid email or password");
        }

        Optional<Trainer> optionalTrainer = trainerRepository.findById(user.getTrainer_id());

        if (!optionalTrainer.isPresent()) {
            throw new Exception("Manager not found");
        }

        Trainer trainerDetails = optionalTrainer.get();

        DetailsResponse response = new DetailsResponse();
        response.setTrainers(List.of(trainerDetails));
        return response;
    }



    public List<String> getStuId() {
        return studentRepository.getStudentIds();
    }



    public List<String> getStudentByTrainerId(String trainerId) {
        return studentRepository.findByTrainerId(trainerId);
    }

    public List<Marks> getMarksByStudentId(String rn_id) {
        return marksRepository.findByMarksId(rn_id);
    }

    public List<Student> getAllMarks() {
        return studentRepository.findAll();
    }


}
