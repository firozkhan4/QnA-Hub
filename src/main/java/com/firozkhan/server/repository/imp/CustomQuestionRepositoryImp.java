package com.firozkhan.server.repository.imp;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.firozkhan.server.dto.Response.QuestionResponseDTO;
import com.firozkhan.server.model.User;
import com.firozkhan.server.repository.CustomQuestionRepository;

@Repository
public class CustomQuestionRepositoryImp implements CustomQuestionRepository {

    private String queryForFindVotes = """
                {
                    $addFields: {
                        upvoteCount: {
                            $size: {
                                $filter: {
                                    input: "$votes",
                                    as: "vote",
                                    cond: { $eq: ["$$vote.vote", "UPVOTE"] }
                                }
                            }
                        },
                        downvoteCount: {
                            $size: {
                                $filter: {
                                    input: "$votes",
                                    as: "vote",
                                    cond: { $eq: ["$$vote.vote", "DOWNVOTE"] }
                                }
                            }
                        }
                    }
                }
            """;

    public final MongoTemplate mongoTemplate;

    public CustomQuestionRepositoryImp(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<QuestionResponseDTO> findQuestionsWithVotes() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup(
                        "question_votes",
                        "_id",
                        "question",
                        "votes"),
                Aggregation.stage(queryForFindVotes),
                Aggregation.project("title", "heading", "content", "question", "user", "upvoteCount",
                        "downvoteCount"));

        return mongoTemplate.aggregate(aggregation, "questions", QuestionResponseDTO.class)
                .getMappedResults();
    }

    @Override
    public List<QuestionResponseDTO> findByUser(User user) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("user").is(new ObjectId(user.getId()))),
                Aggregation.lookup(
                        "question_votes",
                        "_id",
                        "question",
                        "votes"),
                Aggregation.stage(queryForFindVotes),
                Aggregation.project("title", "heading", "content", "question", "user", "upvoteCount",
                        "downvoteCount"));

        return mongoTemplate.aggregate(aggregation, "questions", QuestionResponseDTO.class)
                .getMappedResults();
    }

    @Override
    public List<QuestionResponseDTO> searchQuestions(String input) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("title").regex(input)),
                Aggregation.lookup(
                        "question_votes",
                        "_id",
                        "question",
                        "votes"),
                Aggregation.stage(queryForFindVotes),
                Aggregation.project("title", "heading", "content", "question", "user", "upvoteCount", "downvoteCount"));

        return mongoTemplate.aggregate(aggregation, "questions", QuestionResponseDTO.class).getMappedResults();
    }

}
