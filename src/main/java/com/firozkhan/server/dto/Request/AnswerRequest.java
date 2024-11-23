package com.firozkhan.server.dto.Request;

public record AnswerRequest(
        String content,
        String question,
        String user) {

}
