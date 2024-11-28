package com.firozkhan.server.dto.Response;

import java.util.List;

import com.firozkhan.server.model.QuestionVote;

public record QuestionResponseDTO(
                String id,
                String title,
                String heading,
                String content,
                String user,
                Long upvoteCount,
                Long downvoteCount,
                String createdDate,
                String lastModifiedDate) {

}
