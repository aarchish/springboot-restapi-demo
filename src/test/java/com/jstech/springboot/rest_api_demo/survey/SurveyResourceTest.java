package com.jstech.springboot.rest_api_demo.survey;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

// Launch Unit Test on SurveyResource.java
@WebMvcTest(controllers = SurveyResource.class)
public class SurveyResourceTest {

    // Mock -> surveyService.retrieveSurveyQuestionById(surveyId, questionId)

    // Fire a request -> "/surveys/{surveyId}/questions/{questionId}"
    // Fire a request -> "http://localhost:8080/surveys/Survey1/questions/Question1"

    private static final String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
    private static final String SURVEY1_QUESTIONS_URL = "/surveys/Survey1/questions";

    @MockitoBean
    private SurveyService surveyService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void retrieveSurveyQuestionById_basicScenario() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);

        Question question = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

        when(surveyService.retrieveSurveyQuestionById("Survey1", "Question1"))
                .thenReturn(Optional.of(question));

        String expectedResponse = """
                {
                    "id":"Question1",
                    "description":"Most Popular Cloud Platform Today",
                    "option":["AWS","Azure","Google Cloud","Oracle Cloud"],
                    "correctAnswer":"AWS"}
                """;

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse mvcResultResponse = mvcResult.getResponse();

        assertEquals(200, mvcResultResponse.getStatus());
        JSONAssert.assertEquals(expectedResponse, mvcResultResponse.getContentAsString(), false);
    }

    // Add Survey Question
    @Test
    void addSurveyQuestion_basicScenario() throws Exception {
        String requestBody = """
                {
                    "description":"Your Favourite Coding Language",
                    "option":["Java","Python","C","C++"],
                    "correctAnswer":"Java"}
                """;

        when(surveyService.addSurveyQuestion(anyString(), any())).thenReturn("SOME_ID");

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(SURVEY1_QUESTIONS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON);

        Question question = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mvcResultResponse = mvcResult.getResponse();
        String locationHeader = mvcResultResponse.getHeader("location");

        assertEquals(201, mvcResultResponse.getStatus());
        assertTrue(locationHeader.contains("/surveys/Survey1/questions/SOME_ID"));
    }

    // && Delete Survey Question


}
