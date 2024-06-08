package org.xtreemes.damascus.player.inventory.code;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.slf4j.event.KeyValuePair;
import org.xtreemes.damascus.code.CodeBlock;
import org.xtreemes.damascus.code.CodeList;
import org.xtreemes.damascus.code.value.ValueType;
import org.xtreemes.damascus.player.inventory.CancelClickInv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class CodeSelection extends CancelClickInv {
    protected HashMap<ItemStack, CodeList> ITEM_TO_CODE = new HashMap<>();
    protected Inventory INVENTORY;
    protected Location LOC;
    public CodeBlock getCode(ItemStack i){
        return ITEM_TO_CODE.get(i).getCodeBlock().clone();
    }
    protected ItemStack setItem(Material mat, CodeList code_enum){
        ItemStack item = new ItemStack(mat);
        ItemMeta im = item.getItemMeta();
        im.displayName(Component.text(code_enum.getName(), Style.empty().decoration(TextDecoration.ITALIC, false).color(code_enum.getReturnType().getColor())));
        String desc = code_enum.getDescription();
        ArrayList<Component> lore = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for(String line : desc.split(" ")){
            current.append(line).append(" ");
            if(current.length() >= 30){
                String string_line = current.deleteCharAt(current.length()-1).toString();
                lore.add(Component.text(string_line, Style.empty().decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY)));
                current = new StringBuilder();
            }
        }
        if(!current.isEmpty()){
            String string_line = current.deleteCharAt(current.length()-1).toString();
            lore.add(Component.text(string_line, Style.empty().decoration(TextDecoration.ITALIC, false).color(NamedTextColor.GRAY)));
        }


        // Parameter Lore
        ArrayList<KeyValuePair> params = code_enum.getParameters();
        if(!params.isEmpty()){
            lore.add(Component.empty().decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Parameters",Style.empty()
                    .color(NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC, false)
            ));
            for(KeyValuePair param : params){
                Component line = Component.empty();
                line = line.append(Component.text(param.key,ValueType.valueOf(param.key).getColor()));
                line = line.append(Component.text(" - ", NamedTextColor.DARK_GRAY)).append(Component.text((String) param.value, NamedTextColor.GRAY));
                lore.add(line.decoration(TextDecoration.ITALIC, false));
            }
        }

        im.lore(lore);
        im.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS,
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ARMOR_TRIM,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_DYE,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(im);

        ITEM_TO_CODE.put(item, code_enum);
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
