package com.example.demo12.Service.Learning;

import com.example.demo12.Model.learning.courses;
import com.example.demo12.Model.learning.topics;
import com.example.demo12.Repository.LearningRepository.CourseRepository;
import com.example.demo12.Repository.LearningRepository.TopicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TopicsRepository topicsRepository;

    public List<courses> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<topics> getTopicsByCourseId(Long courseId) {
        return topicsRepository.findByCourseId(courseId);
    }

    //    // Update completion status of a specific topic
    public topics updateTopicCompletion(Long topicId, boolean completed) {
        // Find the topic by ID
        topics topic = topicsRepository.findById(topicId).orElse(null);

        if (topic != null) {
            // Toggle the completion status (if true, set to false, if false, set to true)
            boolean currentStatus = topic.isTopic_completed();
            topic.setTopic_completed(!currentStatus);  // Toggle the status

            // Save the updated topic
            return topicsRepository.save(topic);
        }
        return null;  // Return null if topic is not found
    }



}
