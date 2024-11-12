package com.firozkhan.server.service;

import org.springframework.stereotype.Service;

import com.firozkhan.server.enums.Vote;
import com.firozkhan.server.error.NotFoundException;
import com.firozkhan.server.model.Question;
import com.firozkhan.server.model.QuestionVote;
import com.firozkhan.server.model.User;
import com.firozkhan.server.repository.QuestionRepository;
import com.firozkhan.server.repository.QuestionVoteRepository;
import com.firozkhan.server.repository.UserRepository;

@Service
public class QuestionVoteService {

    private final QuestionVoteRepository questionVoteRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QuestionVoteService(QuestionVoteRepository questionVoteRepository, QuestionRepository questionRepository,
            UserRepository userRepository) {
        this.questionVoteRepository = questionVoteRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public QuestionVote upvoteQuestion(String questionId, String userId) {
        return voteQuestion(questionId, userId, Vote.UPVOTE);
    }

    public QuestionVote downvoteQuestion(String questionId, String userId) {
        return voteQuestion(questionId, userId, Vote.DOWNVOTE);
    }

    public long getVoteCount(String questionId) {
        return questionVoteRepository.countByQuestionId(questionId);
    }

    private QuestionVote voteQuestion(String questionId, String userId, Vote vote) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question not found for ID: " + questionId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found for ID: " + userId));

        QuestionVote existingVote = questionVoteRepository.findByQuestionIdAndUserId(questionId, userId);

        if (existingVote != null) {
            existingVote = existingVote.toBuilder().vote(vote).build();
            return questionVoteRepository.save(existingVote);
        } else {
            QuestionVote newVote = new QuestionVote.Builder()
                    .question(question)
                    .user(user)
                    .vote(vote)
                    .build();
            return questionVoteRepository.save(newVote);
        }
    }
}
