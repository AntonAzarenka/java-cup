package com.azarenka.jc.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * User repository.
 * <p>
 * (c) ant-azarenko@mail.ru
 * <p>
 * <p>
 * Date 21.07.2019
 * <p>
 * author Anton Azarenka
 */
@Mapper
public interface IUsersRoleMapRepository {

    /**
     * Writes role of user.
     *
     * @param userId user id.
     * @param roleId role id.
     */
    void saveRole(@Param("userId") String userId, @Param("roleId") String roleId);

    /**
     * Returns role id by user role.
     *
     * @return role id.
     */
    String getIdByRole(String role);
}
