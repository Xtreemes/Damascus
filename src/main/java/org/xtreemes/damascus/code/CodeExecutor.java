package org.xtreemes.damascus.code;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CodeExecutor {
    private final ArrayList<CodeBlock> CODE;
    private final RunInfo INFO;
    private final Runnable CALLBACK;
    public CodeExecutor(RunInfo info, ArrayList<CodeBlock> code, @Nullable Runnable callback){
        CODE = new ArrayList<>(code);
        INFO = info;
        CALLBACK = callback;
    }
    public void executeNext(){
        if(!CODE.isEmpty()) {
            CODE.remove(0).execute(INFO, this::executeNext);
            if(CODE.isEmpty() && CALLBACK != null){
                CALLBACK.run();
            }
        }
    }
}
