package com.maxmarkovdev.springboot.service.interfaces;

import com.maxmarkovdev.springboot.model.reputation.Reputation;
import com.maxmarkovdev.springboot.service.interfaces.abstracts.ReadWriteService;

public interface ReputationService extends ReadWriteService<Reputation, Long> {
}
