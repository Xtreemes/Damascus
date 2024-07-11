package org.xtreemes.damascus.code.block.action;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.parameters.Parameter;
import org.xtreemes.damascus.code.parameters.Parameters;
import org.xtreemes.damascus.code.value.ValueType;

@Parameter(value= ValueType.TEXT, desc="Message to send")
public class SendMessageAction extends Action {
    @Override
    public void run(RunInfo info) {
        Entity target = info.getDefaultEntity();
        if(target instanceof Player p){
            Parameters param = getParameters(info);
            p.sendMessage(param.getText());
        }
    }
}
