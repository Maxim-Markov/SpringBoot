package com.maxmarkovdev.springboot.service.impl;

import com.maxmarkovdev.springboot.dao.interfaces.AnswerDao;
import com.maxmarkovdev.springboot.dao.interfaces.ReputationDao;
import com.maxmarkovdev.springboot.dao.interfaces.VoteAnswerDao;
import com.maxmarkovdev.springboot.exception.AnswerException;
import com.maxmarkovdev.springboot.model.Answer;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.model.reputation.Reputation;
import com.maxmarkovdev.springboot.model.reputation.ReputationType;
import com.maxmarkovdev.springboot.model.vote.VoteAnswer;
import com.maxmarkovdev.springboot.model.vote.VoteType;
import com.maxmarkovdev.springboot.service.impl.abstracts.ReadWriteServiceImpl;
import com.maxmarkovdev.springboot.service.interfaces.VoteAnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.maxmarkovdev.springboot.model.vote.VoteType.UP_VOTE;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    private final AnswerDao answerDao;
    private final VoteAnswerDao voteAnswerDao;
    private final ReputationDao reputationDao;

    public VoteAnswerServiceImpl(AnswerDao answerDao, VoteAnswerDao voteAnswerDao, ReputationDao reputationDao) {
        super(voteAnswerDao);
        this.answerDao = answerDao;
        this.voteAnswerDao = voteAnswerDao;
        this.reputationDao = reputationDao;
    }

    @Override
    public boolean isUserNonVoted(Long answerId, Long userId) {
        return voteAnswerDao.findByAnswerAndUser(answerId, userId).isEmpty();
    }

    @Override
    @Transactional
    public Long vote(Long answerId, User user, VoteType voteType) {
        Optional<Answer> answerOpt = answerDao.getById(answerId);

        if (answerOpt.isPresent()) {
            Answer answer = answerOpt.get();

            Reputation rep = new Reputation();
            rep.setAuthor(answer.getUser());
            rep.setSender(user);
            rep.setCount(getReputationCount(voteType));
            rep.setAnswer(answer);
            rep.setType(ReputationType.VoteAnswer);

            voteAnswerDao.persist(new VoteAnswer(user, answer, voteType));
            reputationDao.persist(rep);

            return voteAnswerDao.getVoteCount(answer.getId());
        }
        return -1L;
    }

    private Integer getReputationCount(VoteType voteType) {
        int voteDownReputation = -5;
        int voteUpReputation = 10;
        return voteType == UP_VOTE ? voteUpReputation : voteDownReputation;
    }
}
