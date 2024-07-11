package org.xtreemes.damascus.code.block.conditional;

import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.parameters.Parameters;

public class EqualsConditional extends Conditional {
    @Override
    public boolean condition(RunInfo info) {
        Parameters params = getParameters(info);
        return params.getValue().equals(params.getValue());
    }
}
