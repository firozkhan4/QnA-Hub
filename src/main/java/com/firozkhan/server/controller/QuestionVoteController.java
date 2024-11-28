package com.firozkhan.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firozkhan.server.dto.Request.VoteRequestDTO;
import com.firozkhan.server.model.QuestionVote;
import com.firozkhan.server.model.User;
import com.firozkhan.server.service.DataFetcher;
import com.firozkhan.server.service.QuestionVoteService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/question-vote")
public class QuestionVoteController {

    private final QuestionVoteService questionVoteService;
    private final DataFetcher dataFetcher;

    public QuestionVoteController(QuestionVoteService questionVoteService, DataFetcher dataFetcher) {
        this.questionVoteService = questionVoteService;
        this.dataFetcher = dataFetcher;
    }

    @GetMapping("/count")
    public long getQuestionVoteCount(@RequestBody String questionId) {
        return questionVoteService.getVoteCount(questionId);
    }

    @PostMapping("/upvote")
    public QuestionVote upvoteQuestion(@RequestBody VoteRequestDTO vote, HttpServletRequest request) {
        User user = dataFetcher.getCurrentUser(request);
        return questionVoteService.upvoteQuestion(vote.id(), user);
    }

    @PostMapping("/downvote")
    public QuestionVote downvoteQuestion(@RequestBody VoteRequestDTO vote, HttpServletRequest request) {
        User user = dataFetcher.getCurrentUser(request);
        return questionVoteService.downvoteQuestion(vote.id(), user);
    }
}
