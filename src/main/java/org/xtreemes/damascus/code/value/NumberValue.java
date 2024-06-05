package org.xtreemes.damascus.code.value;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.xtreemes.damascus.Damascus;

public class NumberValue extends Value {
    private float VALUE = 0;
    @Override
    public String getAsString() {
        return String.valueOf(VALUE);
    }

    @Override
    public NumberValue setFromString(String value) {
        try {
            VALUE = Float.parseFloat(value);
        } catch (NumberFormatException | NullPointerException e) {
            VALUE = 0f;
        }
        return this;
    }

    @Override
    public ItemStack getAsItem() {
        ItemStack item = new ItemStack(Material.REDSTONE);
        ItemMeta item_meta = item.getItemMeta();
        PersistentDataContainer pdc = item_meta.getPersistentDataContainer();
        pdc.set(NamespacedKey.fromString("value_type", Damascus.PLUGIN), PersistentDataType.STRING, "NUMBER");
        pdc.set(NamespacedKey.fromString("value", Damascus.PLUGIN), PersistentDataType.STRING, getAsString());
        item_meta.displayName(Component.text(VALUE, Style.empty().color(ValueType.NUMBER.getColor()).decoration(TextDecoration.ITALIC, false)));
        item.setItemMeta(item_meta);
        return item;
    }

    public float getNumber(){
        return VALUE;
    }
}
