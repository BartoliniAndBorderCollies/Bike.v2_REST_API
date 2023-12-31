package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.model.entity.Feedback;
import com.klodnicki.Bike.v2.service.api.operation.AddOperation;
import org.springframework.http.ResponseEntity;

/**
 * This interface defines the operations for the FeedbackService.
 * It extends the AddOperation interface.
 */
public interface FeedbackServiceApi extends AddOperation<ResponseEntity<?>, Feedback> {
}
