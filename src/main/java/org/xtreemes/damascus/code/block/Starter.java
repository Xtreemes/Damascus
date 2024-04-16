package org.xtreemes.damascus.code.block;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.xtreemes.damascus.code.CodeBlock;
import org.xtreemes.damascus.code.TriggerType;

public abstract class Starter extends CodeBlock {
    public abstract TriggerType getTrigger();

    @Override
    public void run(@Nullable Entity target){

    }
}
