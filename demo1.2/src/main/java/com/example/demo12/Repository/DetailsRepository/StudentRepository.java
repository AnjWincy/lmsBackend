package com.example.demo12.Repository.DetailsRepository;

import com.example.demo12.Model.details.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,String>{
    @Query("SELECT l FROM Student l WHERE l.email = ?1 AND l.password = ?2")
    Student validateUser(String email, String password);

    Student findByEmail(String email);

    @Query("select s from Student s WHERE s.rn_id=:rn_id")
    Student getStudentByRnId(@Param("rn_id") String rn_id);


    @Query("select s.rn_id from Student s")
    List<String> getStudentIds();

    @Query("SELECT s.rn_id FROM Student s LEFT JOIN Trainer t ON s.t_id = t.trainer_id WHERE t.trainer_id = :trainer_id")
    List<String> findByTrainerId(@Param("trainer_id") String trainer_id);

}
