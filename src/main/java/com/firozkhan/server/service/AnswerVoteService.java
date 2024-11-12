package com.firozkhan.server.service;

import org.springframework.stereotype.Service;

import com.firozkhan.server.enums.Vote;
import com.firozkhan.server.error.NotFoundException;
import com.firozkhan.server.model.Answer;
import com.firozkhan.server.model.AnswerVote;
import com.firozkhan.server.model.User;
import com.firozkhan.server.repository.AnswerRepository;
import com.firozkhan.server.repository.AnswerVoteRepository;
import com.firozkhan.server.repository.UserRepository;

@Service
public class AnswerVoteService {

    private final AnswerVoteRepository answerVoteRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public AnswerVoteService(AnswerVoteRepository answerVoteRepository, AnswerRepository answerRepository,
            UserRepository userRepository) {
        this.answerVoteRepository = answerVoteRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    public AnswerVote upvoteAnswer(String answerId, String userId) {
        return voteAnswer(answerId, userId, Vote.UPVOTE);
    }

    public AnswerVote downvoteAnswer(String answerId, String userId) {
        return voteAnswer(answerId, userId, Vote.DOWNVOTE);
    }

    public long getVoteCount(String answerId) {
        return answerVoteRepository.countByAnswerId(answerId);
    }

    private AnswerVote voteAnswer(String answerId, String userId, Vote vote) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NotFoundException("Answer not found for ID: " + answerId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found for ID: " + userId));

        AnswerVote existingVote = answerVoteRepository.findByAnswerIdAndUserId(answerId, userId);
        if (existingVote != null) {
            existingVote = existingVote.toBuilder().vote(vote).build();
            return answerVoteRepository.save(existingVote);
        } else {
            AnswerVote newVote = new AnswerVote.Builder()
                    .answer(answer)
                    .user(user)
                    .vote(vote)
                    .build();
            return answerVoteRepository.save(newVote);
        }
    }
}
