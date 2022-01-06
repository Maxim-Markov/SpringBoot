package com.maxmarkovdev.springboot.dao.impl.dto;

import com.maxmarkovdev.springboot.dao.interfaces.dto.UserDtoDao;
import com.maxmarkovdev.springboot.dao.util.SingleResultUtil;
import com.maxmarkovdev.springboot.model.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserDto> getUserDtoById(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                    "SELECT new com.maxmarkovdev.springboot.model.dto.UserDto" +
                       "(u.id, u.email, u.name, u.imageLink, u.city, SUM(r.count )) " +
                       "FROM User u LEFT JOIN Reputation r ON u.id = r.author.id WHERE u.id =:id GROUP BY u.id",
                       UserDto.class)
                    .setParameter("id", id));
    }
}
