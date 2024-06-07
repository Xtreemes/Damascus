package org.xtreemes.damascus.code.value;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum ValueType {
    NONE(new NumberValue(), NamedTextColor.AQUA),
    NUMBER(new NumberValue(), NamedTextColor.RED),
    TEXT(new TextValue(), TextColor.color(0x7CE530)),
    VARIABLE(new VariableValue(), NamedTextColor.YELLOW);

    private final Value VALUE_OBJECT;
    private final TextColor COLOR;
    ValueType(Value v, TextColor c){
        VALUE_OBJECT = v;
        COLOR = c;
    }

    public Value getValue(){
        return VALUE_OBJECT.clone();
    }
    public TextColor getColor() {return COLOR;}
}
