package com.example.demo12.ServiceTest;

import com.example.demo12.Model.Mark.Marks;
import com.example.demo12.Repository.Mark.MarksRepository;
import com.example.demo12.Request.Mark.MarkRequest;
import com.example.demo12.Response.Mark.MarkResponse;
import com.example.demo12.Service.Mark.MarkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MarkServiceTest {

    @Mock
    private MarksRepository marksRepository;

    @InjectMocks
    private MarkService markService;

    private Marks mark1;
    private Marks mark2;
    private Marks mark3;

    private MarkRequest markRequest;

    @BeforeEach
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create mock Marks objects
        mark1 = new Marks();
        mark1.setMark_id(1L);
        mark1.setMark(80L);
        mark1.setSubject("Math");
        mark1.setStdent_id("S1");
        mark1.setTran_id("T1");
        mark1.setEdit_date("2025-02-10 10:00:00");

        mark2 = new Marks();
        mark2.setMark_id(2L);
        mark2.setMark(50L);  // Failing mark
        mark2.setSubject("Math");
        mark2.setStdent_id("S1");
        mark2.setTran_id("T1");
        mark2.setEdit_date("2025-02-11 9:00:00");

        mark3 = new Marks();
        mark3.setMark_id(3L);
        mark3.setMark(85L);  // Passing mark
        mark3.setSubject("Math");
        mark3.setStdent_id("S2");
        mark3.setTran_id("T1");
        mark3.setEdit_date("2025-02-11 09:00:00");

        // Create mock MarkRequest
        markRequest = new MarkRequest();
        markRequest.setTrainerId("T1");
        markRequest.setSubject("Math");
        markRequest.setStudentType("ReAttempt");
        markRequest.setMarklist(Arrays.asList(mark1, mark2, mark3));
    }

    @Test
    public void testGetMarks_ReAttempt() {
        // Mock repository behavior
        when(marksRepository.findMarksBySubjectAndTrainer("Math", "T1"))
                .thenReturn(Arrays.asList(mark1, mark2, mark3));

        // Call the method
        List<Marks> result = markService.getMarks(markRequest);

        // Assert the result
        assertEquals(1, result.size());  // Only the student who failed should be returned
        assertTrue(result.stream().anyMatch(m -> m.getStdent_id().equals("S1")));  // "S1" has failed
    }

    @Test
    public void testPublishMarks() {
        // Mock behavior of saveAll
        when(marksRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));  // Return the same list passed to saveAll

        // Call the method
        MarkResponse response = markService.publishMarks(markRequest);

        // Verify that marksRepository.saveAll was called once
        verify(marksRepository, times(1)).saveAll(anyList());

        // Assert the response
        assertNotNull(response);
        assertEquals("Marks published successfully", response.getMsg());
    }

    @Test
    public void testGetSubjectStats() {
        // Mock repository behavior
        when(marksRepository.findAll()).thenReturn(Arrays.asList(mark1, mark2, mark3));

        // Call the method
        Map<String, Map<String, Long>> result = markService.getSubjectStats();

        // Assert the result
        assertNotNull(result);
        assertTrue(result.containsKey("Math"));
        assertTrue(result.get("Math").containsKey("Pass"));
        assertTrue(result.get("Math").containsKey("Fail"));

        assertEquals(2, result.get("Math").get("Pass"));
        assertEquals(1, result.get("Math").get("Fail"));
    }

    @Test
    public void testGetMarks_AllStudents() {
        markRequest.setStudentType("All");

        // Mock repository behavior
        when(marksRepository.findMarksBySubjectAndTrainer("Math", "T1"))
                .thenReturn(Arrays.asList(mark1, mark2, mark3));  // Ensure all 3 students are in the mock


        // Call the method
        List<Marks> result = markService.getMarks(markRequest);

        // Assert the result
        assertEquals(2, result.size());  // All 3 students should be returned
    }

    @Test
    public void testGetMarks_EmptyList() {
        // Mock repository behavior for no marks
        when(marksRepository.findMarksBySubjectAndTrainer("Math", "T1")).thenReturn(Collections.emptyList());

        // Call the method
        List<Marks> result = markService.getMarks(markRequest);

        // Assert the result is empty
        assertTrue(result.isEmpty());
    }




    @Test
    public void testPublishMarks_NewMark() {
        Marks newMark = new Marks();
        newMark.setMark(null);  // New mark without an existing ID
        newMark.setSubject("Math");
        newMark.setStdent_id("S3");
        newMark.setTran_id("T1");
        newMark.setEdit_date("2025-02-12 10:00:00");

        markRequest.setMarklist(Arrays.asList(newMark));

        // Mock behavior of saveAll
        when(marksRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));  // Return the same list passed to saveAll

        // Call the method
        MarkResponse response = markService.publishMarks(markRequest);

        // Verify that marksRepository.saveAll was called once
        verify(marksRepository, times(1)).saveAll(anyList());

        // Assert the response
        assertNotNull(response);
        assertEquals("Marks published successfully", response.getMsg());
    }


}
