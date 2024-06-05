package org.xtreemes.damascus.player.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class CodeItemsInvHolder implements InventoryHolder {
    private Inventory INVENTORY;
    @Override
    public @NotNull Inventory getInventory() {
        this.INVENTORY = Bukkit.createInventory(this,27, Component.text("Code Blocks"));
        this.INVENTORY.addItem(
                // Player Event
                formatItem(Material.CYAN_CONCRETE, "Trigger", NamedTextColor.DARK_AQUA),
                formatItem(Material.YELLOW_CONCRETE, "Action", NamedTextColor.YELLOW),
                formatItem(Material.PINK_CONCRETE, "Conditional", NamedTextColor.LIGHT_PURPLE),
                formatItem(Material.ORANGE_CONCRETE, "World", NamedTextColor.GOLD)
        );
        return this.INVENTORY;
    }
    private static ItemStack formatItem(Material material, String name, TextColor color){
        ItemStack item = new ItemStack(material);
        ItemMeta im = item.getItemMeta();
        im.displayName(Component.text(name, Style.empty().color(color).decoration(TextDecoration.ITALIC, false)));
        item.setItemMeta(im);
        return item;
    }
}
