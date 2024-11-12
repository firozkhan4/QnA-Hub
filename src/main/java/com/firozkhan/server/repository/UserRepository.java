package com.firozkhan.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.firozkhan.server.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'$or': [ { 'username' : ?0 }, { 'email' : ?1 } ] }")
    List<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

}
