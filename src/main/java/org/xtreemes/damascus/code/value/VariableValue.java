package org.xtreemes.damascus.code.value;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.xtreemes.damascus.Damascus;

public class VariableValue extends Value {
    private String NAME;
    @Override
    public String getAsString() {
        return NAME;
    }

    @Override
    public Value setFromString(String value) {
        NAME = value;
        return this;
    }

    @Override
    public ItemStack getAsItem() {
        ItemStack item = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta item_meta = item.getItemMeta();
        PersistentDataContainer pdc = item_meta.getPersistentDataContainer();
        pdc.set(NamespacedKey.fromString("value_type", Damascus.PLUGIN), PersistentDataType.STRING, "VARIABLE");
        pdc.set(NamespacedKey.fromString("variable", Damascus.PLUGIN), PersistentDataType.STRING, getAsString());
        item_meta.displayName(Component.text(NAME, Style.empty().color(ValueType.VARIABLE.getColor()).decoration(TextDecoration.ITALIC, false)));
        item.setItemMeta(item_meta);
        return item;
    }
}
