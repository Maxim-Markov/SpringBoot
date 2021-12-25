package com.maxmarkovdev.springboot.dao.impl;

import com.maxmarkovdev.springboot.dao.impl.astracts.ReadWriteDaoImpl;
import com.maxmarkovdev.springboot.dao.interfaces.TagDao;
import com.maxmarkovdev.springboot.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class TagDaoImpl extends ReadWriteDaoImpl<Tag, Long> implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> getByAllNames(Collection<String> names) {
        if (names != null && names.size() > 0) {
            return entityManager
                    .createQuery("FROM Tag t WHERE  t.name IN :names", Tag.class)
                    .setParameter("names", names).getResultList();
        }
        return new ArrayList<>();
    }
}

