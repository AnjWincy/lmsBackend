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

        List<Marks> marksList = marksRepository.findMarksBySubjectAndTrainer(subject, trainerId);

        Map<String, Marks> latestMarksMap = new HashMap<>();

        for (Marks studentMark : marksList) {
            String studentId = studentMark.getStdent_id();
            String editDate = studentMark.getEdit_date();

            latestMarksMap.merge(studentId, studentMark, (existingMark, newMark) ->
                    isLaterDate(newMark.getEdit_date(), existingMark.getEdit_date()) ? newMark : existingMark);
        }

        if ("ReAttempt".equals(studentType)) {
            return latestMarksMap.values().stream()
                    .filter(studentMark -> studentMark.getMark() == null || studentMark.getMark() < 75)
                    .collect(Collectors.toList());  // Return only failed Marks or absent students
        }else if ("All".equals(studentType)) {
            return new ArrayList<>(latestMarksMap.values());  // Return all students
        } else {

            return new ArrayList<>(latestMarksMap.values());
        }
    }


    public MarkResponse publishMarks(MarkRequest markRequest) {
    List<Marks> studentsMarks = markRequest.getMarklist();


    String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    for (Marks studentMark : studentsMarks) {
        studentMark.setEdit_date(currentDate);
        studentMark.setTran_id(studentMark.getTran_id());
        studentMark.setSubject(studentMark.getSubject());
        if (studentMark.getMark_id() == null) {
            System.out.println("Saving new mark for Student: " + studentMark.getTran_id());
        }
    }

    marksRepository.saveAll(studentsMarks);

    MarkResponse markResponse = new MarkResponse();
    markResponse.setMsg("Marks published successfully");
    return markResponse;
}


    public Map<String, Map<String, Long>> getSubjectStats() {
        List<Marks> marks = marksRepository.findAll();

        return marks.stream()
                .filter(mark -> mark.getMark() != null)
                .collect(Collectors.groupingBy(
                        Marks::getSubject,
                        Collectors.groupingBy(
                                mark -> mark.getMark() >= 75 ? "Pass" : "Fail",
                                Collectors.counting()
                        )
                ));
    }



}
