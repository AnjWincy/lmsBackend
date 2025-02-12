package com.example.demo12.ServiceTest;

import com.example.demo12.Model.details.Trainer;
import com.example.demo12.Repository.DetailsRepository.TrainerRepository;
import com.example.demo12.Service.Details.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer;

    @BeforeEach
    public void setup() {

        trainer = new Trainer();
        trainer.setTrainer_id("T123");
        trainer.setTrainer_name("John Trainer");
        trainer.setRole("Full-Stack");
        trainer.setPh_no("1234566");
        trainer.setEmail("john@gmail.com");
    }

    @Test
    public void testSaveOrUpdateTrainer_NewTrainer() {
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        Trainer savedTrainer = trainerService.saveOrUpdateTrainer(trainer);

        assertNotNull(savedTrainer);
        assertEquals("T123", savedTrainer.getTrainer_id());
        assertEquals("John Trainer", savedTrainer.getTrainer_name());

        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    public void testSaveOrUpdateTrainer_ExistingTrainer() {
        when(trainerRepository.existsById("T123")).thenReturn(true);
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        Trainer updatedTrainer = trainerService.saveOrUpdateTrainer(trainer);

        assertNotNull(updatedTrainer);
        assertEquals("T123", updatedTrainer.getTrainer_id());
        assertEquals("John Trainer", updatedTrainer.getTrainer_name());

        // Verify that save was called once
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    public void testGetAllTrainers() {
        when(trainerRepository.findAll()).thenReturn(Arrays.asList(trainer));

        var trainers = trainerService.getAllTrainers();

        assertNotNull(trainers);
        assertEquals(1, trainers.size());
        assertEquals("John Trainer", trainers.get(0).getTrainer_name());
        verify(trainerRepository, times(1)).findAll();
    }

    @Test
    public void testGetTrainerById_Found() {
        when(trainerRepository.findById("T123")).thenReturn(Optional.of(trainer));

        Optional<Trainer> fetchedTrainer = trainerService.getTrainerById("T123");

        assertTrue(fetchedTrainer.isPresent());
        assertEquals("T123", fetchedTrainer.get().getTrainer_id());
        assertEquals("John Trainer", fetchedTrainer.get().getTrainer_name());

        verify(trainerRepository, times(1)).findById("T123");
    }

    @Test
    public void testGetTrainerById_NotFound() {
        when(trainerRepository.findById("T999")).thenReturn(Optional.empty());

        Optional<Trainer> fetchedTrainer = trainerService.getTrainerById("T999");

        assertFalse(fetchedTrainer.isPresent());
        verify(trainerRepository, times(1)).findById("T999");
    }

    @Test
    public void testDeleteTrainer() {
        doNothing().when(trainerRepository).deleteById("T123");

        trainerService.deleteTrainer("T123");
        verify(trainerRepository, times(1)).deleteById("T123");
    }

    @Test
    public void testCountTrainers() {
        // Mock behavior to return the count of trainers
        when(trainerRepository.count()).thenReturn(5L);

        Long count = trainerService.countTrainers();

        assertNotNull(count);
        assertEquals(5L, count);
        verify(trainerRepository, times(1)).count();
    }

}
