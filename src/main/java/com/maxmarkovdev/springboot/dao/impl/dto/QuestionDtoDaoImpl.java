package com.maxmarkovdev.springboot.dao.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.QuestionDtoDao;
import com.maxmarkovdev.springboot.dao.util.SingleResultUtil;
import com.maxmarkovdev.springboot.model.dto.QuestionDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl implements QuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<QuestionDto> getQuestionDtoById(long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                        "SELECT new com.maxmarkovdev.springboot.model.dto.QuestionDto(q.id, q.title, q.user.id," +
                                "q.user.name, q.user.imageLink, SUM(r.count)/(COUNT(r.id) / nullif(COUNT(DISTINCT r.id),0)), q.description, q.persistDateTime," +
                                "q.lastUpdateDateTime, SUM(0), COUNT(DISTINCT answer.id)," +
                                "(SELECT COUNT(up.vote) FROM VoteQuestion up WHERE up.vote = 'UP_VOTE' AND up.question.id = q.id) - " +
                                "(SELECT COUNT(down.vote) FROM VoteQuestion down WHERE down.vote = 'DOWN_VOTE' AND down.question.id = q.id)) " +
                                "FROM Question q LEFT JOIN Reputation r ON q.user.id = r.author.id LEFT JOIN Answer answer ON q.id = answer.question.id " +
                                "WHERE q.id =:id GROUP BY q.id, q.user.name, q.user.imageLink", QuestionDto.class)
                .setParameter("id", id));
    }
}
