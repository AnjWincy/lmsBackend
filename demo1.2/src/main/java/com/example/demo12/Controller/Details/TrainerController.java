package com.example.demo12.Controller.Details;

import com.example.demo12.Model.details.Trainer;
import com.example.demo12.Service.Details.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TrainerController {
    @Autowired
    TrainerService trainerService;


    @PostMapping("/addTrainer")
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        Trainer savedTrainer = trainerService.saveOrUpdateTrainer(trainer);
        return new ResponseEntity<>(savedTrainer, HttpStatus.CREATED);
    }

    // Update an existing trainer (using PUT)
    @PutMapping("/addTrainer/{trainer_id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable String trainer_id, @RequestBody Trainer trainer) {
        Optional<Trainer> existingTrainer = trainerService.getTrainerById(trainer_id);

        if (existingTrainer.isPresent()) {
            trainer.setTrainer_id(trainer_id); // Ensure we keep the same ID for the trainer
            Trainer updatedTrainer = trainerService.saveOrUpdateTrainer(trainer);
            return new ResponseEntity<>(updatedTrainer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get all trainers
    @GetMapping("/getAllTrainer")
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        List<Trainer> trainers = trainerService.getAllTrainers();
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }

    // Get a trainer by ID
    @GetMapping("/getTrainer/{trainer_id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable String trainer_id) {
        Optional<Trainer> trainer = trainerService.getTrainerById(trainer_id);
        if (trainer.isPresent()) {
            return new ResponseEntity<>(trainer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a trainer by ID
    @DeleteMapping("/deleteTrainer/{trainer_id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable String trainer_id) {
        Optional<Trainer> trainer = trainerService.getTrainerById(trainer_id);
        if (trainer.isPresent()) {
            trainerService.deleteTrainer(trainer_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/trainerCount")
    public ResponseEntity<Long> getStudentCount() {
        long count = trainerService.countTrainers(); // Assuming you have this method in your service.
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
