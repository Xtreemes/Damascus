package org.xtreemes.damascus.code.block;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.xtreemes.damascus.code.CodeBlock;
import org.xtreemes.damascus.code.Trigger;

public abstract class Starter extends CodeBlock {
    public abstract Trigger getTrigger();

    @Override
    public void run(@Nullable Entity target){

    }
}
