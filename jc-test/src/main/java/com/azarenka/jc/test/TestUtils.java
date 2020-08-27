package com.azarenka.jc.test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Test utils.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.08.2020
 */
public class TestUtils {

    private static final String CONSTRUCTOR_ERROR_MESSAGE = "Instantiation via private constructor is forbidden.";

    private static final Validator VALIDATOR = ValidatorBuilder.create()
        .with(new GetterMustExistRule())
        .with(new NoFieldShadowingRule())
        .with(new NoStaticExceptFinalRule())
        .with(new SetterTester())
        .with(new GetterTester())
        .build();

    /**
     * Default constructor.
     */
    private TestUtils() {
        throw new AssertionError(CONSTRUCTOR_ERROR_MESSAGE);
    }

    /**
     * Test pojo structures.
     *
     * @param classes instance of class
     */
    @Test
    public static void testPojoStructureAndBehavior(List<Class> classes) {
        List<PojoClass> pojoClasses = classes.stream().map(PojoClassFactory::getPojoClass).collect(Collectors.toList());
        VALIDATOR.validate(pojoClasses);
    }
}
