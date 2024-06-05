package org.xtreemes.damascus.player.inventory.code;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.xtreemes.damascus.code.CodeList;
import org.xtreemes.damascus.code.block.conditional.SneakingConditional;

public class ConditionalInvHolder extends CodeSelection {
    public ConditionalInvHolder(Location loc) {
        super(loc);
    }

    @Override
    public @NotNull Inventory getInventory() {
        INVENTORY = Bukkit.createInventory(this, 27);
        setItem(Material.IRON_LEGGINGS, CodeList.C_SNEAKING);
        return INVENTORY;
    }
}
