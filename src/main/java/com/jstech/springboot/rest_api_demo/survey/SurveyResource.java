package com.jstech.springboot.rest_api_demo.survey;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class SurveyResource {

    private final SurveyService surveyService;
    // ("/surveys") path
    // /surveys/Survey1 & so on

    public SurveyResource(SurveyService surveyService) {
        super();
        this.surveyService = surveyService;
    }

    // GET METHODS

    @RequestMapping("/surveys")
    public List<Survey> retrieveAllSurveys() {
        List<Survey> survey = surveyService.retrieveAllSurveys();

        if (survey.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return survey;
    }

    @RequestMapping("/surveys/{surveyId}")
    public Optional<Survey> retrieveSurveyById(@PathVariable String surveyId) {
        Optional<Survey> survey = surveyService.retrieveSurveyById(surveyId);

        if (survey.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return survey;
    }

    @RequestMapping("/surveys/{surveyId}/questions")
    public List<Question> retrieveSurveyAllQuestions(@PathVariable String surveyId) {
        List<Question> questions = surveyService.retrieveSurveyAllQuestions(surveyId);

        if (questions.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return questions;
    }

    @RequestMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveSurveyQuestionById(@PathVariable String surveyId, @PathVariable String questionId) {
        Optional<Question> question = surveyService.retrieveSurveyQuestionById(surveyId, questionId);

        if (question.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return question.orElse(null);
    }

    // POST METHODS

    @RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.POST)
    public void addSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
        surveyService.addSurveyQuestion(surveyId, question);
    }
}
