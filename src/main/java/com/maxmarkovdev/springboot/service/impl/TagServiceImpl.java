package com.maxmarkovdev.springboot.service.impl;

import com.maxmarkovdev.springboot.dao.interfaces.TagDao;
import com.maxmarkovdev.springboot.model.Tag;
import com.maxmarkovdev.springboot.service.impl.abstracts.ReadWriteServiceImpl;
import com.maxmarkovdev.springboot.service.interfaces.TagService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        super(tagDao);
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> getByAllNames(Collection<String> names) {
        return tagDao.getByAllNames(names);
    }
}
