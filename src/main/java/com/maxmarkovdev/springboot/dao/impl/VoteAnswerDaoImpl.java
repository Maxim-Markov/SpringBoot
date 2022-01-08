package com.maxmarkovdev.springboot.dao.impl;

import com.maxmarkovdev.springboot.dao.impl.astracts.ReadWriteDaoImpl;
import com.maxmarkovdev.springboot.dao.interfaces.VoteAnswerDao;
import com.maxmarkovdev.springboot.dao.util.SingleResultUtil;
import com.maxmarkovdev.springboot.model.vote.VoteAnswer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long getVoteCount(Long answerId) {
        return entityManager
                .createQuery("SELECT COUNT(*) FROM VoteAnswer v WHERE v.answer.id=:answerId", Long.class)
                .setParameter("answerId", answerId).getSingleResult();
    }

    @Override
    public Optional<VoteAnswer> findByAnswerAndUser(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                        "FROM VoteAnswer v WHERE v.answer.id=:answerId AND v.user.id=:userId", VoteAnswer.class)
                .setParameter("answerId", answerId)
                .setParameter("userId", userId));
    }
}
