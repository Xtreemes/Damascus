package org.xtreemes.damascus.code.block.empty;

import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.block.conditional.Conditional;

public class EmptyConditional extends Conditional {

    @Override
    public boolean condition(RunInfo info) {
        return false;
    }
}
