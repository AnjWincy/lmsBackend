package com.example.demo12.Controller.Mark;

import com.example.demo12.Model.Mark.CalendarModel;
import com.example.demo12.Request.Mark.CalendarRequest;
import com.example.demo12.Response.Mark.CalendarResponse;
import com.example.demo12.Service.Mark.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CalendarController {
    @Autowired
    CalendarService calendarService;

    @GetMapping("/scheduled")
    public ResponseEntity<?> getUserList(){
        //calling the service method
        Map<String, List<CalendarModel>> userModelList= calendarService.getScheduled();
        //return the list into json format
        CalendarResponse se=new CalendarResponse("sjals");
        se.setRes1(userModelList);
        return ResponseEntity.ok(userModelList);

    }

    @GetMapping("/Count")
    public ResponseEntity<?> getUsercount(){
        //calling the service method
        Map<String, Long> userModelList= calendarService.CompletedCount();
        //return the list into json format
        CalendarResponse user=new CalendarResponse("schedule");
        user.setRes(userModelList);
        return ResponseEntity.ok(userModelList);

    }
    //localhost:8080/deleteById?id=45
    @GetMapping("/deleteById")
    public ResponseEntity<?> deleteById(@RequestParam Long id){
        calendarService.deleteById(id);
        return ResponseEntity.ok(new CalendarResponse("Deleted."));
    }

    @PostMapping("/Db")
    public ResponseEntity<?> userRegister(@RequestBody CalendarRequest dbRequest) {
        try {
            // Attempt to register the user using the service
            calendarService.registerUser(dbRequest);

            // Return a successful response
            return ResponseEntity.ok(new CalendarResponse("Successfull"));

        } catch (Exception e) {
            // Return a bad request response if an error occurs
            return ResponseEntity.badRequest().body(new CalendarResponse(e.getMessage()));
        }
    }
    // Update an existing user
    @GetMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody CalendarRequest dbRequest) {
        try {
            // Call the service to update the user
            calendarService.updateUser(id, dbRequest);
            return ResponseEntity.ok(new CalendarResponse("User updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CalendarResponse("Error: " + e.getMessage()));
        }
    }
}
