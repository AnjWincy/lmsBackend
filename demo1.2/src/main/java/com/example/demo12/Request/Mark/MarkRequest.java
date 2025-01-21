package com.example.demo12.Request.Mark;

import com.example.demo12.Model.Mark.marks;

import java.util.List;

public class MarkRequest {



    private List<marks> marklist;
    private Long mark_id;
    private String trainerId;
    private String subject;
    private String studentType;

    public Long getMark_id() {
        return mark_id;
    }

    public void setMark_id(Long mark_id) {
        this.mark_id = mark_id;
    }
    // Getters and setters
    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudentType() {
        return studentType;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    public List<marks> getMarklist() {
        return marklist;
    }

    public void setMarklist(List<marks> marklist) {
        this.marklist = marklist;
    }


}
