package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.model.entity.Feedback;
import com.klodnicki.Bike.v2.repository.FeedbackRepository;
import com.klodnicki.Bike.v2.service.crudInterface.AddService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeedbackService implements AddService<ResponseEntity<?>, Feedback> {

    private final FeedbackRepository feedbackRepository;

    public ResponseEntity<?> add(Feedback feedback) {
        feedbackRepository.save(feedback);

        return new ResponseEntity<>("Thanks for your feedback!", HttpStatus.OK);
    }
}
