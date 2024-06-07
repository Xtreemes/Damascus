package org.xtreemes.damascus.code;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.xtreemes.damascus.code.block.variable.VariableScope;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class RunInfo {
    private final Event EVENT;
    private final Entity TARGET_ENTITY;
    private CompletableFuture<Boolean> SHOULD_CANCEL;
    private final boolean DEFAULT_CANCEL;

    // Variable Containers
    private final HashMap<String, String> LOCAL_VARIABLES = new HashMap<>();
    private HashMap<String, String> GAME_VARIABLES;

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

    public void setGame(HashMap<String, String> game_variables){
        GAME_VARIABLES = game_variables;
    }
    public String getVariableValue(String variable_name, VariableScope scope){
        switch(scope){
            case GAME -> {
                return GAME_VARIABLES.getOrDefault(variable_name, "");
            }
        }
        return "";
    }
    public void setVariable(String variable_name, VariableScope scope, String value){
        switch(scope){
            case GAME -> GAME_VARIABLES.put(variable_name, value);
        }
    }
}
