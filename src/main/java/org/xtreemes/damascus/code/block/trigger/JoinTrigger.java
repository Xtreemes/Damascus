package org.xtreemes.damascus.code.block.trigger;

import org.xtreemes.damascus.code.Trigger;
import org.xtreemes.damascus.code.block.Starter;

public class JoinTrigger extends Starter {
    @Override
    public Trigger getTrigger() {
        return Trigger.JOIN;
    }
}
