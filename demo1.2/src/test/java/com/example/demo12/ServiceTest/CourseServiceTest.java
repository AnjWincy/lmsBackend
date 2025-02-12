package com.example.demo12.ServiceTest;

import com.example.demo12.Model.learning.Courses;
import com.example.demo12.Model.learning.Topics;
import com.example.demo12.Repository.LearningRepository.CourseRepository;
import com.example.demo12.Repository.LearningRepository.TopicsRepository;
import com.example.demo12.Service.Learning.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TopicsRepository topicsRepository;

    @InjectMocks
    private CourseService courseService;

    private Courses course;
    private Topics topic;

    @BeforeEach
    public void setup() {
        // Set up course and topic for testing
        course = new Courses();
        course.setCourse_id(1L);
        course.setCourse_title("Java Programming");
        course.setStart_date("2025-02-01");

        topic = new Topics();
        topic.setTopic_id(1L);
        topic.setTopic_model("Java Basics");
        topic.setTopic_completed(false);
        topic.setC_id(1L);
        topic.setTopics_topic("Introduction to Java");
    }

    @Test
    public void testGetAllCourses() {
        // Mock behavior to return a list of courses
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        var courses = courseService.getAllCourses();

        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("Java Programming", courses.get(0).getCourse_title());

        // Verify that findAll was called once
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testGetTopicsByCourseId() {
        // Mock behavior to return a list of topics for a specific course
        when(topicsRepository.findByCourseId(1L)).thenReturn(Arrays.asList(topic));

        var topics = courseService.getTopicsByCourseId(1L);

        assertNotNull(topics);
        assertEquals(1, topics.size());
        assertEquals("Java Basics", topics.get(0).getTopic_model());

        // Verify that findByCourseId was called once
        verify(topicsRepository, times(1)).findByCourseId(1L);
    }

    @Test
    public void testUpdateTopicCompletion_TopicFound() {
        // Mock behavior to return a topic by ID
        when(topicsRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(topicsRepository.save(any(Topics.class))).thenReturn(topic);

        // Call method to update topic completion
        Topics updatedTopic = courseService.updateTopicCompletion(1L, true);

        assertNotNull(updatedTopic);
        assertTrue(updatedTopic.isTopic_completed());  // The completion status should be toggled

        // Verify that findById and save were called once
        verify(topicsRepository, times(1)).findById(1L);
        verify(topicsRepository, times(1)).save(any(Topics.class));
    }

    @Test
    public void testUpdateTopicCompletion_TopicNotFound() {
        // Mock behavior to return an empty result for non-existent topic ID
        when(topicsRepository.findById(999L)).thenReturn(Optional.empty());

        // Call method to update topic completion
        Topics updatedTopic = courseService.updateTopicCompletion(999L, true);

        assertNull(updatedTopic);  // Should return null as the topic was not found

        // Verify that findById was called once
        verify(topicsRepository, times(1)).findById(999L);
        verify(topicsRepository, times(0)).save(any(Topics.class));  // save should not be called
    }

    @Test
    public void testToggleTopicCompletion() {
        // Mock findById to return the existing topic
        when(topicsRepository.findById(1L)).thenReturn(Optional.of(topic));

        // Mock save to return the topic after toggling
        when(topicsRepository.save(any(Topics.class))).thenReturn(topic);

        // Initially, the topic is not completed (false)
        Topics updatedTopic = courseService.updateTopicCompletion(topic.getTopic_id(), true);

        assertNotNull(updatedTopic);  // Topic should not be null
        assertTrue(updatedTopic.isTopic_completed());  // The status should toggle to true

        // Toggle again and check if it toggles back to false
        updatedTopic = courseService.updateTopicCompletion(topic.getTopic_id(), false);

        assertNotNull(updatedTopic);  // Topic should still not be null
        assertFalse(updatedTopic.isTopic_completed());  // The status should toggle back to false

        // Verify that findById was called twice (once for each toggle)
        verify(topicsRepository, times(2)).findById(1L);  // Verify that findById was called twice
        verify(topicsRepository, times(2)).save(any(Topics.class));  // Verify that save was called twice
    }



}

