package com.maxmarkovdev.springboot.service.interfaces;

import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.model.vote.VoteAnswer;
import com.maxmarkovdev.springboot.model.vote.VoteType;
import com.maxmarkovdev.springboot.service.interfaces.abstracts.ReadWriteService;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {
    Long vote(Long answerId, User user, VoteType voteType);
    boolean isUserNonVoted(Long answerId, Long userId);
}
