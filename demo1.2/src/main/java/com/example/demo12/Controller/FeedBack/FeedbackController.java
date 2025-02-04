package com.example.demo12.Controller.FeedBack;

import com.example.demo12.Model.FeedBack.FeedbackModel;
import com.example.demo12.Response.GeneralResponse;
import com.example.demo12.Service.FeedBack.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FeedbackController {


        @Autowired

        private FeedbackService feedbackService;

        // Endpoint for posting feedback

        @PostMapping("/feedback")

        public ResponseEntity<?> post(@RequestBody FeedbackModel feedback) {

            try {

                feedbackService.post(feedback);

                return ResponseEntity.ok(new GeneralResponse("ok"));

            } catch (Exception e) {

                return ResponseEntity.badRequest().body("Error posting feedback: " + e.getMessage());

            }

        }

        // Endpoint for getting all feedback

        @GetMapping("/comments")

        public ResponseEntity<List<FeedbackModel>> getAllFeedback() {

            try {

                List<FeedbackModel> feedbackList = feedbackService.getAllFeedback();

                return ResponseEntity.ok(feedbackList);

            } catch (Exception e) {

                return ResponseEntity.badRequest().body(null); // Return empty response on error

            }

        }

    }


