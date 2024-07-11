package org.xtreemes.damascus.code.block.action;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.xtreemes.damascus.code.block.MidLine;


public abstract class Action extends MidLine {
    @Override
    public Material getSign(){
        return Material.BAMBOO_WALL_SIGN;
    }
    @Override
    public Material getConnector(){
        return Material.YELLOW_CONCRETE;
    }
    public DyeColor getSignColour(){
        return DyeColor.YELLOW;
    }
    public String getSignMain(){
        return "Action";
    }

}
