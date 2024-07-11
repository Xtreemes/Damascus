package org.xtreemes.damascus.code.parameters;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ParameterContainer {
    Parameter[] value();
}
