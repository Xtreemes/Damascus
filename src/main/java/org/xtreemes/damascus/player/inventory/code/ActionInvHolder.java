package org.xtreemes.damascus.player.inventory.code;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.xtreemes.damascus.code.block.action.CrashAction;
import org.xtreemes.damascus.code.block.action.SendMessageAction;
import org.xtreemes.damascus.code.block.action.Wait;

public class ActionInvHolder extends CodeSelection {
    public ActionInvHolder(Location loc) {
        super(loc);
    }

    @Override
    public @NotNull Inventory getInventory() {
        INVENTORY = Bukkit.createInventory(this, 27);
        setItem(Material.BOOK, new SendMessageAction());
        setItem(Material.BARRIER, new CrashAction());
        setItem(Material.CLOCK, new Wait());
        return INVENTORY;
    }
}
