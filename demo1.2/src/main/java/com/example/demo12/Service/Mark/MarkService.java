package com.example.demo12.Service.Mark;

import com.example.demo12.Model.Mark.marks;
import com.example.demo12.Repository.Mark.MarksRepository;
import com.example.demo12.Request.Mark.MarkRequest;
import com.example.demo12.Response.Mark.MarkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MarkService {
    @Autowired
    MarksRepository marksRepository;

    public List<marks> getMarks() {
        return marksRepository.findAll();
    }
//public List<marks> getMarks(MarkRequest markRequest) {
//    String trainerId = markRequest.getTrainerId();
//    String subject = markRequest.getSubject();
//    String studentType = markRequest.getStudentType();
//
//    List<marks> marksList;
//    if ("ReAttempt".equals(studentType)) {
//        // Fetch marks where the student is "ReAttempt" and marks are less than 75
//        marksList = marksRepository.findMarksBySubjectAndTrainerAndMarkLessThan(subject, trainerId, 75);
//    } else {
//        // Fetch all marks for non-"ReAttempt" students
//        marksList = marksRepository.findMarksBySubjectAndTrainer(subject, trainerId);
//    }
//    // Group by unique student ID and get the latest edit_date for each student
//    Map<String, marks> latestMarksMap = new HashMap<>();
//    for (marks studentMark : marksList) {
//        String studentId = studentMark.getStdent_id();
//        String editDate = studentMark.getEdit_date();
//
//        // If no mark exists for the student or if this edit_date is later, update the map
//        if (!latestMarksMap.containsKey(studentId) || isLaterDate(editDate, latestMarksMap.get(studentId).getEdit_date())) {
//            latestMarksMap.put(studentId, studentMark);
//        }
//    }
//
//    // Return the latest marks for each student
//    return new ArrayList<>(latestMarksMap.values());
//}

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


public MarkResponse publishMarks(MarkRequest markRequest) {
    List<marks> studentsMarks = markRequest.getMarklist();

    // Set the current date for all marks
//    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    for (marks studentMark : studentsMarks) {
        studentMark.setEdit_date(currentDate);  // Set the edit date
        studentMark.setTran_id(studentMark.getTran_id()); // Ensure trainer ID is set
        studentMark.setSubject(studentMark.getSubject()); // Ensure subject is set

        // If the id is null, Hibernate will treat it as a new record
        if (studentMark.getMark_id() == null) {
            // Log it if you want to verify that this is a new entry
            System.out.println("Saving new mark for student: " + studentMark.getTran_id());
        }
    }

    // Save all marks (if id is null, Hibernate will treat them as new records)
    marksRepository.saveAll(studentsMarks);

    MarkResponse markResponse = new MarkResponse();
    markResponse.setMsg("Marks published successfully");
    return markResponse;
}

    public List<marks> getMarks(MarkRequest markRequest) {
        String trainerId = markRequest.getTrainerId();
        String subject = markRequest.getSubject();
        String studentType = markRequest.getStudentType();

        // Fetch all marks for the given subject and trainer
        List<marks> marksList = marksRepository.findMarksBySubjectAndTrainer(subject, trainerId);

        // Map to store the latest mark for each student based on edit_date
        Map<String, marks> latestMarksMap = new HashMap<>();

        // Loop through the marks to get the latest mark for each student
        for (marks studentMark : marksList) {
            String studentId = studentMark.getStdent_id();
            String editDate = studentMark.getEdit_date();

            // If no mark exists for the student or if this edit_date is later, update the map
            if (!latestMarksMap.containsKey(studentId) || isLaterDate(editDate, latestMarksMap.get(studentId).getEdit_date())) {
                latestMarksMap.put(studentId, studentMark);
            }
        }

        // After grouping by the latest edit_date, filter based on studentType
        if ("ReAttempt".equals(studentType)) {
            // Filter out students who have passed (marks >= 75)
            List<marks> filteredMarksList = new ArrayList<>();
            for (marks studentMark : latestMarksMap.values()) {
                if (studentMark.getMark() < 75) {
                    filteredMarksList.add(studentMark);
                }
            }
            // Return only failed marks for ReAttempt students
            return filteredMarksList;
        } else {
            // For non-"ReAttempt" students, return the latest mark (whether failed or passed)
            return new ArrayList<>(latestMarksMap.values());
        }
    }


}
