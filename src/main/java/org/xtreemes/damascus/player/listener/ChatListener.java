package org.xtreemes.damascus.player.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.xtreemes.damascus.Rank;
import org.xtreemes.damascus.code.block.conditional.Conditional;
import org.xtreemes.damascus.player.PlayerInfo;
import org.xtreemes.damascus.player.PlayerMode;
import org.xtreemes.damascus.world.WorldDispatcher;

public class ChatListener implements Listener {
    @EventHandler
    private static void onChat(AsyncChatEvent e){
        Player player = e.getPlayer();
        Component prefix = Component.empty();
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
        Bukkit.getServer().sendMessage(message);
        e.setCancelled(true);
    }

    @EventHandler
    private static void temp(PlayerDropItemEvent e){
        e.setCancelled(true);
        Player player = e.getPlayer();
        WorldDispatcher.sendToWorld(player.getName(), player, PlayerMode.DEV);
    }
}
