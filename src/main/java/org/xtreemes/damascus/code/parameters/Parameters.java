package org.xtreemes.damascus.code.parameters;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.event.KeyValuePair;
import org.xtreemes.damascus.Damascus;
import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.value.NumberValue;
import org.xtreemes.damascus.code.value.TextValue;
import org.xtreemes.damascus.code.block.variable.VariableScope;

import java.util.ArrayList;


public class Parameters {
    private final ArrayList<Object> QUEUE = new ArrayList<>();
    public Parameters(@Nullable ItemStack[] items, RunInfo info){
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
                        } else {
                            NamespacedKey var_nsk = NamespacedKey.fromString("variable", Damascus.PLUGIN);
                            if(pdc.has(var_nsk)){
                                String var = pdc.get(var_nsk, PersistentDataType.STRING);
                                // TODO: make it use actual var scope and not just game
                                QUEUE.add(new KeyValuePair(var, info.getVariableValue(var, VariableScope.GAME)));
                            }
                        }
                    }
                }
            }
        }
    }
    private Object getNext(){
        try {
            Object result = QUEUE.get(0);
            QUEUE.remove(0);
            return result;
        } catch(IndexOutOfBoundsException e){
            return "0";
        }
    }
    private String getNextString(){
        Object result_object = getNext();
        String s = "";
        if(result_object instanceof String){
            s = (String) result_object;
        } else if(result_object instanceof KeyValuePair kv){
            s = (String) kv.value;
        }
        return s;
    }
    public String getValue(){
        return getNextString();
    }
    public float getNumber(){
        String s = getNextString();
        return new NumberValue().setFromString(s).getNumber();
    }
    public Component getText(){
        return new TextValue().setFromString(getNextString()).getText();
    }
    public String getVariable(){
        Object result_object = getNext();
        String s = "";
        if(result_object instanceof String){
            s = "";
        } else if(result_object instanceof KeyValuePair kv){
            s = (String) kv.key;
        }
        return s;
    }
}
