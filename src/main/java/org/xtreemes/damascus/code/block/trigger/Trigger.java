package org.xtreemes.damascus.code.block.trigger;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.xtreemes.damascus.code.block.Starter;

public abstract class Trigger extends Starter {
    public Material getSign(){
        return Material.WARPED_WALL_SIGN;
    }
    public Material getConnector(){
        return Material.CYAN_CONCRETE;
    }
    public DyeColor getSignColour(){
        return DyeColor.CYAN;
    }
    public String getSignMain(){
        return "Trigger";
    }
}
