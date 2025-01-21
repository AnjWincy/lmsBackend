package com.example.demo12.Repository.DetailsRepository;

import com.example.demo12.Model.details.student;
import com.example.demo12.Model.learning.topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<student,String>{
    @Query("SELECT l FROM student l WHERE l.email = ?1 AND l.password = ?2")
    student validateUser(String email, String password);

    student findByEmail(String email);


    @Query("select s.rn_id from student s")
    List<String> getStudentIds();

    @Query("SELECT s.rn_id FROM student s LEFT JOIN trainer t ON s.t_id = t.trainer_id WHERE t.trainer_id = :trainer_id")
    List<String> findByTrainerId(@Param("trainer_id") String trainer_id);

}
