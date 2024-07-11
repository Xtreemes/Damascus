package org.xtreemes.damascus.code.value;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.xtreemes.damascus.Damascus;

import java.util.Collections;

public class VariableValue extends Value {
    private String NAME;
    private String SCOPE;
    @Override
    public String getAsString() {
        return SCOPE + ":" + NAME;
    }

    @Override
    public Value setFromString(String value) {
        String[] s = value.split(":",2);
        SCOPE = s[0];
        if(s.length > 1){
            NAME = s[1];
        } else {
            NAME = "";
        }
        return this;
    }

    @Override
    public ItemStack getAsItem() {
        ItemStack item = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta item_meta = item.getItemMeta();
        PersistentDataContainer pdc = item_meta.getPersistentDataContainer();
        pdc.set(NamespacedKey.fromString("value_type", Damascus.PLUGIN), PersistentDataType.STRING, "VARIABLE");
        pdc.set(NamespacedKey.fromString("variable", Damascus.PLUGIN), PersistentDataType.STRING, getAsString());

        TextColor color = NamedTextColor.YELLOW;
        if(SCOPE.equals("LOCAL")){
            color = TextColor.color(0x94FFBE);
        }
        item_meta.lore(Collections.singletonList(Component.text(SCOPE, color).decoration(TextDecoration.ITALIC,false)));

        item_meta.displayName(Component.text(NAME, Style.empty().color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
        item.setItemMeta(item_meta);
        return item;
    }
}
