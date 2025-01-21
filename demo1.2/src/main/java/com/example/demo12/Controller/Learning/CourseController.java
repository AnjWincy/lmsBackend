package com.example.demo12.Controller.Learning;


import com.example.demo12.Model.learning.courses;
import com.example.demo12.Model.learning.topics;
import com.example.demo12.Request.Learning.CourseRequest;
import com.example.demo12.Response.Learning.CoursesResponse;
import com.example.demo12.Service.Learning.CourseService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/course")
    public CoursesResponse getAllCourses() {
        List<courses> courses = courseService.getAllCourses();

        // Create a response object
        CoursesResponse response = new CoursesResponse();
        response.setMsg("Courses retrieved successfully");
        response.setCourses(courses);  // Assuming you added a `courses` field in `LoginResponse`

        return response;
    }

    @GetMapping("/topics/{courseId}")
    public CoursesResponse getTopicsByCourseId(@PathVariable Long courseId) {
        List<topics> topics = courseService.getTopicsByCourseId(courseId);

        // Create a response object
        CoursesResponse response = new CoursesResponse();
        response.setMsg("Topics retrieved successfully for course ID: " + courseId);

        // You can also add the list of topics or any other details as needed in the response
        response.setTopics(topics);  // Assuming you added a `topics` field in `LoginResponse`

        return response;    }

    // Update completion status of a specific topic
    @PutMapping("/topics/{topicId}")
    public CoursesResponse updateTopicCompletion(
            @PathVariable Long topicId, @RequestBody CourseRequest completionRequest) {

        boolean completed = completionRequest.isTopic_completed();  // Get the completion status (true/false)

        // Toggle the current completion status
        topics updatedTopic = courseService.updateTopicCompletion(topicId, completed);

        CoursesResponse response = new CoursesResponse();
        if (updatedTopic != null) {
            response.setMsg("Topic completion updated successfully");
            response.setTopic(updatedTopic);  // Return the updated topic
            return response;
        } else {
            response.setMsg("Topic not found or update failed");
            return response;
        }
    }



}
