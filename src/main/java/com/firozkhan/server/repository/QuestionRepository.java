package com.firozkhan.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.firozkhan.server.model.Question;
import com.firozkhan.server.model.User;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    @NonNull
    Optional<Question> findById(@NonNull String id);

    List<Question> findByUser(User user);
}
