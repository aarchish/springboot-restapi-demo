package com.jstech.springboot.rest_api_demo.survey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// Launch Unit Test on SurveyResource.java
@WebMvcTest(controllers = SurveyResource.class)
public class SurveyResourceTest {

    // Mock -> surveyService.retrieveSurveyQuestionById(surveyId, questionId)

    // Fire a request -> "/surveys/{surveyId}/questions/{questionId}"
    // Fire a request -> "http://localhost:8080/surveys/Survey1/questions/Question1"

    private static final String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";
    private static final String SURVEY1_QUESTIONS_URL = "/surveys/Survey1/questions";

    @MockitoBean
    private SurveyResource surveyResource;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void retrieveSurveyQuestionById_basicScenario() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(mvcResult.getResponse().getStatus());
        System.out.println(mvcResult.getResponse().getContentAsString());
        /*
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
        */
    }

}
