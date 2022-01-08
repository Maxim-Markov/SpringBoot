package com.maxmarkovdev.springboot.dao.interfaces;

import com.maxmarkovdev.springboot.dao.interfaces.abstracts.ReadWriteDao;
import com.maxmarkovdev.springboot.model.Question;
import com.maxmarkovdev.springboot.model.vote.VoteQuestion;

import java.util.Optional;

public interface VoteQuestionDao extends ReadWriteDao<VoteQuestion, Long> {
    Long getCountVoteQuestionByQuestionId(Long questionId);
    Question getQuestionByIdWithAuthor(Long questionId);
    Optional<VoteQuestion> getVoteQuestionByQuestionIdAndUserId(Long questionId, Long userId);
}
