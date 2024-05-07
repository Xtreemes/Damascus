package org.xtreemes.damascus.code;

import org.xtreemes.damascus.code.block.action.CrashAction;
import org.xtreemes.damascus.code.block.action.SendMessageAction;
import org.xtreemes.damascus.code.block.conditional.SneakingConditional;
import org.xtreemes.damascus.code.block.empty.EmptyAction;
import org.xtreemes.damascus.code.block.empty.EmptyConditional;
import org.xtreemes.damascus.code.block.empty.EmptyTrigger;
import org.xtreemes.damascus.code.block.trigger.JoinTrigger;
import org.xtreemes.damascus.code.block.trigger.LeftClickTrigger;
import org.xtreemes.damascus.code.block.trigger.RightClickTrigger;

// I'm so sorry to whoever ends up reading this. I am too lazy to think about
// a better way to do this and have decided to do it shitty. Godspeed.
public enum CodeList {
    /**
     * T: Trigger
     * A: Action
     * C: Conditional
     */

    // TRIGGERS
    T_EMPTY(new EmptyTrigger(), ""),
    T_JOIN(new JoinTrigger(), "Join"),
    T_RCLICK(new RightClickTrigger(), "RightClick"),
    T_LCLICK(new LeftClickTrigger(), "LeftClick"),

    // ACTIONS
    A_EMPTY(new EmptyAction(), ""),
    A_CRASH(new CrashAction(), "Crash"),
    A_MESSAGE(new SendMessageAction(), "Message"),

    // CONDITIONAL
    C_EMPTY(new EmptyConditional(), ""),
    C_SNEAKING(new SneakingConditional(), "Sneaking");

    private final CodeBlock CLASS;
    private final String SIGN;

    CodeList(CodeBlock c, String sign){
        CLASS = c;
        SIGN = sign;
    }
    public CodeBlock getCodeBlock(){
        return CLASS.clone();
    }
    public String getSignText(){return SIGN;}
    public static CodeList classToEnum(CodeBlock code){
        Class<? extends CodeBlock> check_class = code.getClass();
        for(CodeList en : CodeList.values()){
            Class<? extends CodeBlock> cb = en.getCodeBlock().getClass();
            if(cb.isAssignableFrom(check_class)){
                return en;
            }
        }
        return A_CRASH;
    }
}
