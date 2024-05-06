package org.xtreemes.damascus.player.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.xtreemes.damascus.Damascus;
import org.xtreemes.damascus.Rank;
import org.xtreemes.damascus.databse.DatabaseHandler;
import org.xtreemes.damascus.player.PlayerInfo;
import org.xtreemes.damascus.player.PlayerMode;
import org.xtreemes.damascus.world.WorldDispatcher;


public class JoinListener implements Listener {
    @EventHandler
    private static void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        DatabaseHandler.addPlayer(player.getUniqueId().toString());

        Rank rank;
        if(Damascus.DB_STATUS){
            rank = DatabaseHandler.getPlayerRank(player.getUniqueId().toString());
        } else {
            rank = Rank.DEFAULT;
        }
        if(rank != Rank.DEFAULT){
            player.playerListName(rank.getFormattedPrefix().append(Component.text(" " + player.getName(), NamedTextColor.WHITE)));
        }

        PlayerInfo.setMode(player, PlayerMode.LOBBY);
        PlayerInfo.setRank(player, rank);
        for(Player p:player.getWorld().getPlayers()){
            p.sendMessage(Component.text(player.getName() + " joined", NamedTextColor.GRAY));
        }
        e.joinMessage(null);
        player.teleport(WorldDispatcher.getLobby().getSpawnLocation());
        if((!Damascus.DB_STATUS) && player.isOp()){
            PlayerInfo.sendMessage(player, "The database failed to connect!", PlayerInfo.MessageType.ERROR);
        }
    }

    @EventHandler
    private static void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        for(Player p:player.getWorld().getPlayers()){
            p.sendMessage(Component.text(player.getName() + " left", NamedTextColor.GRAY));
        }
        e.quitMessage(null);
    }
}
