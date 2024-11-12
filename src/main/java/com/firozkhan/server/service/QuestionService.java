package com.firozkhan.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.firozkhan.server.error.NotFoundException;
import com.firozkhan.server.model.Question;
import com.firozkhan.server.model.User;
import com.firozkhan.server.repository.QuestionRepository;
import com.firozkhan.server.repository.UserRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    public Question getById(String id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found by id: " + id));
    }

    public Question create(String title, String content, String userId) {
        User existUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User name not found by id: " + userId));

        Question question = new Question.Builder()
                .title(title)
                .content(content)
                .user(existUser)
                .build();

        return questionRepository.save(question);
    }

    public Boolean deleteById(String id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }

        throw new NotFoundException("Question not found by id: " + id);
    }

    public Question update(String id, String title, String content) {

        Question existQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found by id: " + id));

        existQuestion = existQuestion.toBuilder().title(title).content(content).build();

        return questionRepository.save(existQuestion);

    }

    public Boolean deleteAllQuestion() {
        questionRepository.deleteAll();
        return questionRepository.count() == 0;
    }
}
