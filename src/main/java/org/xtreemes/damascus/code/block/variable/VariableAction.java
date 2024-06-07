package org.xtreemes.damascus.code.block.variable;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.xtreemes.damascus.code.block.MidLine;

public abstract class VariableAction extends MidLine {
    @Override
    public Material getSign(){
        return Material.OAK_WALL_SIGN;
    }
    @Override
    public Material getConnector(){
        return Material.WHITE_CONCRETE;
    }
    public DyeColor getSignColour(){
        return DyeColor.WHITE;
    }
    public String getSignMain(){
        return "Variable";
    }
}
