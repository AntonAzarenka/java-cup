package com.azarenka.jc.repository;

import com.azarenka.jc.domain.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Interface of user repository.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.08.2020
 */
@Mapper
public interface IUserRepository {

    /**
     * Gets user by login.
     *
     * @param email login
     * @return user.
     */
    User getByEmail(String email);

    /**
     * Saves of {@link User}.
     *
     * @param user {@link User}.
     */
    void save(User user);

    /**
     * Returns user by activation code.
     *
     * @param code code.
     * @return instance of {@link User}.
     */
    User getByActivateCode(String code);

    /**
     * Updates activation status of user.
     *
     * @param id user id.
     */
    void updateActivationStatus(@Param("id") String id);

    /**
     * This method take all users for send email.
     *
     * @return users {@link User}
     */
    List<User> getAll();

    /**
     * Removes user with id.
     *
     * @param id user id
     */
    void remove(String id);
}
