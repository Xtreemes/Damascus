package org.xtreemes.damascus.code.block.action;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class SendMessageAction extends Action {
    @Override
    public String getSignSub(){
        return "Message";
    }
    @Override
    public void run(@Nullable Entity target) {
        if(target instanceof Player p){
            p.sendMessage("No params for now...");
        }
    }
}
