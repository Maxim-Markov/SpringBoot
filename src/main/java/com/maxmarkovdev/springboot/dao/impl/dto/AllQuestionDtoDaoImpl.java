package com.maxmarkovdev.springboot.dao.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.PageDtoDao;
import com.maxmarkovdev.springboot.model.dto.QuestionDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository(value = "AllQuestions")
public class AllQuestionDtoDaoImpl implements PageDtoDao<QuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<QuestionDto> getItems(Map<Object, Object> param) {
        int curPageNumber = (int) param.get("currentPageNumber");
        int itemsOnPage = (int) param.get("itemsOnPage");
        List<Long> trackedIds = ((List<Long>) param.get("trackedIds"));
        List<Long> ignoredIds = ((List<Long>) param.get("ignoredIds"));
        String additionalHql = trackedIds==null?"1=1 OR ":"";
        if (ignoredIds == null) {
            ignoredIds = new ArrayList<>();
            ignoredIds.add(-1L);
        }

        return entityManager.createQuery(
                        "SELECT new com.maxmarkovdev.springboot.model.dto.QuestionDto(q.id, q.title, q.user.id," +
                                " q.user.name, q.user.imageLink, SUM(r.count)/(COUNT(r.id) / NULLIF(COUNT(DISTINCT r.id),0)), q.description, q.persistDateTime," +
                                " q.lastUpdateDateTime, SUM(0), COUNT(DISTINCT answer.id)," +
                                "(SELECT COUNT(up.vote) FROM VoteQuestion up WHERE up.vote = 'UP_VOTE' AND up.question.id = q.id) - " +
                                "(SELECT COUNT(down.vote) fROM VoteQuestion down WHERE down.vote = 'DOWN_VOTE' AND down.question.id = q.id))" +
                                " FROM Question q JOIN q.tags t LEFT JOIN Answer answer ON q.id = answer.question.id" +
                                " LEFT JOIN Reputation r ON q.user.id = r.author.id" +
                                " WHERE q.id IN (SELECT q.id FROM Question q JOIN q.tags t WHERE " + additionalHql + " t.id IN :trackedIds)" +
                                " AND q.id NOT IN (SELECT q.id FROM Question q JOIN q.tags t WHERE t.id IN :ignoredIds)" +
                                " GROUP BY q.id, q.user.name,q.user.imageLink ORDER BY q.id", QuestionDto.class)
                .setParameter("trackedIds", trackedIds)
                .setParameter("ignoredIds", ignoredIds)
                .setFirstResult((curPageNumber - 1) * itemsOnPage).setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public long getTotalResultCount(Map<Object, Object> param) {
        List<Long> trackedIds = ((List<Long>) param.get("trackedIds"));
        List<Long> ignoredIds = ((List<Long>) param.get("ignoredIds"));
        String additionalHql = trackedIds==null?"1=1 OR ":"";
        if (ignoredIds == null) {
            ignoredIds = new ArrayList<>();
            ignoredIds.add(-1L);
        }
        return (Long) entityManager.createQuery("SELECT COUNT(DISTINCT q.id) FROM Question q JOIN q.tags t" +
                        " WHERE q.id IN (SELECT q.id FROM Question q JOIN q.tags t WHERE " + additionalHql + " t.id IN :trackedIds)" +
                        " AND q.id NOT IN (SELECT q.id FROM Question q JOIN q.tags t WHERE t.id IN :ignoredIds)")
                .setParameter("trackedIds", trackedIds)
                .setParameter("ignoredIds", ignoredIds)
                .getSingleResult();
    }
}

