package org.xtreemes.damascus.player.inventory.code;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.xtreemes.damascus.code.CodeList;

public class WorldInvHolder extends CodeSelection {
    public WorldInvHolder(Location loc) {
        super(loc);
    }

    @Override
    public @NotNull Inventory getInventory() {
        INVENTORY = Bukkit.createInventory(this, 27);
        setItem(Material.CLOCK, CodeList.W_WAIT);
        setItem(Material.BARRIER, CodeList.W_CANCEL);
        return INVENTORY;
    }
}
