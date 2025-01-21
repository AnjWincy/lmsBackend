package com.example.demo12.Repository.Mark;

import com.example.demo12.Model.Mark.marks;
import com.example.demo12.Model.learning.topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<marks,Long> {
    @Query("SELECT m FROM marks m WHERE m.subject = :subject AND m.tran_id = :tran_id")
    List<marks> findMarksBySubjectAndTrainer(@Param("subject") String subject, @Param("tran_id") String tran_id);

    // Query to filter by marks less than 75 for reattempt students
    @Query("SELECT m FROM marks m WHERE m.subject = :subject AND m.tran_id = :tran_id AND m.mark < :mark")
    List<marks> findMarksBySubjectAndTrainerAndMarkLessThan(@Param("subject") String subject, @Param("tran_id") String tran_id, @Param("mark") int mark);

    @Query("SELECT m FROM marks m LEFT JOIN student s ON m.stdent_id = s.rn_id WHERE s.rn_id = :rn_id")
    List<marks> findByMarksId(@Param("rn_id") String rn_id);


}


