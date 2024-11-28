package com.firozkhan.server.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.firozkhan.server.model.AnswerVote;
import com.firozkhan.server.service.AnswerVoteService;

@RestController
@RequestMapping("/api/answer-votes")
public class AnswerVoteController {

    private final AnswerVoteService answerVoteService;

    public AnswerVoteController(AnswerVoteService answerVoteService) {
        this.answerVoteService = answerVoteService;
    }

    @GetMapping("/{answerId}/count")
    public ResponseEntity<Long> getAnswerVoteCount(@PathVariable String answerId) {
        long voteCount = answerVoteService.getVoteCount(answerId);
        return ResponseEntity.ok(voteCount);
    }

    @PostMapping("/{answerId}/upvote")
    public ResponseEntity<AnswerVote> upvoteAnswer(
            @PathVariable String answerId,
            @RequestParam String userId) {
        AnswerVote answerVote = answerVoteService.upvoteAnswer(answerId, userId);
        return ResponseEntity.ok(answerVote);
    }

    @PostMapping("/{answerId}/downvote")
    public ResponseEntity<AnswerVote> downvoteAnswer(
            @PathVariable String answerId,
            @RequestParam String userId) {
        AnswerVote answerVote = answerVoteService.downvoteAnswer(answerId, userId);
        return ResponseEntity.ok(answerVote);
    }
}
