package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.model.entity.Feedback;
import com.klodnicki.Bike.v2.repository.FeedbackRepository;
import configuration.IntegrationTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
@Import(IntegrationTestConfig.class)
class FeedbackControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private FeedbackRepository feedbackRepository;

    @AfterEach
    public void clearDatabase() {
        feedbackRepository.deleteAll();
    }

    @Test
    public void addFeedback_ShouldSaveFeedbackAndReturnCorrectResponse_WhenProvidedAnyFeedback() {

        Feedback feedback = new Feedback(null, "feedback test");
        feedbackRepository.save(feedback);

        webTestClient.post()
                .uri("/api/feedback/add")
                .bodyValue(feedback)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String stringResponse = response.getResponseBody();
                    assertNotNull(stringResponse);
                    assertEquals("Thanks for your feedback!", stringResponse);
                    Feedback savedFeedback = feedbackRepository.findById(feedback.getId()).orElseThrow(IllegalArgumentException::new);
                    assertEquals(feedback, savedFeedback);
                });
    }

}