package com.firozkhan.server.dto;

public record QuestionDTO(
        String id,
        String title,
        String heading,
        String content,
        String user,
        String createdAt) {

}
