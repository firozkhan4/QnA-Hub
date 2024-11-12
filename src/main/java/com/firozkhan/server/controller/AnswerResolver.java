package com.firozkhan.server.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.firozkhan.server.model.Answer;
import com.firozkhan.server.service.AnswerService;

@Controller
public class AnswerResolver {

    private final AnswerService answerService;

    public AnswerResolver(AnswerService answerService) {
        this.answerService = answerService;
    }

    @QueryMapping
    public List<Answer> getAllAnswers() {
        return answerService.getAllAnswers();
    }

    @QueryMapping
    public Answer getAnswerById(@Argument String id) {
        return answerService.getAnswerById(id);
    }

    @QueryMapping
    public List<Answer> getAnswersByQuestionId(@Argument String question) {
        return answerService.getAnswersByQuestionId(question);
    }

    @MutationMapping
    public Answer createAnswer(@Argument String content, @Argument String question, @Argument String user) {
        return answerService.createAnswer(content, question, user);
    }

    @MutationMapping
    public Boolean deleteAnswer(@Argument String id) {
        return answerService.deleteAnswerById(id);
    }

    @MutationMapping
    public Answer updateAnswer(@Argument String id, @Argument String content) {
        return answerService.updateAnswer(id, content);
    }

}
