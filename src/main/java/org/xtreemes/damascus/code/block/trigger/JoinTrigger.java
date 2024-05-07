package org.xtreemes.damascus.code.block.trigger;

import org.xtreemes.damascus.code.TriggerType;
import org.xtreemes.damascus.code.block.Starter;

public class JoinTrigger extends Trigger {

    @Override
    public TriggerType getTrigger() {
        return TriggerType.JOIN;
    }
}
