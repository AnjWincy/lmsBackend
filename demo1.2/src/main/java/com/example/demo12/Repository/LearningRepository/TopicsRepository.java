package com.example.demo12.Repository.LearningRepository;


import com.example.demo12.Model.learning.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicsRepository extends JpaRepository<Topics,Long> {
    @Query("SELECT t FROM Topics t LEFT JOIN Courses c ON t.c_id = c.course_id WHERE c.course_id = :course_id ORDER BY t.topic_id")
    List<Topics> findByCourseId(@Param("course_id") Long course_id);

//    @Query("SELECT t FROM Topics t LEFT JOIN Courses c ON t.course_id = c.c_id WHERE c.c_id = :c_id")
//    List<Topics> findByCourseId(@Param("c_id") Long c_id);
}
