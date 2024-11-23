package com.firozkhan.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.firozkhan.server.dto.Response.AnswerResponseDTO;
import com.firozkhan.server.error.NotFoundException;
import com.firozkhan.server.model.Answer;
import com.firozkhan.server.model.Question;
import com.firozkhan.server.model.User;
import com.firozkhan.server.repository.AnswerRepository;
import com.firozkhan.server.repository.QuestionRepository;
import com.firozkhan.server.repository.UserRepository;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository,
            UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public List<AnswerResponseDTO> getAllAnswers() {
        List<Answer> answers = answerRepository.findAll();

        return answers.stream()
                .map(this::mapToAnswerResponseDTO)
                .collect(Collectors.toList());
    }

    public AnswerResponseDTO getAnswerById(String id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Answer not found with ID: " + id));

        return mapToAnswerResponseDTO(answer);
    }

    public AnswerResponseDTO createAnswer(String content, String questionId, String userId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question not found with ID: " + questionId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        Answer answer = new Answer.Builder()
                .content(content)
                .question(question)
                .user(user)
                .build();

        answer = answerRepository.save(answer);

        return mapToAnswerResponseDTO(answer);
    }

    public boolean deleteAnswerById(String id) {
        if (!answerRepository.existsById(id)) {
            throw new NotFoundException("Answer not found with ID: " + id);
        }

        answerRepository.deleteById(id);
        return true;
    }

    public AnswerResponseDTO updateAnswer(String id, String content) {
        Answer existingAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Answer not found with ID: " + id));

        existingAnswer = existingAnswer.toBuilder()
                .content(content)
                .build();

        existingAnswer = answerRepository.save(existingAnswer);

        return mapToAnswerResponseDTO(existingAnswer);
    }

    public List<AnswerResponseDTO> getAnswersByQuestionId(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question not found by ID: " + questionId));
        List<Answer> answers = answerRepository.findByQuestion(question);

        return answers.stream()
                .map(this::mapToAnswerResponseDTO)
                .collect(Collectors.toList());
    }

    private AnswerResponseDTO mapToAnswerResponseDTO(Answer answer) {
        return new AnswerResponseDTO(
                answer.getId(),
                answer.getUser().getId(),
                answer.getUser().getUsername(),
                answer.getQuestion().getId(),
                answer.getContent(),
                answer.getCreatedDate().toString(),
                answer.getLastModifiedDate().toString());
    }
}
