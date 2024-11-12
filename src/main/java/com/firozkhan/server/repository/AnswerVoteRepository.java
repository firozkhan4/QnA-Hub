package com.firozkhan.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.firozkhan.server.model.AnswerVote;

@Repository
public interface AnswerVoteRepository extends MongoRepository<AnswerVote, String> {

    AnswerVote findByAnswerIdAndUserId(String answerId, String userId);

    long countByAnswerId(String answerId);
}
