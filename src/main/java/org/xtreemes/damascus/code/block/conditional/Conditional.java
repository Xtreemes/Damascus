package org.xtreemes.damascus.code.block.conditional;

import org.bukkit.DyeColor;
import org.xtreemes.damascus.code.CodeExecutor;
import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.block.Nestable;


public abstract class Conditional extends Nestable {

    @Override
    public void execute(RunInfo info, Runnable runnable){
        CodeExecutor executor = new CodeExecutor(info, CODE, runnable);
        if(condition(info)){
            executor.executeNext();
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
