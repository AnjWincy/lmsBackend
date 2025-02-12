package com.example.demo12.Service.Details;


import com.example.demo12.Model.details.Trainer;
import com.example.demo12.Repository.DetailsRepository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerService {
    @Autowired
    TrainerRepository trainerRepository;



    public Trainer saveOrUpdateTrainer(Trainer trainer) {
        // Check if trainer has a valid ID and exists in the database
        if (trainer.getTrainer_id() != null && trainerRepository.existsById(trainer.getTrainer_id())) {
            // If trainer exists, update it
            return trainerRepository.save(trainer);
        } else {
            // If the trainer is new, create a new record
            return trainerRepository.save(trainer);
        }
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    // Get trainer by ID
    public Optional<Trainer> getTrainerById(String trainer_id) {
        return trainerRepository.findById(trainer_id);
    }


    public void deleteTrainer(String trainer_id) {
        trainerRepository.deleteById(trainer_id);
    }


    public Long countTrainers(){
        return trainerRepository.count();
    }
}
