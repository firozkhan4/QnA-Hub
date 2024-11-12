package com.firozkhan.server.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.firozkhan.server.model.QuestionVote;
import com.firozkhan.server.service.QuestionVoteService;

@Controller
public class QuestionVoteResolver {

    private final QuestionVoteService questionVoteService;

    public QuestionVoteResolver(QuestionVoteService questionVoteService) {
        this.questionVoteService = questionVoteService;
    }

    @QueryMapping
    public long getQuestionVoteCount(@Argument String questionId) {
        return questionVoteService.getVoteCount(questionId);
    }

    @MutationMapping
    public QuestionVote upvoteQuestion(@Argument String questionId, @Argument String userId) {
        return questionVoteService.upvoteQuestion(questionId, userId);
    }

    @MutationMapping
    public QuestionVote downvoteQuestion(@Argument String questionId, @Argument String userId) {
        return questionVoteService.downvoteQuestion(questionId, userId);
    }
}
