package com.maxmarkovdev.springboot.dao.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.PageDtoDao;
import com.maxmarkovdev.springboot.model.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "UserPaginationByVote")
public class UserDtoVoteDaoImpl implements PageDtoDao<UserDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserDto> getItems(Map<Object, Object> param) {
          int curPageNumber = (int) param.get("currentPageNumber");
          int itemsOnPage = (int) param.get("itemsOnPage");
        return entityManager.createQuery("SELECT new com.maxmarkovdev.springboot.model.dto.UserDto" +
                        "(u.id, u.email, u.name, u.imageLink, u.city, SUM(r.count )) " +
                        "FROM User u LEFT JOIN VoteAnswer va, VoteQuestion vq ON u.id = r.author.id GROUP BY u.id " +
                        "ORDER BY u.persistDateTime", UserDto.class)
                .setFirstResult((curPageNumber - 1) * itemsOnPage).setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public long getTotalResultCount(Map<Object, Object> param) {
        return 0;
    }
}
