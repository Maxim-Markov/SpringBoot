package com.maxmarkovdev.springboot.service.impl;

import com.maxmarkovdev.springboot.dao.interfaces.ReputationDao;
import com.maxmarkovdev.springboot.model.reputation.Reputation;
import com.maxmarkovdev.springboot.service.impl.abstracts.ReadWriteServiceImpl;
import com.maxmarkovdev.springboot.service.interfaces.ReputationService;
import org.springframework.stereotype.Service;

@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    public ReputationServiceImpl(ReputationDao reputationDao) {
        super(reputationDao);
    }
}
