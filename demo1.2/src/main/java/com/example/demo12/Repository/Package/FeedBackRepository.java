package com.example.demo12.Repository.Package;

import com.example.demo12.Model.FeedBack.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository extends JpaRepository<FeedbackModel,Long> {
}
