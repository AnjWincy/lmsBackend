package com.example.demo12.Model.learning;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class topics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topic_id;

    private String topic_model;
    private boolean topic_completed;
    private Long c_id;
    private String topics_topic;

    public Long getTopic_id() {
        return topic_id;
    }

    public String getTopic_model() {
        return topic_model;
    }

    public void setTopic_model(String topic_model) {
        this.topic_model = topic_model;
    }

    public void setTopic_id(Long topic_id) {
        this.topic_id = topic_id;
    }


    public boolean isTopic_completed() {
        return topic_completed;
    }

    public void setTopic_completed(boolean topic_completed) {
        this.topic_completed = topic_completed;
    }



    public Long getC_id() {
        return c_id;
    }

    public void setC_id(Long c_id) {
        this.c_id = c_id;
    }



    public String getTopics_topic() {
        return topics_topic;
    }

    public void setTopics_topic(String topics_topic) {
        this.topics_topic = topics_topic;
    }


}
