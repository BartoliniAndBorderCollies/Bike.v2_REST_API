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

@RestController
@RequestMapping("/api/feedback")
@AllArgsConstructor
public class FeedbackController {

    private final FeedbackServiceApi feedbackService;


    @PostMapping("/add")
    public ResponseEntity<?> addFeedback(@Valid @RequestBody Feedback feedback) {
        return feedbackService.add(feedback);
    }
}
