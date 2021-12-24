package com.maxmarkovdev.springboot.service.impl;

import com.maxmarkovdev.springboot.dao.interfaces.TagDao;
import com.maxmarkovdev.springboot.model.Tag;
import com.maxmarkovdev.springboot.service.impl.abstracts.ReadWriteServiceImpl;
import com.maxmarkovdev.springboot.service.interfaces.TagService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        super(tagDao);
        this.tagDao = tagDao;
    }

    @Override
    public boolean existsByName(String name) {
        return tagDao.existsByName(name);
    }

    @Override
    public Optional<Tag> getByName(String name) {
        return tagDao.getByName(name);
    }

    @Override
    public List<Tag> getByAllNames(Collection<String> names) {
        return tagDao.getByAllNames(names);
    }
}
