package org.xtreemes.damascus.player.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.xtreemes.damascus.code.value.Value;
import org.xtreemes.damascus.code.value.ValueType;

@RegisterCommand({"num","text","var"})
public class ValueCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String value_name = command.getName();
        String arg = String.join(" ", args);
        Player p = (Player) sender;
        ItemStack item = null;
        switch(value_name) {
            case "num" -> {
                Value nv = ValueType.NUMBER.getValue().setFromString(arg);
                item = nv.getAsItem();
            }
            case "text" -> {
                Value tv = ValueType.TEXT.getValue().setFromString(arg);
                item = tv.getAsItem();
            }
            case "var" -> {
                Value vv = ValueType.VARIABLE.getValue().setFromString(arg);
                item = vv.getAsItem();
            }
        }
        if(item != null){
            p.getInventory().addItem(item);
        }
        return true;
    }
}
