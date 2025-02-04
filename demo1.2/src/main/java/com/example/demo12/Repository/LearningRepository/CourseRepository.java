package com.example.demo12.Repository.LearningRepository;

import com.example.demo12.Model.learning.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses,Long> {
}
