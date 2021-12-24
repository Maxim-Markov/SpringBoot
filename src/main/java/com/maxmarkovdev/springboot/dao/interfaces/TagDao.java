package com.maxmarkovdev.springboot.dao.interfaces;

import com.maxmarkovdev.springboot.dao.interfaces.abstracts.ReadWriteDao;
import com.maxmarkovdev.springboot.model.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagDao extends ReadWriteDao<Tag, Long> {
    boolean existsByName(String name);

    Optional<Tag> getByName(String name);

    List<Tag> getByAllNames(Collection<String> names);
}
