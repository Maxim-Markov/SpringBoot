package com.maxmarkovdev.springboot.dao.impl;

import com.maxmarkovdev.springboot.dao.impl.astracts.ReadWriteDaoImpl;
import com.maxmarkovdev.springboot.dao.interfaces.TagDao;
import com.maxmarkovdev.springboot.dao.util.SingleResultUtil;
import com.maxmarkovdev.springboot.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl extends ReadWriteDaoImpl<Tag, Long> implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean existsByName(String name) {
        long count = (long) entityManager.createQuery("SELECT COUNT(t) FROM Tag t WHERE t.name =: name").setParameter("name", name).getSingleResult();
        return count > 0;
    }

    public Optional<Tag> getByName(String name) {
        TypedQuery<Tag> query = entityManager
                .createQuery("FROM Tag t WHERE  t.name= :name", Tag.class)
                .setParameter("name", name);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

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

