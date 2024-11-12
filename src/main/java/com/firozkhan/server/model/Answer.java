package com.firozkhan.server.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "answers")
public class Answer {

    @Id
    private String id;
    private String content;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @DocumentReference(lazy = true)
    private Question question;

    @DocumentReference(lazy = true)
    private User user;

    public Answer() {
    }

    private Answer(Builder builder) {
        this.id = builder.id;
        this.content = builder.content;
        this.createdDate = builder.createdDate;
        this.lastModifiedDate = builder.lastModifiedDate;
        this.question = builder.question;
        this.user = builder.user;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Question getQuestion() {
        return question;
    }

    public User getUser() {
        return user;
    }

    public Builder toBuilder() {
        return new Builder()
                .id(this.id)
                .content(this.content)
                .createdDate(this.createdDate)
                .lastModifiedDate(this.lastModifiedDate)
                .question(this.question)
                .user(this.user);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private Question question;
        private User user;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
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

        public Builder question(Question question) {
            this.question = question;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Answer build() {
            return new Answer(this);
        }
    }
}
