package com.firozkhan.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firozkhan.server.dto.Request.AnswerRequest;
import com.firozkhan.server.dto.Response.AnswerResponseDTO;
import com.firozkhan.server.service.AnswerService;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AnswerResponseDTO> getAllAnswers() {
        log.trace("Fetching all answers");
        return answerService.getAllAnswers();
    }

    @GetMapping("/{id}")
    public AnswerResponseDTO getAnswerById(@PathVariable String id) {
        log.trace("Fetching answer with id: {}", id);
        return answerService.getAnswerById(id);
    }

    @GetMapping("/question/{questionId}")
    public List<AnswerResponseDTO> getAnswersByQuestionId(@PathVariable String questionId) {
        log.trace("Fetching answers for questionId: {}", questionId);
        return answerService.getAnswersByQuestionId(questionId);
    }

    @PostMapping
    public AnswerResponseDTO createAnswer(@RequestBody AnswerRequest answerRequest) {
        log.trace("Creating a new answer for questionId: {}, by user: {}", answerRequest.question(),
                answerRequest.user());
        return answerService.createAnswer(answerRequest.content(), answerRequest.question(),
                answerRequest.user());
    }

    @DeleteMapping("/{id}")
    public Boolean deleteAnswer(@PathVariable String id) {
        log.trace("Deleting answer with id: {}", id);
        return answerService.deleteAnswerById(id);
    }

    @PutMapping("/{id}")
    public AnswerResponseDTO updateAnswer(@PathVariable String id, @RequestBody String updateRequest) {
        log.trace("Updating answer with id: {}, new content: {}", id, updateRequest);
        return answerService.updateAnswer(id, updateRequest);
    }

}
