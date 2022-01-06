package com.maxmarkovdev.springboot.dao.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.AnswerDtoDao;
import com.maxmarkovdev.springboot.model.dto.AnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnswerDto> getAnswerById(Long id) {
        return entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.AnswerDto" +
                                "(answ.id," +
                                "answ.user.id," +
                                "SUM(r.count)," +
                                "answ.question.id," +
                                "answ.htmlBody," +
                                "answ.persistDateTime," +
                                "answ.isHelpful," +
                                "answ.dateAcceptTime," +
                                "(SELECT count (up.vote) FROM VoteAnswer up WHERE up.vote = 'UP_VOTE' AND up.user.id = answ.user.id) - " +
                                "(SELECT count (down.vote) FROM VoteAnswer down WHERE down.vote = 'DOWN_VOTE' AND down.user.id = answ.user.id)," +
                                "answ.user.imageLink," +
                                "answ.user.nickname)" +
                                "FROM Answer answ " +
                                "LEFT JOIN Reputation r ON answ.user.id = r.author.id WHERE answ.question.id = :id GROUP BY answ.id, answ.user.imageLink, answ.user.nickname",
                        AnswerDto.class)
                .setParameter("id", id)
                .getResultList();
    }
}
