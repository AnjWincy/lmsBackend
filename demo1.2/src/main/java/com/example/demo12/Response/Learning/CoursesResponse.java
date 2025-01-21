package com.example.demo12.Response.Learning;

import com.example.demo12.Model.learning.courses;
import com.example.demo12.Model.learning.topics;

import java.util.List;

public class CoursesResponse {

    private String msg;
    private List<courses> Courses;
    private List<topics> Topics;
    private topics Topic;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<topics> getTopics() {
        return Topics;
    }

    public void setTopics(List<topics> topics) {
        Topics = topics;
    }

    public topics getTopic() {
        return Topic;
    }

    public void setTopic(topics topic) {
        Topic = topic;
    }



    public List<courses> getCourses() {
        return Courses;
    }

    public void setCourses(List<courses> courses) {
        Courses = courses;
    }

}
