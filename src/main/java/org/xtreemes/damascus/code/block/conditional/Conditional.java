package org.xtreemes.damascus.code.block.conditional;

import org.bukkit.DyeColor;
import org.xtreemes.damascus.code.CodeBlock;
import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.block.Nestable;


public abstract class Conditional extends Nestable {

    public void run(RunInfo info){
        if(condition(info)){
            for(CodeBlock c : CODE){
                c.run(info);
            }
        }
    }
    abstract public boolean condition(RunInfo info);
    public DyeColor getSignColour(){
        return DyeColor.PINK;
    }
    public String getSignMain(){
        return "Conditional";
    }
}
