package org.xtreemes.damascus.code.block.trigger;

import org.xtreemes.damascus.code.TriggerType;

public class RightClickTrigger extends Trigger {
    @Override
    public TriggerType getTrigger() {
        return TriggerType.RCLICK;
    }
}
