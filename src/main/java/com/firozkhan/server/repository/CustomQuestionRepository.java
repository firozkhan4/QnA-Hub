package com.firozkhan.server.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.firozkhan.server.dto.Response.QuestionResponseDTO;
import com.firozkhan.server.model.User;

@Repository
public interface CustomQuestionRepository {
    List<QuestionResponseDTO> findQuestionsWithVotes();

    List<QuestionResponseDTO> findByUser(User user);

    List<QuestionResponseDTO> searchQuestions(String input);
}
