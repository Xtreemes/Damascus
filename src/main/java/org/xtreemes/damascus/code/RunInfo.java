package org.xtreemes.damascus.code;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RunInfo {
    private final Event EVENT;
    private final Entity TARGET_ENTITY;
    private CompletableFuture<Boolean> SHOULD_CANCEL;
    private final boolean DEFAULT_CANCEL;

    public RunInfo(@Nullable Event event, @Nullable Entity target, boolean should_cancel){
        this.EVENT = event;
        this.TARGET_ENTITY = target;
        this.SHOULD_CANCEL = new CompletableFuture<>();
        this.DEFAULT_CANCEL = should_cancel;
    }

    public void cancelEvent(boolean cancel){
        SHOULD_CANCEL.complete(cancel);
    }
    public void cancelEvent(){
        SHOULD_CANCEL.complete(DEFAULT_CANCEL);
    }
    public CompletableFuture<Boolean> shouldCancel(){
        return SHOULD_CANCEL;
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
