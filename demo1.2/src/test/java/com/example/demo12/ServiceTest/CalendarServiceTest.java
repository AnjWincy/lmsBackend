package com.example.demo12.ServiceTest;



import com.example.demo12.Model.Mark.CalendarModel;
import com.example.demo12.Repository.Mark.CalendarRepository;
import com.example.demo12.Request.Mark.CalendarRequest;
import com.example.demo12.Service.Mark.CalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class CalendarServiceTest {

    @InjectMocks
    private CalendarService calendarService;

    @Mock
    private CalendarRepository calendarRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetScheduled() {
        CalendarModel model1 = new CalendarModel();
        model1.setDate(LocalDate.now().plusDays(1));

        CalendarModel model2 = new CalendarModel();
        model2.setDate(LocalDate.now().minusDays(1));

        List<CalendarModel> calendarModels = Arrays.asList(model1, model2);

        when(calendarRepository.findAll()).thenReturn(calendarModels);

        Map<String, List<CalendarModel>> result = calendarService.getScheduled();

        assertEquals(1, result.get("scheduled").size());
        assertEquals(1, result.get("completed").size());
    }


    @Test
    public void testDeleteById() {
        Long id = 1L;


        calendarService.deleteById(id);
        verify(calendarRepository, times(1)).deleteById(id);
    }

    @Test
    public void testAddEvent() throws Exception {
        CalendarRequest request = new CalendarRequest();
        request.setTitle("Test Event");
        request.setDate(LocalDate.now().plusDays(5));
        calendarService.registerUser(request);
        verify(calendarRepository, times(1)).save(any(CalendarModel.class));
    }

    @Test
    public void testAddEventThrowsException() {
        CalendarRequest request = new CalendarRequest();
        request.setTitle("Test Event");
        request.setDate(LocalDate.now().plusDays(5));

        // Simulate a repository exception
        doThrow(new RuntimeException("Database error")).when(calendarRepository).save(any(CalendarModel.class));

        // Test the method and expect exception
        Exception exception = assertThrows(Exception.class, () -> calendarService.registerUser(request));

        assertTrue(exception.getMessage().contains("Error"));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        Long id = 1L;
        CalendarRequest request = new CalendarRequest();
        request.setTitle("Updated Event");
        request.setDate(LocalDate.now().plusDays(5));

        CalendarModel existingModel = new CalendarModel();
        existingModel.setId(id);
        existingModel.setTitle("Old Event");
        existingModel.setDate(LocalDate.now().minusDays(2));

        // Mock repository behavior
        when(calendarRepository.findById(id)).thenReturn(Optional.of(existingModel));

        // Test update
        calendarService.updateUser(id, request);

        // Verify that the save method is called
        verify(calendarRepository, times(1)).save(existingModel);
        assertEquals("Updated Event", existingModel.getTitle());
        assertEquals(LocalDate.now().plusDays(5), existingModel.getDate());
    }

    @Test
    public void testUpdateEventThrowsException() {
        Long id = 1L;
        CalendarRequest request = new CalendarRequest();
        request.setTitle("Updated Event");
        request.setDate(LocalDate.now().plusDays(5));

        // Mock repository behavior
        when(calendarRepository.findById(id)).thenReturn(Optional.empty());

        // Test update and expect exception
        Exception exception = assertThrows(Exception.class, () -> calendarService.updateUser(id, request));

        assertTrue(exception.getMessage().contains("not found"));
    }
}
