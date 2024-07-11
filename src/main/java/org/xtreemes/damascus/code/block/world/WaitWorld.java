package org.xtreemes.damascus.code.block.world;

import org.bukkit.Bukkit;
import org.xtreemes.damascus.Damascus;
import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.parameters.Parameter;
import org.xtreemes.damascus.code.value.ValueType;

@Parameter(value= ValueType.NUMBER, desc="Amount of ticks to wait")
public class WaitWorld extends WorldAction {
    @Override
    protected void run(RunInfo info) {

    }

    @Override
    public void execute(RunInfo info, Runnable runnable){
        Bukkit.getScheduler().runTaskLater(Damascus.PLUGIN, runnable, (int) getParameters(info).getNumber());
    }
}
