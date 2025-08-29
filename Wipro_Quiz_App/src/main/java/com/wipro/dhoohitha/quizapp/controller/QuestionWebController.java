package com.wipro.dhoohitha.quizapp.controller;

import com.wipro.dhoohitha.quizapp.entity.Question;
import com.wipro.dhoohitha.quizapp.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
public class QuestionWebController {

    private final QuestionService questionService;

    public QuestionWebController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // Show all questions + form
    @GetMapping
    public String listQuestions(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        model.addAttribute("question", new Question()); // for form
        return "questions"; // -> renders templates/questions.html
    }

    // Save new question
    @PostMapping
    public String saveQuestion(@ModelAttribute("question") Question question) {
        questionService.saveQuestion(question);
        return "redirect:/questions";
    }

    // Delete question
    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/questions";
    }
}
