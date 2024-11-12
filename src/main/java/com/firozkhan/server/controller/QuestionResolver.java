package com.firozkhan.server.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.firozkhan.server.model.Question;
import com.firozkhan.server.service.QuestionService;

@Controller
public class QuestionResolver {

    private final QuestionService questionService;

    public QuestionResolver(QuestionService questionService) {
        this.questionService = questionService;
    }

    @QueryMapping
    public List<Question> getAllQuestion() {
        return questionService.getAll();
    }

    @QueryMapping
    public Question getQuestionById(@Argument String id) {
        return questionService.getById(id);
    }

    @MutationMapping
    public Question createQuestion(@Argument String title, @Argument String content, @Argument String user) {
        return questionService.create(title, content, user);
    }

    @MutationMapping
    public Boolean deleteQuestion(@Argument String id) {
        return questionService.deleteById(id);
    }

    @MutationMapping
    public Question updateQuestion(@Argument String id, @Argument String title, @Argument String content) {
        return questionService.update(id, title, content);
    }

    @MutationMapping
    public Boolean deleteAllQuestion() {
        return questionService.deleteAllQuestion();
    }

}
