package com.firozkhan.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.firozkhan.server.model.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

}
