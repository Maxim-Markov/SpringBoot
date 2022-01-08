package com.maxmarkovdev.springboot.service.interfaces;

import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.model.vote.VoteQuestion;
import com.maxmarkovdev.springboot.model.vote.VoteType;
import com.maxmarkovdev.springboot.service.interfaces.abstracts.ReadWriteService;

public interface VoteQuestionService extends ReadWriteService<VoteQuestion, Long> {
    boolean checkIfVoteQuestionDoesNotExist(Long questionId, Long userId);
    Long voteAndGetCountVoteQuestionFotThisQuestion(Long questionId, VoteType type, User user);
}
