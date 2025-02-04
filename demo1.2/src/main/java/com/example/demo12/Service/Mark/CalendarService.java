package com.example.demo12.Service.Mark;

import com.example.demo12.Model.Mark.CalendarModel;
import com.example.demo12.Repository.Mark.CalendarRepository;
import com.example.demo12.Request.Mark.CalendarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalendarService {
    @Autowired
    CalendarRepository calendarRepository;


    public Map<String, List<CalendarModel>> getScheduled(){
        List<CalendarModel> um =  calendarRepository.findAll();
        // Filter users with a date greater than today
        List<CalendarModel> scheduled = um.stream()
                .filter(l -> l.getDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
        List<CalendarModel> completed = um.stream()
                .filter(l -> l.getDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());

        Map<String,  List<CalendarModel>> result = new HashMap<>();
        result.put("completed", completed);
        result.put("scheduled", scheduled);

        return result;

    }

    public Map<String, Long> CompletedCount(){
        List<CalendarModel> um =  calendarRepository.findAll();
        Long completed = um.stream()
                .filter(l -> l.getDate().isBefore(LocalDate.now()))
                .count();
        Long scheduled = um.stream()
                .filter(l -> l.getDate().isAfter(LocalDate.now()))
                .count();
        Map<String, Long> result = new HashMap<>();
        result.put("completed", completed);
        result.put("scheduled", scheduled);

        return result;  // Return the map with both counts
    }


    public void deleteById(Long id){
        calendarRepository.deleteById(id);
    }

    public void registerUser(CalendarRequest dbRequest) throws Exception{

        try{
            CalendarModel calendarModel =new CalendarModel();
            calendarModel.setTitle(dbRequest.getTitle());
            calendarModel.setDate(dbRequest.getDate());
            calendarRepository.save(calendarModel);

        }

        catch (Exception e){
            throw new Exception("Error"+e.getMessage());
        }
    }

    public void updateUser(Long id, CalendarRequest dbRequest) throws Exception {
        try {
            Optional<CalendarModel> existingUserOpt = calendarRepository.findById(id);

            if (existingUserOpt.isPresent()) {
                CalendarModel existingUser = existingUserOpt.get();
                existingUser.setTitle(dbRequest.getTitle());
                existingUser.setDate(dbRequest.getDate());

                // Save updated user
                calendarRepository.save(existingUser);
            } else {
                throw new Exception("User with ID " + id + " not found.");
            }
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }
}
