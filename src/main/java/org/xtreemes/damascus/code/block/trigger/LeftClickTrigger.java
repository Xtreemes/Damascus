package org.xtreemes.damascus.code.block.trigger;

import org.xtreemes.damascus.code.TriggerType;

public class LeftClickTrigger extends Trigger {
    @Override
    public String getSignSub(){
        return "LeftClick";
    }
    @Override
    public TriggerType getTrigger() {
        return TriggerType.LCLICK;
    }
}
