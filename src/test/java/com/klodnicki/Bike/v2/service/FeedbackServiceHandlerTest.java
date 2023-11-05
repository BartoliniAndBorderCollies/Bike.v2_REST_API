package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.model.entity.Feedback;
import com.klodnicki.Bike.v2.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackServiceHandlerTest {

    private FeedbackServiceHandler feedbackServiceHandler;

    private FeedbackRepository feedbackRepository; // our lovely mock

    @BeforeEach
    public void setUp() {
        feedbackRepository = mock(FeedbackRepository.class);
        feedbackServiceHandler = new FeedbackServiceHandler(feedbackRepository);
    }

    @Test
    public void add_ShouldReturnResponseEntityWithCodeOK_WhenGivenFeedbackObject() {
        // Arrange
        Feedback feedback = new Feedback();

        when(feedbackRepository.save(any())).thenReturn(new Feedback(1L, "mocked comment")); // configure mock

        // Act
        ResponseEntity<?> response = feedbackServiceHandler.add(feedback);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

    }

}