package org.xtreemes.damascus.code.value;

import org.bukkit.inventory.ItemStack;

public abstract class Value implements Cloneable {
    public abstract String getAsString();
    public abstract Value setFromString(String value);
    public abstract ItemStack getAsItem();

    @Override
    public Value clone() {
        try {
            return (Value) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
