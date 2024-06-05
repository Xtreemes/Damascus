package org.xtreemes.damascus.player.inventory.code;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.xtreemes.damascus.code.CodeList;
import org.xtreemes.damascus.code.block.trigger.JoinTrigger;
import org.xtreemes.damascus.code.block.trigger.LeftClickTrigger;
import org.xtreemes.damascus.code.block.trigger.RightClickTrigger;

public class TriggerInvHolder extends CodeSelection {

    public TriggerInvHolder(Location loc) {
        super(loc);
    }

    @Override
    public @NotNull Inventory getInventory() {
        INVENTORY = Bukkit.createInventory(this, 27);
        setItem(Material.DIAMOND, CodeList.T_JOIN);
        setItem(Material.IRON_SWORD, CodeList.T_LCLICK);
        setItem(Material.SHIELD, CodeList.T_RCLICK);
        setItem(Material.IRON_LEGGINGS, CodeList.T_SNEAK);

        return INVENTORY;
    }
}
