package com.klodnicki.Bike.v2.service.interfacee;

import com.klodnicki.Bike.v2.model.entity.Feedback;
import com.klodnicki.Bike.v2.service.interfacee.basic.operation.AddService;
import org.springframework.http.ResponseEntity;

public interface GenericFeedbackService extends AddService<ResponseEntity<?>, Feedback> {
}
