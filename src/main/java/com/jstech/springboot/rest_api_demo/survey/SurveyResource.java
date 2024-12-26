package com.jstech.springboot.rest_api_demo.survey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Object> addSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
        String questionId = surveyService.addSurveyQuestion(surveyId, question);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    // DELETE METHODS

    @RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSurveyQuestionById(@PathVariable String surveyId, @PathVariable String questionId) {
        boolean removed = surveyService.deleteSurveyQuestionById(surveyId, questionId);

        if (!removed) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.noContent().build();
    }

    // PUT or UPDATE METHODS

    @RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSurveyQuestionById(
            @PathVariable String surveyId, @PathVariable String questionId, @RequestBody Question question) {
        String updatedQuestion = surveyService.updateSurveyQuestionById(surveyId, questionId, question);

        if (updatedQuestion == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }
}
