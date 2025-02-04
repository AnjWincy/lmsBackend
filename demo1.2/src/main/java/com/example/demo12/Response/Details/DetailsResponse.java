package com.example.demo12.Response.Details;

import com.example.demo12.Model.details.Manager;
import com.example.demo12.Model.details.Student;
import com.example.demo12.Model.details.Trainer;

import java.util.List;

public class DetailsResponse {
    private List<Student> Students;

    public List<Student> getStudents() {
        return Students;
    }

    public void setStudents(List<Student> students) {
        this.Students = students;
    }


    private List<Manager> Managers;

    public List<Trainer> getTrainers() {
        return Trainers;
    }

    public void setTrainers(List<Trainer> Trainers) {
        this.Trainers = Trainers;
    }

    private List<Trainer> Trainers;// Change to hold 'Manager' objects

    public List<Manager> getManagers() {
        return Managers;
    }

    public void setManagers(List<Manager> Managers) {
        this.Managers = Managers;
    }

}
