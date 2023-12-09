package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.model.entity.Feedback;
import com.klodnicki.Bike.v2.repository.FeedbackRepository;
import com.klodnicki.Bike.v2.service.api.FeedbackServiceApi;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeedbackServiceHandler implements FeedbackServiceApi {

    private final FeedbackRepository feedbackRepository;

    /**
     * This method is used to add a new {@link Feedback}.
     * It saves the feedback object in the repository and returns a ResponseEntity with a thank you message and HTTP status OK.
     *
     * @param feedback The feedback to be added.
     * @return ResponseEntity A response entity containing a thank you message and HTTP status OK.
     */
    @Override
    public ResponseEntity<?> add(Feedback feedback) {
        feedbackRepository.save(feedback);

        return new ResponseEntity<>("Thanks for your feedback!", HttpStatus.OK);
    }
}
