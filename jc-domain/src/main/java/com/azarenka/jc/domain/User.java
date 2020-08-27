package com.azarenka.jc.domain;

import com.azarenka.jc.domain.auth.SignUpForm;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Class for Entity User.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.08.2020
 */
public class User extends BaseEntity {

    private String email;
    private String password;
    private String name;
    private boolean enabled = false;
    private Set<Role> roles;
    private String avatar;
    private String activateCode;
    private String currentMenu;
    private LocalDateTime registrationDate = LocalDateTime.now();

    /**
     * Constructor.
     *
     * @param id       user id
     * @param email    user email
     * @param password user password
     */
    public User(String id, String email, String password) {
        this.setId(id);
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor to create {@link User} based on {@link SignUpForm}.
     *
     * @param registrationUser instance of {@link SignUpForm}.
     */
    public User(SignUpForm registrationUser) {
        this.email = registrationUser.getUsername();
        this.password = registrationUser.getPassword();
        this.name = StringUtils.EMPTY;
    }

    /**
     * Default constructor.
     */
    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }

    public String getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(String currentMenu) {
        this.currentMenu = currentMenu;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(enabled, user.enabled)
                .append(email, user.email)
                .append(password, user.password)
                .append(name, user.name)
                .append(roles, user.roles)
                .append(avatar, user.avatar)
                .append(activateCode, user.activateCode)
                .append(currentMenu, user.currentMenu)
                .append(registrationDate, user.registrationDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(email)
                .append(password)
                .append(name)
                .append(enabled)
                .append(roles)
                .append(avatar)
                .append(activateCode)
                .append(currentMenu)
                .append(registrationDate)
                .toHashCode();
    }
}
