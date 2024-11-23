package com.firozkhan.server.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.firozkhan.server.model.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    Optional<Question> findById(@NonNull String id);
}
