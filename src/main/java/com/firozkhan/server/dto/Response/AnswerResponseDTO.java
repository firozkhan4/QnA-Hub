package com.firozkhan.server.dto.Response;

public record AnswerResponseDTO(
                String id,
                String user,
                String username,
                String question,
                String content,
                String createAt,
                String updateAt) {

}
