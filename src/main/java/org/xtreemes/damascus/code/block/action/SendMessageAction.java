package org.xtreemes.damascus.code.block.action;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.parameters.Parameters;

public class SendMessageAction extends Action {
    @Override
    public void run(RunInfo info) {
        Entity target = info.getTargetEntity();
        if(target instanceof Player p){
            Parameters param = getParameters();
            p.sendMessage(param.getText());
        }
    }
}
