package org.xtreemes.damascus.code.block.action;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.xtreemes.damascus.code.RunInfo;

public class SendMessageAction extends Action {
    @Override
    public String getSignSub(){
        return "Message";
    }
    @Override
    public void run(RunInfo info) {
        Entity target = info.getTargetEntity();
        if(target instanceof Player p){
            Event e = info.getEvent();
            if(e instanceof PlayerInteractEvent ee){
                p.sendMessage(ee.getAction().name());
            }
        }
    }
}
