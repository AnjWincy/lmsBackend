package com.example.demo12.Service.Mark;

import com.example.demo12.Model.Mark.Marks;
import com.example.demo12.Repository.Mark.MarksRepository;
import com.example.demo12.Request.Mark.MarkRequest;
import com.example.demo12.Response.Mark.MarkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MarkService {
    @Autowired
    MarksRepository marksRepository;

    private boolean isLaterDate(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            return d1.after(d2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Marks> getMarks(MarkRequest markRequest) {
        String trainerId = markRequest.getTrainerId();
        String subject = markRequest.getSubject();
        String studentType = markRequest.getStudentType();

        // Fetch all Marks for the given subject and Trainer, including absent students
        List<Marks> marksList = marksRepository.findMarksBySubjectAndTrainer(subject, trainerId);

        // Map to store the latest mark for each Student based on edit_date
        Map<String, Marks> latestMarksMap = new HashMap<>();

        // Loop through the Marks to get the latest mark for each Student
        for (Marks studentMark : marksList) {
            String studentId = studentMark.getStdent_id();
            String editDate = studentMark.getEdit_date();

            // If no mark exists for the Student or if this edit_date is later, update the map
            latestMarksMap.merge(studentId, studentMark, (existingMark, newMark) ->
                    isLaterDate(newMark.getEdit_date(), existingMark.getEdit_date()) ? newMark : existingMark);
        }

        // Filter based on studentType and return the filtered list
        if ("ReAttempt".equals(studentType)) {
            return latestMarksMap.values().stream()
                    .filter(studentMark -> studentMark.getMark() == null || studentMark.getMark() < 75)
                    .collect(Collectors.toList());  // Return only failed Marks or absent students
        } else {
            // For non-"ReAttempt" students, return the latest mark only for students with Marks >= 75
//            return latestMarksMap.values().stream()
//                    .filter(studentMark -> studentMark.getMark() != null && studentMark.getMark() >= 75)
//                    .collect(Collectors.toList());  // Return only students who passed (Marks >= 75)
            return new ArrayList<>(latestMarksMap.values());
        }
    }



    //Old getMarks
public MarkResponse publishMarks(MarkRequest markRequest) {
    List<Marks> studentsMarks = markRequest.getMarklist();

    // Set the current date for all Marks
//    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    for (Marks studentMark : studentsMarks) {
        studentMark.setEdit_date(currentDate);  // Set the edit date
        studentMark.setTran_id(studentMark.getTran_id()); // Ensure Trainer ID is set
        studentMark.setSubject(studentMark.getSubject()); // Ensure subject is set
        // If the id is null, Hibernate will treat it as a new record
        if (studentMark.getMark_id() == null) {
            // Log it if you want to verify that this is a new entry
            System.out.println("Saving new mark for Student: " + studentMark.getTran_id());
        }
    }

    // Save all Marks (if id is null, Hibernate will treat them as new records)
    marksRepository.saveAll(studentsMarks);

    MarkResponse markResponse = new MarkResponse();
    markResponse.setMsg("Marks published successfully");
    return markResponse;
}


    public Map<String, Map<String, Long>> getSubjectStats() {
        List<Marks> marks = marksRepository.findAll();  // Fetch all marks data from DB

        // Group marks by subject and calculate pass/fail counts, ignoring null marks
        return marks.stream()
                .filter(mark -> mark.getMark() != null)  // Filter out marks that are null
                .collect(Collectors.groupingBy(
                        Marks::getSubject,
                        Collectors.groupingBy(
                                mark -> mark.getMark() >= 75 ? "Pass" : "Fail",  // Group by Pass/Fail
                                Collectors.counting()
                        )
                ));
    }

//Old getMarks


//    public List<Marks> getMarks(MarkRequest markRequest) {
//        String trainerId = markRequest.getTrainerId();
//        String subject = markRequest.getSubject();
//        String studentType = markRequest.getStudentType();
//
//        // Fetch all Marks for the given subject and Trainer
//        List<Marks> marksList = marksRepository.findMarksBySubjectAndTrainer(subject, trainerId);
//
//        // Map to store the latest mark for each Student based on edit_date
//        Map<String, Marks> latestMarksMap = new HashMap<>();
//
//        // Loop through the Marks to get the latest mark for each Student
//        for (Marks studentMark : marksList) {
//            String studentId = studentMark.getStdent_id();
//            String editDate = studentMark.getEdit_date();
//
//            // If no mark exists for the Student or if this edit_date is later, update the map
//            if (!latestMarksMap.containsKey(studentId) || isLaterDate(editDate, latestMarksMap.get(studentId).getEdit_date())) {
//                latestMarksMap.put(studentId, studentMark);
//            }
//        }
//
//        // After grouping by the latest edit_date, filter based on studentType
//        if ("ReAttempt".equals(studentType)) {
//            // Filter out students who have passed (Marks >= 75)
//            List<Marks> filteredMarksList = new ArrayList<>();
//            for (Marks studentMark : latestMarksMap.values()) {
//                if (studentMark.getMark() < 75) {
//                    filteredMarksList.add(studentMark);
//                }
//            }
//            // Return only failed Marks for ReAttempt students
//            return filteredMarksList;
//        } else {
//            // For non-"ReAttempt" students, return the latest mark (whether failed or passed)
//            return new ArrayList<>(latestMarksMap.values());
//        }
//    }


}
