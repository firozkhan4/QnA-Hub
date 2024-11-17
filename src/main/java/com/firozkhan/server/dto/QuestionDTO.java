package com.firozkhan.server.dto;

import java.time.LocalDateTime;

public record QuestionDTO(
                String id,
                String title,
                String heading,
                String content,
                String user,
                String createdAt) {

}
