package org.xtreemes.damascus.code.parameters;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xtreemes.damascus.Damascus;
import org.xtreemes.damascus.code.value.NumberValue;
import org.xtreemes.damascus.code.value.TextValue;

import java.util.ArrayList;


public class Parameters {
    private final ArrayList<String> QUEUE = new ArrayList<>();
    public Parameters(@Nullable ItemStack[] items){
        if(items != null) {
            for (ItemStack item : items.clone()) {
                if (item != null) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        PersistentDataContainer pdc = meta.getPersistentDataContainer();
                        NamespacedKey nsk = NamespacedKey.fromString("value", Damascus.PLUGIN);
                        if (pdc.has(nsk)) {
                            String value = pdc.get(nsk, PersistentDataType.STRING);
                            //String value_type = pdc.get(NamespacedKey.fromString("value_type", Damascus.PLUGIN), PersistentDataType.STRING);
                            QUEUE.add(value);
                        }
                    }
                }
            }
        }
    }
    private String getNext(){
        try {
            String result = QUEUE.get(0);
            QUEUE.remove(0);
            return result;
        } catch(IndexOutOfBoundsException e){
            return "0";
        }
    }
    public float getNumber(){
        String result = getNext();
        return new NumberValue().setFromString(result).getNumber();
    }
    public Component getText(){
        return new TextValue().setFromString(getNext()).getText();
    }
}
