package com.maxmarkovdev.springboot.dao.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.PageDtoDao;
import com.maxmarkovdev.springboot.model.dto.QuestionDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository(value = "QuestionByDate")
public class QuestionDateDaoImpl implements PageDtoDao<QuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<QuestionDto> getItems(Map<Object, Object> param) {

        int curPageNumber = (int) param.get("currentPageNumber");
        int itemsOnPage = (int) param.get("itemsOnPage");
        List<Long> ignoredIds = (List<Long>) param.get("ignoredTags");
        List<Long> trackedIds = (List<Long>) param.get("trackedTags");
        String additionalHql = trackedIds==null?"1=1 OR ":"";
        if (ignoredIds == null) {
            ignoredIds = new ArrayList<>();
            ignoredIds.add(-1L);
        }
        return entityManager.createQuery(
                        "SELECT new com.maxmarkovdev.springboot.model.dto.QuestionDto" +
                                "(q.id, " +
                                "q.title, " +
                                "q.user.id, " +
                                "q.user.name, " +
                                "q.user.imageLink, " +
                                "q.description, " +
                                "SUM(0), " +
                                "(SELECT COUNT(answer.id) FROM Answer answer WHERE answer.user.id = q.user.id), " +
                                "(SELECT COUNT (up.vote) FROM VoteQuestion up WHERE up.vote = 'UP_VOTE' AND up.user.id = q.user.id) - " +
                                "(SELECT COUNT(down.vote) FROM VoteQuestion down WHERE down.vote = 'DOWN_VOTE' AND down.user.id = q.user.id)," +
                                "(SELECT SUM (r.count) FROM Reputation r WHERE q.user.id = r.author.id), " +
                                "q.persistDateTime, " +
                                "q.lastUpdateDateTime)" +
                                "FROM Question q " +
                                "JOIN q.tags tgs " +
                                "WHERE q.id IN (SELECT q.id From Question q JOIN q.tags tgs WHERE " + additionalHql + " tgs.id IN :tracked)" +
                                "AND q.id NOT IN (SELECT q.id From Question q JOIN q.tags tgs WHERE tgs.id IN :ignored)" +
                                "GROUP BY q.id, q.user.name, q.user.imageLink ORDER BY q.persistDateTime DESC ",
                                QuestionDto.class)
                        .setParameter("ignored", ignoredIds)
                        .setParameter("tracked", trackedIds)
                        .setFirstResult((curPageNumber - 1) * itemsOnPage).setMaxResults(itemsOnPage)
                        .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public long getTotalResultCount(Map<Object, Object> param) {
        List<Long> ignoredIds = (List<Long>) param.get("ignoredTags");
        List<Long> trackedIds = (List<Long>) param.get("trackedTags");
        String additionalHql = trackedIds==null?"1=1 OR ":"";
        if (ignoredIds == null) {
            ignoredIds = new ArrayList<>();
            ignoredIds.add(-1L);
        }
        return (Long) entityManager.createQuery("SELECT COUNT(DISTINCT q.id) FROM Question q JOIN q.tags tgs" +
                        " WHERE q.id IN (SELECT q.id From Question q JOIN q.tags tgs WHERE " + additionalHql + " tgs.id IN :tracked)" +
                        " AND q.id NOT IN (SELECT q.id From Question q JOIN q.tags tgs WHERE tgs.id IN :ignored)")
                .setParameter("tracked", trackedIds)
                .setParameter("ignored", ignoredIds)
                .getSingleResult();
    }
}
