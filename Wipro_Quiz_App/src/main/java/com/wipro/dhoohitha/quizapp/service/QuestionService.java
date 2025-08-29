package com.wipro.dhoohitha.quizapp.service;

import java.util.List;

import com.wipro.dhoohitha.quizapp.entity.Question;

public interface QuestionService {
    Question saveQuestion(Question question);
    List<Question> getAllQuestions();
    Question getQuestionById(Long id);
    void deleteQuestion(Long id);
}
