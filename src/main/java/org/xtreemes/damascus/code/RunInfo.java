package org.xtreemes.damascus.code;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class RunInfo {
    private final Event EVENT;
    private final Entity TARGET_ENTITY;

    public RunInfo(@Nullable Event event, @Nullable Entity target){
        this.EVENT = event;
        this.TARGET_ENTITY = target;
    }

    @Nullable
    public Event getEvent(){
        return this.EVENT;
    }
    @Nullable
    public Entity getTargetEntity(){
        return this.TARGET_ENTITY;
    }
}
