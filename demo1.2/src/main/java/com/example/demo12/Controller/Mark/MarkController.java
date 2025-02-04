package com.example.demo12.Controller.Mark;

import com.example.demo12.Model.Mark.Marks;
import com.example.demo12.Request.Mark.MarkRequest;
import com.example.demo12.Response.Mark.MarkResponse;
import com.example.demo12.Service.Mark.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MarkController {
    @Autowired
    MarkService markService;

    @PostMapping("/Marks")
    public List<Marks> getMarks(@RequestBody MarkRequest markRequest) {
        // Pass the MarkRequest to the service to fetch filtered Marks
        return markService.getMarks(markRequest);
    }

    @PostMapping("/publish_marks")
    public MarkResponse publishMarks(@RequestBody MarkRequest markRequest) {
        if (markRequest.getMarklist() == null || markRequest.getMarklist().isEmpty()) {
            throw new IllegalArgumentException("No Marks data found in the request body");
        }

        return markService.publishMarks(markRequest);
    }

    @GetMapping("/subject-stats")
    public ResponseEntity<Map<String, Map<String, Long>>> getSubjectStats() {
        try {
            Map<String, Map<String, Long>> subjectStats = markService.getSubjectStats();
            if (subjectStats == null || subjectStats.isEmpty()) {
                // Return a 204 No Content if no statistics are available
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(subjectStats, HttpStatus.OK);
        } catch (Exception e) {
            // Log the error and return a proper error response
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
