package com.maxmarkovdev.springboot.service.interfaces;

import com.maxmarkovdev.springboot.model.Tag;
import com.maxmarkovdev.springboot.service.interfaces.abstracts.ReadWriteService;

import java.util.Collection;
import java.util.List;

public interface TagService extends ReadWriteService<Tag, Long> {

    List<Tag> getByAllNames(Collection<String> names);
}
