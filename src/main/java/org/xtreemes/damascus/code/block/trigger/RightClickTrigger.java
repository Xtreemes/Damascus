package org.xtreemes.damascus.code.block.trigger;

import org.xtreemes.damascus.code.TriggerType;

public class RightClickTrigger extends Trigger {
    @Override
    public String getSignSub(){
        return "RightClick";
    }
    @Override
    public TriggerType getTrigger() {
        return TriggerType.RCLICK;
    }
}
