package com.example.demo12.Controller.Learning;


import com.example.demo12.Model.learning.Courses;
import com.example.demo12.Model.learning.Topics;
import com.example.demo12.Request.Learning.CourseRequest;
import com.example.demo12.Response.Learning.CoursesResponse;
import com.example.demo12.Service.Learning.CourseService;
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
        List<Courses> courses = courseService.getAllCourses();

        // Create a response object
        CoursesResponse response = new CoursesResponse();
        response.setMsg("Courses retrieved successfully");
        response.setCourse(courses);  // Assuming you added a `Courses` field in `LoginResponse`

        return response;
    }

    @GetMapping("/Topics/{courseId}")
    public CoursesResponse getTopicsByCourseId(@PathVariable Long courseId) {
        List<Topics> topics = courseService.getTopicsByCourseId(courseId);

        // Create a response object
        CoursesResponse response = new CoursesResponse();
        response.setMsg("Topics retrieved successfully for course ID: " + courseId);

        // You can also add the list of Topics or any other details as needed in the response
        response.setTopic(topics);  // Assuming you added a `Topics` field in `LoginResponse`

        return response;    }

    // Update completion status of a specific topic
    @PutMapping("/Topics/{topicId}")
    public CoursesResponse updateTopicCompletion(
            @PathVariable Long topicId, @RequestBody CourseRequest completionRequest) {

        boolean completed = completionRequest.isTopic_completed();  // Get the completion status (true/false)

        // Toggle the current completion status
        Topics updatedTopic = courseService.updateTopicCompletion(topicId, completed);

        CoursesResponse response = new CoursesResponse();
        if (updatedTopic != null) {
            response.setMsg("Topic completion updated successfully");
            response.setTopc(updatedTopic);  // Return the updated topic
            return response;
        } else {
            response.setMsg("Topic not found or update failed");
            return response;
        }
    }



}
