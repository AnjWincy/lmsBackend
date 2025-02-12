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
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));
        var courses = courseService.getAllCourses();
        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("Java Programming", courses.get(0).getCourse_title());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testGetTopicsByCourseId() {
        when(topicsRepository.findByCourseId(1L)).thenReturn(Arrays.asList(topic));
        var topics = courseService.getTopicsByCourseId(1L);
        assertNotNull(topics);
        assertEquals(1, topics.size());
        assertEquals("Java Basics", topics.get(0).getTopic_model());
        verify(topicsRepository, times(1)).findByCourseId(1L);
    }

    @Test
    public void testUpdateTopicCompletion_TopicFound() {
        when(topicsRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(topicsRepository.save(any(Topics.class))).thenReturn(topic);
        Topics updatedTopic = courseService.updateTopicCompletion(1L, true);
        assertNotNull(updatedTopic);
        assertTrue(updatedTopic.isTopic_completed());
        verify(topicsRepository, times(1)).findById(1L);
        verify(topicsRepository, times(1)).save(any(Topics.class));
    }

    @Test
    public void testUpdateTopicCompletion_TopicNotFound() {
        when(topicsRepository.findById(999L)).thenReturn(Optional.empty());
        Topics updatedTopic = courseService.updateTopicCompletion(999L, true);
        assertNull(updatedTopic);
        verify(topicsRepository, times(1)).findById(999L);
        verify(topicsRepository, times(0)).save(any(Topics.class));  // save should not be called
    }




}

