package com.azarenka.jc.domain.test;

import com.azarenka.jc.domain.BaseEntity;
import com.azarenka.jc.domain.User;
import com.azarenka.jc.domain.auth.JwtResponse;
import com.azarenka.jc.domain.auth.LoginForm;
import com.azarenka.jc.test.TestUtils;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import java.util.Arrays;

/**
 * Verifies domain.
 *
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.08.2020
 */
public class DomainVerifierTest {

    @Test
    public void testPojoStructureAndBehavior() {
        TestUtils.testPojoStructureAndBehavior(Arrays.asList(BaseEntity.class, User.class, LoginForm.class, JwtResponse.class));
    }

    @Test
    public void testEquals() {
        Arrays.asList(BaseEntity.class, User.class, LoginForm.class, JwtResponse.class)
                .forEach(
                        clazz -> EqualsVerifier.forClass(clazz).suppress(Warning.NONFINAL_FIELDS).usingGetClass().verify());
    }
}
