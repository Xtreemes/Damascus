package org.xtreemes.damascus.code.block.action;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.xtreemes.damascus.code.RunInfo;

public class SetOvariesAction extends Action {
    @Override
    public void run(RunInfo info) {
        Entity e = info.getTargetEntity();
        if(e != null){
            if(e instanceof Player p){
                p.sendMessage("you feel ovaries growing inside of you");
            }
        }
    }
}
