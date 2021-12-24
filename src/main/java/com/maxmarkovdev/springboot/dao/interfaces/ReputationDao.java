package com.maxmarkovdev.springboot.dao.interfaces;

import com.maxmarkovdev.springboot.dao.interfaces.abstracts.ReadWriteDao;
import com.maxmarkovdev.springboot.model.reputation.Reputation;

public interface ReputationDao extends ReadWriteDao<Reputation, Long> {
}
