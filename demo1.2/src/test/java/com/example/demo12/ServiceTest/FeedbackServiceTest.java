package com.example.demo12.ServiceTest;


import com.example.demo12.Model.FeedBack.FeedbackModel;
import com.example.demo12.Repository.Package.FeedBackRepository;
import com.example.demo12.Service.FeedBack.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedBackRepository feedbackrepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostFeedback() {
        FeedbackModel feedback = new FeedbackModel();
        feedback.setId(1);
        feedbackService.post(feedback);
        verify(feedbackrepo, times(1)).save(feedback);
    }

    @Test
    public void testGetAllFeedback() {
        FeedbackModel feedback1 = new FeedbackModel();
        feedback1.setId(1);

        FeedbackModel feedback2 = new FeedbackModel();
        feedback2.setId(2);

        List<FeedbackModel> feedbackList = Arrays.asList(feedback1, feedback2);

        when(feedbackrepo.findAll()).thenReturn(feedbackList);
        List<FeedbackModel> result = feedbackService.getAllFeedback();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllFeedbackEmpty() {
        List<FeedbackModel> feedbackList = Arrays.asList();

        when(feedbackrepo.findAll()).thenReturn(feedbackList);

        List<FeedbackModel> result = feedbackService.getAllFeedback();

        assertTrue(result.isEmpty());
    }
}

