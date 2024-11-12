package com.firozkhan.server.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.firozkhan.server.enums.Vote;

@Document(collection = "question_votes")
public class QuestionVote {

    @Id
    private String id;

    @DocumentReference
    private User user;

    @DocumentReference
    private Question question;

    private Vote vote;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public QuestionVote() {
    }

    private QuestionVote(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.question = builder.question;
        this.vote = builder.vote;
        this.createdDate = builder.createdDate;
        this.lastModifiedDate = builder.lastModifiedDate;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Question getQuestion() {
        return question;
    }

    public Vote getVote() {
        return vote;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public static class Builder {
        private String id;
        private User user;
        private Question question;
        private Vote vote;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder question(Question question) {
            this.question = question;
            return this;
        }

        public Builder vote(Vote vote) {
            this.vote = vote;
            return this;
        }

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder lastModifiedDate(LocalDateTime lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public QuestionVote build() {
            return new QuestionVote(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .id(this.id)
                .user(this.user)
                .question(this.question)
                .vote(this.vote)
                .createdDate(this.createdDate)
                .lastModifiedDate(this.lastModifiedDate);
    }
}
