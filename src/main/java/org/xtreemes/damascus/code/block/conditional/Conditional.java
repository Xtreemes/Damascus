package org.xtreemes.damascus.code.block.conditional;

import org.bukkit.DyeColor;
import org.xtreemes.damascus.code.block.Nestable;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Conditional extends Nestable {

    public void run(){
        if(condition()){

        }
    }
    abstract public boolean condition();
    public DyeColor getSignColour(){
        return DyeColor.PINK;
    }
    public String getSignMain(){
        return "Conditional";
    }
}
