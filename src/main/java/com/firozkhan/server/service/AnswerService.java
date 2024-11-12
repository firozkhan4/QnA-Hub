package com.firozkhan.server.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Answer getAnswerById(String id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Answer not found with ID: " + id));
    }

    @Transactional
    public Answer createAnswer(String content, String questionId, String userId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question not found with ID: " + questionId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        Answer answer = new Answer.Builder()
                .content(content)
                .question(question)
                .user(user)
                .build();

        return answerRepository.save(answer);
    }

    @Transactional
    public boolean deleteAnswerById(String id) {
        if (!answerRepository.existsById(id)) {
            throw new NotFoundException("Answer not found with ID: " + id);
        }

        answerRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Answer updateAnswer(String id, String content) {
        Answer existingAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Answer not found with ID: " + id));

        existingAnswer = existingAnswer.toBuilder()
                .content(content)
                .build();

        return answerRepository.save(existingAnswer);
    }

    public List<Answer> getAnswersByQuestionId(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question not found by ID: " + questionId));
        return answerRepository.findByQuestion(question);
    }
}
