package com.maxmarkovdev.springboot.dao.impl;

import com.maxmarkovdev.springboot.dao.impl.astracts.ReadWriteDaoImpl;
import com.maxmarkovdev.springboot.dao.interfaces.VoteQuestionDao;
import com.maxmarkovdev.springboot.dao.util.SingleResultUtil;
import com.maxmarkovdev.springboot.model.Question;
import com.maxmarkovdev.springboot.model.vote.VoteQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteQuestionDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long getCountVoteQuestionByQuestionId(Long questionId) {
        return em.createQuery(
                        "SELECT COUNT(v.id) FROM VoteQuestion v " +
                                "WHERE v.question.id=:ID", Long.class)
                .setParameter("ID", questionId).getSingleResult();
    }

    @Override
    public Question getQuestionByIdWithAuthor(Long questionId) {
        return em.createQuery("SELECT q FROM Question q " +
                        "JOIN User u ON q.user.id = u.id " +
                        "WHERE q.id=:ID", Question.class)
                .setParameter("ID", questionId)
                .getSingleResult();
    }

    @Override
    public Optional<VoteQuestion> getVoteQuestionByQuestionIdAndUserId(Long questionId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(em.createQuery(
                        "SELECT v FROM VoteQuestion v JOIN Question q " +
                                "ON v.question.id=q.id WHERE q.id=:qID AND v.user.id=:vID", VoteQuestion.class)
                .setParameter("qID", questionId)
                .setParameter("vID", userId));
    }
}
