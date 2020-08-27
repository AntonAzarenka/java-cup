package com.azarenka.jc.repository.testInteg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static java.util.Collections.singleton;

import com.azarenka.jc.domain.Role;
import com.azarenka.jc.domain.User;
import com.azarenka.jc.repository.IUserRepository;
import com.azarenka.jc.repository.IUsersRoleMapRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EmbeddedPostgresConfig.class)
public class UserRepositoryIntegrationTest {

    private static final String EMAIL_ADMIN = "Admin@mail.ru";
    private static final String EMAIL_USER_1 = "user@mail.ru";
    private static final String EMAIL_USER_2 = "userTwo@mail.ru";
    private static final String EMAIL_USER_3 = "userThree@mail.ru";
    private static final String EMAIL_USER_4 = "userFour@mail.ru";
    private static final String ROLE_USER = "USER";

    @Resource
    private IUserRepository userRepository;
    @Resource
    private IUsersRoleMapRepository roleMapRepository;

    @Before
    public void setup() {
        User user = buildUser("2984e9d8-e5d8-4839-8de0-604a78104cea", EMAIL_USER_2, Role.USER,
            "2993f33d-cd81-4b87-a4d4-57a11e65aa9b");
        user.setRegistrationDate(LocalDateTime.of(2019, 9, 22, 0, 0, 0));
        User user2 = buildUser("5918010e-13af-45cf-951f-c0788fca785f", EMAIL_USER_3, Role.USER,
            "8a26be87-f25d-4808-a0d2-a786b898832f");
        user.setRegistrationDate(LocalDateTime.of(2019, 9, 22, 0, 0, 0));
        userRepository.save(user);
        userRepository.save(user2);
        String roleId = roleMapRepository.getIdByRole(ROLE_USER);
        roleMapRepository.saveRole(user.getId(), roleId);
        roleMapRepository.saveRole(user2.getId(), roleId);
    }

    @After
    public void destroy() {
        userRepository.remove("2984e9d8-e5d8-4839-8de0-604a78104cea");
        userRepository.remove("5918010e-13af-45cf-951f-c0788fca785f");
    }

    @Test
    public void testGetUserByEmail() {
        User user = buildUser("4993f33d-cd83-4b87-a4d4-57a11e65aa9b", EMAIL_ADMIN, Role.ADMIN, "active");
        user.setRegistrationDate(LocalDateTime.of(2019, 9, 22, 0, 0, 0));
        assertUser(user, userRepository.getByEmail(EMAIL_ADMIN));
    }

    @Test
    public void testSaveUser() {
        User user = buildUser("1e67e110-69a8-4797-94b3-dc0a717ac4fe", EMAIL_USER_1, Role.USER,
            "1993f33d-7d83-4b87-a4d4-57a11e65aa9b");
        user.setRegistrationDate(LocalDateTime.of(2019, 9, 22, 0, 0, 0));
        userRepository.save(user);
        String roleId = roleMapRepository.getIdByRole(ROLE_USER);
        roleMapRepository.saveRole(user.getId(), roleId);
        assertUser(user, userRepository.getByEmail(EMAIL_USER_1));
    }

    @Test
    public void testGetByActivateCode() {
        User user = userRepository.getByEmail(EMAIL_USER_2);
        assertUser(user, userRepository.getByActivateCode("2993f33d-cd81-4b87-a4d4-57a11e65aa9b"));
    }

    @Test
    public void testUpdateActivationStatus() {
        User expectedUser = buildUser("4993f33d-cd83-4b87-a4d4-57a11e65aa9b", EMAIL_ADMIN, Role.ADMIN, "active");
        assertUser(expectedUser, userRepository.getByActivateCode("active"));
        expectedUser.setActivateCode("ACTIVATED");
        userRepository.updateActivationStatus("4993f33d-cd83-4b87-a4d4-57a11e65aa9b");
        assertUser(expectedUser, userRepository.getByEmail(EMAIL_ADMIN));
    }

    @Test
    public void testRemoveUser() {
        User user = buildUser("c4b0b029-c97f-4aa3-a45a-c70b011bcef8", EMAIL_USER_4, Role.USER,
            "07eb3ccc-06da-41ee-9674-266d6e8215b4");
        user.setRegistrationDate(LocalDateTime.of(2019, 9, 22, 0, 0, 0));
        userRepository.save(user);
        String roleId = roleMapRepository.getIdByRole(ROLE_USER);
        roleMapRepository.saveRole(user.getId(), roleId);
        List<User> users = userRepository.getAll();
        assertEquals(5, users.size());
        userRepository.remove("c4b0b029-c97f-4aa3-a45a-c70b011bcef8");
        List<User> removedUsers = userRepository.getAll();
        assertEquals(4, removedUsers.size());
    }

    @Test
    public void testGetAll() {
        List<User> users = userRepository.getAll();
        assertEquals(4, users.size());
    }

    @Test(expected = DuplicateKeyException.class)
    public void testDuplicateUserId() {
        User user = buildUser("1e67e110-69a8-4797-94b3-dc0a717ac4fe", EMAIL_USER_4, Role.USER,
            "1993f33d-7d83-4b87-a4d4-57a11e65aa9b");
        userRepository.save(user);
    }

    private void assertUser(User expectedUser, User actualUser) {
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getRoles(), actualUser.getRoles());
        assertEquals(expectedUser.getActivateCode(), actualUser.getActivateCode());
        assertEquals(expectedUser.getName(), actualUser.getName());
    }

    private User buildUser(String id, String email, Role role, String activateCode) {
        User user = new User();
        user.setId(id);
        user.setEnabled(true);
        user.setEmail(email);
        user.setRoles(singleton(role));
        user.setActivateCode(activateCode);
        user.setName("admin");
        user.setPassword("admin");
        user.setRegistrationDate(LocalDateTime.of(2019, 9, 22, 0, 0, 0));
        user.setCurrentMenu("");
        return user;
    }
}
