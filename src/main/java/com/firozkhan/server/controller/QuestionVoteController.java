package com.firozkhan.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firozkhan.server.model.QuestionVote;
import com.firozkhan.server.model.User;
import com.firozkhan.server.service.DataFetcher;
import com.firozkhan.server.service.QuestionVoteService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/questions/{questionId}")
public class QuestionVoteController {

    private static Logger log = LoggerFactory.getLogger(QuestionVoteController.class);

    private final QuestionVoteService questionVoteService;
    private final DataFetcher dataFetcher;

    public QuestionVoteController(QuestionVoteService questionVoteService, DataFetcher dataFetcher) {
        this.questionVoteService = questionVoteService;
        this.dataFetcher = dataFetcher;
    }

    @GetMapping("/vote-count")
    public long getQuestionVoteCount(@PathVariable String questionId) {
        return questionVoteService.getVoteCount(questionId);
    }

    @PostMapping("/upvote")
    public QuestionVote upvoteQuestion(@PathVariable String questionId, HttpServletRequest request) {
        log.info("UP Vote api hit\t" + questionId);
        User user = dataFetcher.getCurrentUser(request);
        return questionVoteService.upvoteQuestion(questionId, user);
    }

    @PostMapping("/downvote")
    public QuestionVote downvoteQuestion(@PathVariable String questionId, HttpServletRequest request) {
        User user = dataFetcher.getCurrentUser(request);
        return questionVoteService.downvoteQuestion(questionId, user);
    }
}
