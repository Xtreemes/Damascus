package org.xtreemes.damascus.player.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.xtreemes.damascus.Rank;
import org.xtreemes.damascus.databse.DatabaseHandler;
import org.xtreemes.damascus.player.PlayerInfo;

public class Setrank implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = sender.getServer().getPlayer(sender.getName());
        try {
            Rank rank = Rank.valueOf(args[0]);
            if(!DatabaseHandler.setPlayerRank(player.getUniqueId().toString(), rank)){
                PlayerInfo.sendMessage(player, "There was an issue setting the rank in the database.", PlayerInfo.MessageType.ERROR);
                return true;
            }
            PlayerInfo.setRank(player, rank);
            PlayerInfo.sendMessage(player, "Set rank successfully.", PlayerInfo.MessageType.SUCCESS);
        } catch (IllegalArgumentException e){
            PlayerInfo.sendMessage(player, "Rank not found!", PlayerInfo.MessageType.ERROR);
        }
        return true;
    }
}
