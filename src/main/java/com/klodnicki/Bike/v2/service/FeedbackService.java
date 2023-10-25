package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.model.entity.Feedback;
import com.klodnicki.Bike.v2.repository.FeedbackRepository;
import com.klodnicki.Bike.v2.service.crudInterface.AddService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService implements AddService<ResponseEntity<?>, Feedback> {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public ResponseEntity<?> add(Feedback feedback) {
        feedbackRepository.save(feedback);

        return new ResponseEntity<>("Thanks for your feedback!", HttpStatus.OK);
    }
}
