package com.maxmarkovdev.springboot.dao.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.TagDtoDao;
import com.maxmarkovdev.springboot.model.dto.TagDto;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TagDto> getTagsByLetters(String letters) {
        return em.createQuery("SELECT new com.maxmarkovdev.springboot.model.dto.TagDto(tag.id, tag.name, tag.description)" +
                        "FROM Tag tag WHERE tag.name LIKE :letters", TagDto.class)
                .setParameter("letters", MatchMode.ANYWHERE.toMatchString(letters))
                .setMaxResults(6).getResultList();
    }

    @Override
    public Map<Long, List<TagDto>> getMapTagsByQuestionIds(List<Long> questionIds){
        List<Tuple> tags = em.createQuery("SELECT t.id AS tag_id, t.name AS tag_name, t.description AS tag_description," +
                                " q.id AS question_id FROM Tag t JOIN t.questions q WHERE q.id IN :ids", Tuple.class)
                .setParameter("ids", questionIds)
                .getResultList();

        Map<Long, List<TagDto>> tagsMap = new HashMap<>();
        tags.forEach(tuple -> {
            tagsMap.computeIfAbsent(tuple.get("question_id", Long.class), id -> new ArrayList<>())
                    .add(new TagDto(tuple.get("tag_id", Long.class), tuple.get("tag_name", String.class), tuple.get("tag_description", String.class)));
        });
        return tagsMap;
    }

    @Override
    public List<TagDto> getTagDtoListByQuestionId(Long id) {
        return em.createQuery("SELECT new com.maxmarkovdev.springboot.model.dto.TagDto" +
                "(tag.id, tag.name, tag.description)" +
                "FROM Tag tag WHERE :id IN (SELECT tag_q.id FROM tag.questions tag_q)", TagDto.class).setParameter("id", id).getResultList();
    }
}

