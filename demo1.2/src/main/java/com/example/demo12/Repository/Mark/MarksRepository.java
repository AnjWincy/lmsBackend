package com.example.demo12.Repository.Mark;

import com.example.demo12.Model.Mark.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<Marks,Long> {
    @Query("SELECT m FROM Marks m WHERE m.subject = :subject AND m.tran_id = :tran_id")
    List<Marks> findMarksBySubjectAndTrainer(@Param("subject") String subject, @Param("tran_id") String tran_id);

    // Query to filter by Marks less than 75 for reattempt students
    @Query("SELECT m FROM Marks m WHERE m.subject = :subject AND m.tran_id = :tran_id AND m.mark < :mark")
    List<Marks> findMarksBySubjectAndTrainerAndMarkLessThan(@Param("subject") String subject, @Param("tran_id") String tran_id, @Param("mark") int mark);

    @Query("SELECT m FROM Marks m LEFT JOIN Student s ON m.stdent_id = s.rn_id WHERE s.rn_id = :rn_id")
    List<Marks> findByMarksId(@Param("rn_id") String rn_id);


}


