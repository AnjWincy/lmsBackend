package com.example.demo12.Repository.DetailsRepository;

import com.example.demo12.Model.details.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer,String> {
    @Query("SELECT l FROM Trainer l  WHERE l.email = ?1 AND l.password = ?2")
    Trainer validateTrainer(String email, String password);

    Trainer findByEmail(String email);
}
