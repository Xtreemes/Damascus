package org.xtreemes.damascus.code.block.conditional;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.xtreemes.damascus.code.RunInfo;

public class SneakingConditional extends Conditional {

    @Override
    public boolean condition(RunInfo info) {
        Entity target = info.getDefaultEntity();
        if(target != null){
            if(target instanceof Player p){
                return p.isSneaking();
            }
        }
        return false;
    }
}
