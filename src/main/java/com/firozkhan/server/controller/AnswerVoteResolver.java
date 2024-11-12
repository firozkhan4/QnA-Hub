package com.firozkhan.server.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.firozkhan.server.model.AnswerVote;
import com.firozkhan.server.service.AnswerVoteService;

@Controller
public class AnswerVoteResolver {

    private final AnswerVoteService answerVoteService;

    public AnswerVoteResolver(AnswerVoteService answerVoteService) {
        this.answerVoteService = answerVoteService;
    }

    @QueryMapping
    public long getAnswerVoteCount(@Argument String answerId) {
        return answerVoteService.getVoteCount(answerId);
    }

    @MutationMapping
    public AnswerVote upvoteAnswer(@Argument String answerId, @Argument String userId) {
        return answerVoteService.upvoteAnswer(answerId, userId);
    }

    @MutationMapping
    public AnswerVote downvoteAnswer(@Argument String answerId, @Argument String userId) {
        return answerVoteService.downvoteAnswer(answerId, userId);
    }
}
