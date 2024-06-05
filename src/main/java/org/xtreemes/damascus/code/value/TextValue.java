package org.xtreemes.damascus.code.value;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.xtreemes.damascus.Damascus;

import java.util.ArrayList;

public class TextValue extends Value {
    private final MiniMessage mm = MiniMessage.miniMessage();
    private Component VALUE = Component.empty();

    @Override
    public String getAsString() {
        return mm.serialize(VALUE);
    }

    @Override
    public TextValue setFromString(String value) {
        VALUE = mm.deserialize(value);
        return this;
    }

    @Override
    public ItemStack getAsItem() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta item_meta = item.getItemMeta();
        PersistentDataContainer pdc = item_meta.getPersistentDataContainer();
        pdc.set(NamespacedKey.fromString("value_type", Damascus.PLUGIN), PersistentDataType.STRING, "TEXT");
        pdc.set(NamespacedKey.fromString("value", Damascus.PLUGIN), PersistentDataType.STRING, getAsString());
        TextComponent text = Component.text("", Style.empty().decoration(TextDecoration.ITALIC, false)).append(VALUE);
        item_meta.displayName(text);
        ArrayList<TextComponent> lore = new ArrayList<>();
        lore.add(Component.text("Value: ", Style.style(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text(mm.serialize(VALUE), Style.style(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
        item_meta.lore(lore);
        item.setItemMeta(item_meta);
        return item;
    }
    public Component getText(){
        return VALUE;
    }
}
