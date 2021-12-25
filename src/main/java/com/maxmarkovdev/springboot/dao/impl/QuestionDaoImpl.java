package com.maxmarkovdev.springboot.dao.impl;

import com.maxmarkovdev.springboot.dao.impl.astracts.ReadWriteDaoImpl;
import com.maxmarkovdev.springboot.dao.interfaces.QuestionDao;
import com.maxmarkovdev.springboot.model.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long countQuestions() {
        return (Long) (long) entityManager
                .createQuery("SELECT COUNT(q) FROM Question q WHERE q.isDeleted= :del")
                .setParameter("del", false)
                .getSingleResult();
    }
}
