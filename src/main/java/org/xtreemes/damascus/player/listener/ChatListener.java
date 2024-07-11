package org.xtreemes.damascus.player.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.xtreemes.damascus.Damascus;
import org.xtreemes.damascus.Rank;
import org.xtreemes.damascus.code.value.ValueType;
import org.xtreemes.damascus.player.PlayerInfo;
import org.xtreemes.damascus.player.PlayerMode;
import org.xtreemes.damascus.world.WorldDispatcher;

public class ChatListener implements Listener {

    @EventHandler
    private static void onChat(AsyncChatEvent e){
        Player player = e.getPlayer();
        Component prefix = Component.empty();
        e.setCancelled(true);

        if(PlayerInfo.getMode(player) == PlayerMode.DEV){
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() != Material.AIR){
                ItemMeta im = item.getItemMeta();
                PersistentDataContainer pdc = im.getPersistentDataContainer();
                String value_type = pdc.get(NamespacedKey.fromString("value_type", Damascus.PLUGIN), PersistentDataType.STRING);
                if(value_type != null){
                    String plain = PlainTextComponentSerializer.plainText().serialize(e.message());
                    NamespacedKey var_check = NamespacedKey.fromString("variable",Damascus.PLUGIN);
                    if(pdc.has(var_check)){
                        plain = pdc.get(var_check, PersistentDataType.STRING).split(":")[0] + ":" + plain;
                    }
                    item = ValueType.valueOf(value_type).getValue().setFromString(plain).getAsItem();
                    player.getInventory().setItemInMainHand(item);
                    return;
                }
            }
        }

        Rank rank = PlayerInfo.getRank(player);
        if(rank != Rank.DEFAULT){
            HoverEvent<Component> hover = HoverEvent.showText(Component.text(
                    Character.toString(rank.name().charAt(0)).toUpperCase() + rank.name().substring(1).toLowerCase(), rank.getTextColor()
            ));
            prefix = Component.empty().append(rank.getFormattedPrefix()
                    .hoverEvent(hover))
                    .append(
                            Component.text(" ", Style.empty().color(NamedTextColor.WHITE)).hoverEvent(null));
        }
        Component message = prefix.append(Component.text(player.getName() + ": ", Style.empty().color(NamedTextColor.WHITE)).append(e.message()));
        String og_loc = PlayerInfo.getLocation(player);

        Component away_message = Component.text("\u25C7",Style.empty().color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false));
        Component present_message = Component.text("\u25C6",Style.empty().color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false));

        HoverEvent<Component> hover = HoverEvent.showText(Component.text(og_loc, Style.empty().decoration(TextDecoration.ITALIC,false).color(NamedTextColor.GRAY)));

        away_message = Component.empty().append(away_message.hoverEvent(hover).append(Component.text(" ")).append(message));
        present_message = Component.empty().append(present_message.hoverEvent(hover)).append(Component.text(" ").append(message));

        for(Player p : Bukkit.getOnlinePlayers()){
            String location = PlayerInfo.getLocation(p);
            if(location.equals(og_loc)){
                p.sendMessage(present_message);
            } else {
                p.sendMessage(away_message);
            }
        }
    }

    @EventHandler
    private static void temp(PlayerDropItemEvent e){
        e.setCancelled(true);
        Player player = e.getPlayer();
        WorldDispatcher.sendToWorld(player.getName(), player, PlayerMode.DEV);
    }
}
