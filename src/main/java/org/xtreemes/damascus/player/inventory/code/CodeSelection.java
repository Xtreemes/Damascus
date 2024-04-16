package org.xtreemes.damascus.player.inventory.code;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.xtreemes.damascus.code.CodeBlock;
import org.xtreemes.damascus.player.inventory.CancelClickInv;

import java.util.HashMap;

public abstract class CodeSelection extends CancelClickInv {
    protected HashMap<ItemStack, CodeBlock> ITEM_TO_CODE = new HashMap<>();
    protected Inventory INVENTORY;
    protected Location LOC;
    public CodeBlock getCode(ItemStack i){
        HashMap<ItemStack, CodeBlock> copy = new HashMap<>(ITEM_TO_CODE);
        return copy.get(i);
    }
    protected ItemStack setItem(Material mat, CodeBlock c){
        ItemStack item = new ItemStack(mat);
        ITEM_TO_CODE.put(item, c);
        INVENTORY.addItem(item);
        return item;
    }

    // Location is set to the BARREL of the codeblock you are editing
    public CodeSelection(Location loc){
        LOC = loc;
    }
    public Location getLocation(){
        return LOC;
    }
}
