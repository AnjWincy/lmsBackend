package com.example.demo12.Repository.DetailsRepository;

import com.example.demo12.Model.details.trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<trainer,String> {
    @Query("SELECT l FROM trainer l  WHERE l.email = ?1 AND l.password = ?2")
    trainer validateTrainer(String email, String password);

    trainer findByEmail(String email);
}
