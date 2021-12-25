package com.maxmarkovdev.springboot.dao.interfaces;

import com.maxmarkovdev.springboot.dao.interfaces.abstracts.ReadWriteDao;
import com.maxmarkovdev.springboot.model.Tag;

import java.util.Collection;
import java.util.List;

public interface TagDao extends ReadWriteDao<Tag, Long> {
    List<Tag> getByAllNames(Collection<String> names);
}
