package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.model.entity.Feedback;
import com.klodnicki.Bike.v2.service.api.FeedbackServiceApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing feedback.
 * It provides an endpoint for adding new feedback.
 */
@RestController
@RequestMapping("/api/feedback")
@AllArgsConstructor
public class FeedbackController {

    /**
     * Service for handling feedback-related operations.
     */
    private final FeedbackServiceApi feedbackService;


    /**
     * This method is used to add a new feedback.
     * It takes a Feedback object as input and returns a ResponseEntity object.
     * The Feedback must fulfil some requirements since it is preceded by @Valid.
     *
     * @param feedback This is a request object which contains the details of the feedback to be added.
     * @return ResponseEntity This returns the response entity after the feedback has been added.
     *
     * @PostMapping("/add")
     */
    @PostMapping("/add")
    public ResponseEntity<?> addFeedback(@Valid @RequestBody Feedback feedback) {
        return feedbackService.add(feedback);
    }
}
