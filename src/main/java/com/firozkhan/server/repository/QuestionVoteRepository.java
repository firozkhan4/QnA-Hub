package com.firozkhan.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.firozkhan.server.model.QuestionVote;

@Repository
public interface QuestionVoteRepository extends MongoRepository<QuestionVote, String> {

    QuestionVote findByQuestionIdAndUserId(String questionId, String userId);

    long countByQuestionId(String questionId);
}
