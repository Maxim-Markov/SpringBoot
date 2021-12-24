package com.maxmarkovdev.springboot.dao.interfaces;

import com.maxmarkovdev.springboot.dao.interfaces.abstracts.ReadWriteDao;
import com.maxmarkovdev.springboot.model.Question;

public interface QuestionDao extends ReadWriteDao<Question, Long> {
    Long countQuestions();
}
