package org.xtreemes.damascus.code.block.empty;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.xtreemes.damascus.code.block.action.Action;

public class EmptyTest extends Action {
    @Override
    public void run(@Nullable Entity target) {

    }
    @Override
    public Material getConnector(){
        return Material.WHITE_CONCRETE;
    }
}
