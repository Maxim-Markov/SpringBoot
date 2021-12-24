package com.maxmarkovdev.springboot.service.interfaces;

import com.maxmarkovdev.springboot.model.Tag;
import com.maxmarkovdev.springboot.service.interfaces.abstracts.ReadWriteService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagService extends ReadWriteService<Tag, Long> {
    boolean existsByName(String name);
    Optional<Tag> getByName(String name);

    List<Tag> getByAllNames(Collection<String> names);
}
