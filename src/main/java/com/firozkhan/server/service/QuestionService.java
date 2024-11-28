package com.firozkhan.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.firozkhan.server.dto.QuestionDTO;
import com.firozkhan.server.dto.Response.QuestionResponseDTO;
import com.firozkhan.server.error.NotFoundException;
import com.firozkhan.server.model.Question;
import com.firozkhan.server.model.User;
import com.firozkhan.server.repository.QuestionRepository;
import com.firozkhan.server.repository.imp.CustomQuestionRepositoryImp;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CustomQuestionRepositoryImp customQuestionRepositoryImp;

    public QuestionService(QuestionRepository questionRepository,
            CustomQuestionRepositoryImp customQuestionRepositoryImp) {
        this.questionRepository = questionRepository;
        this.customQuestionRepositoryImp = customQuestionRepositoryImp;
    }

    public List<QuestionResponseDTO> getAll() {

        List<QuestionResponseDTO> questions = customQuestionRepositoryImp.findQuestionsWithVotes();

        if (questions.isEmpty()) {
            throw new NotFoundException("No questions found.");
        }

        return questions;
    }

    public QuestionDTO getById(String id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found by id: " + id));

        return mapToQuestionDTO(question);
    }

    public QuestionDTO create(QuestionDTO questionDTO, User user) {

        Question question = new Question.Builder()
                .title(questionDTO.title())
                .content(questionDTO.content())
                .heading(questionDTO.heading())
                .user(user)
                .build();

        Question savedQuestion = questionRepository.save(question);
        return mapToQuestionDTO(savedQuestion);
    }

    public QuestionDTO update(String id, Question entity) {

        Question existQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found by id: " + id));

        existQuestion = existQuestion.toBuilder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .heading(entity.getHeading())
                .build();

        Question updatedQuestion = questionRepository.save(existQuestion);
        return mapToQuestionDTO(updatedQuestion);
    }

    public Boolean deleteById(String id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        throw new NotFoundException("Question not found by id: " + id);
    }

    public Boolean deleteAllQuestion() {
        questionRepository.deleteAll();
        return questionRepository.count() == 0;
    }

    public List<QuestionResponseDTO> getCurrentUserQuestions(User user) {
        return customQuestionRepositoryImp.findByUser(user);
    }

    public List<QuestionResponseDTO> search(String input) {
        return customQuestionRepositoryImp.searchQuestions(input);
    }

    private QuestionDTO mapToQuestionDTO(Question question) {
        return new QuestionDTO(
                question.getId(),
                question.getTitle(),
                question.getHeading(),
                question.getContent(),
                question.getUser().getId(),
                question.getCreatedDate().toString());
    }
}
