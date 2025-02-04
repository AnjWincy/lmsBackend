package com.example.demo12.Service.FeedBack;

import com.example.demo12.Model.FeedBack.FeedbackModel;
import com.example.demo12.Repository.Package.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedBackRepository feedbackrepo;

    // Post feedback
    public void post(FeedbackModel feedback) {
        feedbackrepo.save(feedback); // Save the feedback to the database
    }

    // Get all feedback data
    public List<FeedbackModel> getAllFeedback() {
        return feedbackrepo.findAll(); // Fetch all feedback from the database
    }
}
