package org.xtreemes.damascus.code.parameters;

import org.slf4j.event.KeyValuePair;
import org.xtreemes.damascus.code.value.ValueType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(ParameterContainer.class)
public @interface Parameter {
    ValueType value();
    String desc();
}
