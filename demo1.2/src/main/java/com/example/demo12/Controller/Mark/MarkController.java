package com.example.demo12.Controller.Mark;

import com.example.demo12.Model.Mark.marks;
import com.example.demo12.Request.Mark.MarkRequest;
import com.example.demo12.Response.Mark.MarkResponse;
import com.example.demo12.Service.Mark.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MarkController {
    @Autowired
    MarkService markService;

    @PostMapping("/marks")
    public List<marks> getMarks(@RequestBody MarkRequest markRequest) {
        // Pass the MarkRequest to the service to fetch filtered marks
        return markService.getMarks(markRequest);
    }


//    @PostMapping("/publish_marks")
//    public MarkResponse publishMarks(@RequestBody MarkRequest markRequest) {
//        return markService.publishMarks(markRequest);
//    }
    @PostMapping("/publish_marks")
    public MarkResponse publishMarks(@RequestBody MarkRequest markRequest) {
        if (markRequest.getMarklist() == null || markRequest.getMarklist().isEmpty()) {
            throw new IllegalArgumentException("No marks data found in the request body");
        }

        return markService.publishMarks(markRequest);
    }

}
