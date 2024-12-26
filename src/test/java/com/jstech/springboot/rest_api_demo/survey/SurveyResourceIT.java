package com.jstech.springboot.rest_api_demo.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    private static final String GET_SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
    private static final String SURVEY1_QUESTIONS_URL = "/surveys/Survey1/questions";

    @Autowired
    private TestRestTemplate testRestTemplate;


    /*
        1. HTTP Response
        2. Content Type
        3. Body
    */

    // GET Method Tests

    @Test
    void retrieveSurveyQuestionById_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(GET_SPECIFIC_QUESTION_URL, String.class);
        String expectedResponse = """
                {
                    "id":"Question1",
                    "description":"Most Popular Cloud Platform Today",
                    "option":["AWS","Azure","Google Cloud","Oracle Cloud"],
                    "correctAnswer":"AWS"}
                """;
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @Test
    void retrieveSurveyAllQuestions_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(SURVEY1_QUESTIONS_URL, String.class);
        String expectedResponse = """
                [
                    {
                        "id": "Question1"
                    },
                    {
                        "id": "Question2"
                    },
                    {
                        "id": "Question3"
                    }
                ]
                """;
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    // POST Method Tests

    /*
    // addSurveyQuestion
    @Test
    void addSurveyQuestion_basicScenario() throws JSONException {
        String requestBody = """
                {
                    "description":"Your Favourite Coding Language",
                    "option":["Java","Python","C","C++"],
                    "correctAnswer":"Java"}
                """;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(SURVEY1_QUESTIONS_URL, HttpMethod.POST, httpEntity, String.class);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertTrue(responseEntity.getHeaders().get("location").get(0).contains("/surveys/Survey1/questions/"));
    }
     */

    // Add Survey Question && Delete Survey Question
    @Test
    void addSurveyQuestion_basicScenario() throws JSONException {
        String requestBody = """
                {
                    "description":"Your Favourite Coding Language",
                    "option":["Java","Python","C","C++"],
                    "correctAnswer":"Java"}
                """;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(SURVEY1_QUESTIONS_URL, HttpMethod.POST, httpEntity, String.class);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        String locationHeader = responseEntity.getHeaders().get("location").get(0);
        assertTrue(locationHeader.contains("/surveys/Survey1/questions/"));

        testRestTemplate.delete(locationHeader);
    }
}
