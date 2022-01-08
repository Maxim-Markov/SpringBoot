package com.maxmarkovdev.springboot.service.impl;

import com.maxmarkovdev.springboot.dao.interfaces.ReputationDao;
import com.maxmarkovdev.springboot.dao.interfaces.VoteQuestionDao;
import com.maxmarkovdev.springboot.dao.interfaces.abstracts.ReadWriteDao;
import com.maxmarkovdev.springboot.model.Question;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.model.reputation.Reputation;
import com.maxmarkovdev.springboot.model.reputation.ReputationType;
import com.maxmarkovdev.springboot.model.vote.VoteQuestion;
import com.maxmarkovdev.springboot.model.vote.VoteType;
import com.maxmarkovdev.springboot.service.impl.abstracts.ReadWriteServiceImpl;
import com.maxmarkovdev.springboot.service.interfaces.VoteQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    private final VoteQuestionDao voteQuestionDao;
    private final ReputationDao reputationDao;

    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, VoteQuestionDao voteQuestionDao,
                                   ReputationDao reputationDao) {
        super(readWriteDao);
        this.voteQuestionDao = voteQuestionDao;
        this.reputationDao = reputationDao;
    }


    @Override
    @Transactional
    public boolean checkIfVoteQuestionDoesNotExist(Long questionId, Long userId) {
        return voteQuestionDao.getVoteQuestionByQuestionIdAndUserId(questionId, userId).isEmpty();
    }

    @Override
    @Transactional
    public Long voteAndGetCountVoteQuestionFotThisQuestion(Long questionId, VoteType type, User user) {

        int reputationCount = 0;
        if (type == VoteType.DOWN_VOTE) {
            reputationCount = -5;
        }
        if (type == VoteType.UP_VOTE) {
            reputationCount = 10;
        }

        Question question = voteQuestionDao.getQuestionByIdWithAuthor(questionId);
        User author = question.getUser();

        voteQuestionDao.persist(new VoteQuestion(user, question, type));
        reputationDao.persist(new Reputation(author, user, reputationCount, ReputationType.VoteQuestion, question));

        return voteQuestionDao.getCountVoteQuestionByQuestionId(questionId);
    }
}
