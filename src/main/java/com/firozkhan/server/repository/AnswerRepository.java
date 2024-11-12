package com.firozkhan.server.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.firozkhan.server.model.Answer;
import com.firozkhan.server.model.Question;

@Repository
public interface AnswerRepository extends MongoRepository<Answer, String> {

    List<Answer> findByQuestion(Question question);

}
