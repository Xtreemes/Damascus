package org.xtreemes.damascus.code.block.world;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.xtreemes.damascus.code.block.MidLine;

public abstract class WorldAction extends MidLine {
    public Material getSign(){
        return Material.ACACIA_WALL_SIGN;
    }
    public Material getConnector(){
        return Material.ORANGE_CONCRETE;
    }
    public DyeColor getSignColour(){
        return DyeColor.ORANGE;
    }
    public String getSignMain(){
        return "World";
    }
}
