package com.firozkhan.server.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.firozkhan.server.enums.Vote;

@Document(collection = "answer_votes")
public class AnswerVote {

    @Id
    private String id;

    @DocumentReference
    private User user;

    @DocumentReference
    private Answer answer;

    private Vote vote;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public AnswerVote() {
    }

    private AnswerVote(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.answer = builder.answer;
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

    public Answer getAnswer() {
        return answer;
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
        private Answer answer;
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

        public Builder answer(Answer answer) {
            this.answer = answer;
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

        public AnswerVote build() {
            return new AnswerVote(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .id(this.id)
                .user(this.user)
                .answer(this.answer)
                .vote(this.vote)
                .createdDate(this.createdDate)
                .lastModifiedDate(this.lastModifiedDate);
    }
}
