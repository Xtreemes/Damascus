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
import org.xtreemes.damascus.code.block.trigger.SneakTrigger;
import org.xtreemes.damascus.code.block.world.CancelWorld;
import org.xtreemes.damascus.code.block.world.WaitWorld;
import org.xtreemes.damascus.code.value.ValueType;

// I'm so sorry to whoever ends up reading this. I am too lazy to think about
// a better way to do this and have decided to do it shitty. Godspeed.
public enum CodeList {
    /**
     * T: Trigger
     * A: Action
     * C: Conditional
     * V: Variable
     * W: World
     */

    // TRIGGERS
    T_EMPTY(new EmptyTrigger(), "", "", "", ValueType.NONE),
    T_JOIN(new JoinTrigger(), "Join", "Player Join", "Runs when a player joins.", ValueType.NONE),
    T_RCLICK(new RightClickTrigger(), "RightClick", "Right Click", "Runs when a player right clicks.", ValueType.NONE),
    T_LCLICK(new LeftClickTrigger(), "LeftClick", "Left Click", "Runs when a player left clicks.", ValueType.NONE),
    T_SNEAK(new SneakTrigger(), "Sneak", "Sneak", "Runs when a player sneaks.", ValueType.NONE),

    // ACTIONS
    A_EMPTY(new EmptyAction(), "", "", "", ValueType.NONE),
    A_CRASH(new CrashAction(), "Crash", "Crash the damn player", "CRASH!", ValueType.NONE),
    A_MESSAGE(new SendMessageAction(), "Message", "Send Message", "Sends a message to the target.", ValueType.TEXT),

    // CONDITIONAL
    C_EMPTY(new EmptyConditional(), "", "", "", ValueType.NONE),
    C_SNEAKING(new SneakingConditional(), "Sneaking", "Is Sneaking", "is they ass out?", ValueType.NONE),

    // WORLD
    W_WAIT(new WaitWorld(), "Wait", "Wait", "Pauses the code for a certain amount of ticks.", ValueType.NONE),
    W_CANCEL(new CancelWorld(), "Cancel", "Cancel Event", "Cancels the event that triggered the code.", ValueType.NONE);

    // VARIABLE

    private final CodeBlock CLASS;
    private final String SIGN;
    private final String NAME;
    private final String DESC;
    private final ValueType RETURN_TYPE;

    CodeList(CodeBlock c, String sign, String name, String description, ValueType return_type){
        CLASS = c;
        SIGN = sign;
        NAME = name;
        DESC = description;
        RETURN_TYPE = return_type;
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

    public String getName(){
        return NAME;
    }
    public String getDescription(){
        return DESC;
    }
    public ValueType getReturnType(){
        return RETURN_TYPE;
    }
}
