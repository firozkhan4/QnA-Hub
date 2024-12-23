package com.firozkhan.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firozkhan.server.dto.QuestionDTO;
import com.firozkhan.server.dto.Response.QuestionResponseDTO;
import com.firozkhan.server.model.Question;
import com.firozkhan.server.model.User;
import com.firozkhan.server.service.DataFetcher;
import com.firozkhan.server.service.QuestionService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;
    private final DataFetcher dataFetcher;

    public QuestionController(QuestionService questionService, DataFetcher dataFetcher) {
        this.questionService = questionService;
        this.dataFetcher = dataFetcher;
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestions() {
        logger.trace("Fetching all questions");
        List<QuestionResponseDTO> questions = questionService.getAll();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable String id) {
        logger.trace("Fetching question with ID: {}", id);
        QuestionDTO question = questionService.getById(id);
        return ResponseEntity.ok(question);
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO,
            HttpServletRequest request) {
        logger.trace("Create Question API endpoint hit");
        User user = dataFetcher.getCurrentUser(request);
        QuestionDTO createdQuestion = questionService.create(questionDTO, user);
        return ResponseEntity.ok(createdQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteQuestion(@PathVariable String id) {
        logger.trace("Deleting question with ID: {}", id);
        boolean isDeleted = questionService.deleteById(id);
        return ResponseEntity.ok(isDeleted);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable String id, @RequestBody Question entity) {
        logger.trace("Updating question with ID: {}", id);
        QuestionDTO updatedQuestion = questionService.update(id, entity);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAllQuestions() {
        logger.trace("Deleting all questions");
        boolean isDeleted = questionService.deleteAllQuestion();
        return ResponseEntity.ok(isDeleted);
    }

    @GetMapping("/me")
    public ResponseEntity<List<QuestionResponseDTO>> getCurrentUserQuestion(HttpServletRequest request) {
        User user = dataFetcher.getCurrentUser(request);
        return ResponseEntity.ok(questionService.getCurrentUserQuestions(user));
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<List<QuestionResponseDTO>> getSearchQuestions(@PathVariable String search) {
        if (search.equals("all")) {
            search = ".*";
        }
        return ResponseEntity.ok(questionService.search(search));
    }
}
