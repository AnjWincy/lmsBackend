package com.example.demo12.Response.Learning;

import com.example.demo12.Model.learning.Courses;
import com.example.demo12.Model.learning.Topics;

import java.util.List;

public class CoursesResponse {

    private String msg;
    private List<Courses> Course;
    private List<Topics> Topic;
    private com.example.demo12.Model.learning.Topics Topc;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Courses> getCourse() {
        return Course;
    }

    public void setCourse(List<Courses> course) {
        Course = course;
    }

    public List<Topics> getTopic() {
        return Topic;
    }

    public void setTopic(List<Topics> topic) {
        Topic = topic;
    }

    public Topics getTopc() {
        return Topc;
    }

    public void setTopc(Topics topc) {
        Topc = topc;
    }


}
