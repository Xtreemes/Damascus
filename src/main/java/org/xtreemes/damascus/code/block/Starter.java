package org.xtreemes.damascus.code.block;

import org.xtreemes.damascus.code.CodeBlock;
import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.TriggerType;

public abstract class Starter extends CodeBlock {
    public abstract TriggerType getTrigger();

    @Override
    protected void run(RunInfo info){

    }
}
