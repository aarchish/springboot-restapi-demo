package com.jstech.springboot.rest_api_demo.survey;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class SurveyService {

    private static final List<Survey> surveys = new ArrayList<>();

    static {
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        surveys.add(survey);
    }

    // GET or Retrieve Existing Data

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public Optional<Survey> retrieveSurveyById(String surveyId) {
        Predicate<? super Survey> predicate = survey -> survey.getId().equals(surveyId);
        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();

        return optionalSurvey;
    }

    public List<Question> retrieveSurveyAllQuestions(String surveyId) {
        Optional<Survey> survey = retrieveSurveyById(surveyId);
        if (survey.isEmpty()) return null;

        return survey.get().getQuestions();
    }

    public Optional<Question> retrieveSurveyQuestionById(String surveyId, String questionId) {
        List<Question> questions = retrieveSurveyAllQuestions(surveyId);
        if (questions.isEmpty()) return null;

        Predicate<? super Question> predicate = question -> question.getId().equals(questionId);
        Optional<Question> question = questions.stream().filter(predicate).findFirst();

        return question;
    }

    // POST or Add new Data

    public void addSurveyQuestion(String surveyId, Question question) {
        List<Question> questions = retrieveSurveyAllQuestions(surveyId);
        questions.add(question);
    }
}