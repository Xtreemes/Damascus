package org.xtreemes.damascus.code;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.xtreemes.damascus.code.block.variable.VariableScope;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class RunInfo {
    private final Event EVENT;
    private Entity DEFAULT_ENTITY;
    private Entity SELECTED_ENTITY;
    private CompletableFuture<Boolean> SHOULD_CANCEL;
    private final boolean DEFAULT_CANCEL;

    // Variable Containers
    private final HashMap<String, String> LOCAL_VARIABLES = new HashMap<>();
    private HashMap<String, String> GAME_VARIABLES;

    public RunInfo(@Nullable Event event, @Nullable Entity target, boolean should_cancel){
        this.EVENT = event;
        this.DEFAULT_ENTITY = target;
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
    public Entity getDefaultEntity(){
        return this.DEFAULT_ENTITY;
    }
    @Nullable
    public Entity getSelectedEntity(){return this.SELECTED_ENTITY;}
    public void setSelectedEntity(@Nullable Entity target){
        this.SELECTED_ENTITY = target;
    }

    public void setGame(HashMap<String, String> game_variables){
        GAME_VARIABLES = game_variables;
    }
    public String getVariableValue(String variable){
        String[] s = variable.split(":",2);
        String variable_name = s[1];
        VariableScope scope = VariableScope.GAME;
        if(s[0].equals("LOCAL")){
            scope = VariableScope.LOCAL;
        }
        switch(scope){
            case GAME -> {
                return GAME_VARIABLES.getOrDefault(variable_name, "0");
            }
            case LOCAL -> {
                return LOCAL_VARIABLES.getOrDefault(variable_name, "0");
            }
        }
        return "0";
    }
    public void setVariable(String variable, String value){
        String[] s = variable.split(":",2);
        if(s.length == 2) {
            String variable_name = s[1];
            VariableScope scope = VariableScope.GAME;
            if (s[0].equals("LOCAL")) {
                scope = VariableScope.LOCAL;
            }
            switch (scope) {
                case GAME -> GAME_VARIABLES.put(variable_name, value);
                case LOCAL -> LOCAL_VARIABLES.put(variable_name, value);
            }
        }
    }

}
