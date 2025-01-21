package com.example.demo12.Response;

import com.example.demo12.Model.details.manager;
import com.example.demo12.Model.details.student;
import com.example.demo12.Model.details.trainer;

import java.util.List;

public class DetailsResponse {
    private List<student> Students;

    public List<student> getStudents() {
        return Students;
    }

    public void setStudents(List<student> students) {
        Students = students;
    }

    private List<manager> managers;

    public List<trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<trainer> trainers) {
        this.trainers = trainers;
    }

    private List<trainer> trainers;// Change to hold 'manager' objects

    public List<manager> getManagers() {
        return managers;
    }

    public void setManagers(List<manager> managers) {
        this.managers = managers;
    }

}
