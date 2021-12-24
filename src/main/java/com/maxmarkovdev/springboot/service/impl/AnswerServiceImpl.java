package com.maxmarkovdev.springboot.service.impl;

import com.maxmarkovdev.springboot.dao.interfaces.AnswerDao;
import com.maxmarkovdev.springboot.model.Answer;
import com.maxmarkovdev.springboot.service.impl.abstracts.ReadWriteServiceImpl;
import com.maxmarkovdev.springboot.service.interfaces.AnswerService;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {

    public AnswerServiceImpl(AnswerDao answerDao) {
        super(answerDao);
    }
}
