package org.xtreemes.damascus.player.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.xtreemes.damascus.Rank;
import org.xtreemes.damascus.player.PlayerInfo;

public class Setrank implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = sender.getServer().getPlayer(sender.getName());
        Rank rank = Rank.valueOf(args[0]);
        PlayerInfo.setRank(player, rank);
        return true;
    }
}
