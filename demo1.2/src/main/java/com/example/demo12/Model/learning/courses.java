package com.example.demo12.Model.learning;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Courses {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long course_id;
    private String course_title;
    private String start_date;
    @OneToMany
    @JoinColumn(name="c_id",referencedColumnName = "course_id")
    private List<Topics> topic;

    public List<Topics> getTopic() {
        return topic;
    }

    public void setTopic(List<Topics> topic) {
        this.topic = topic;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }


    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }



}
