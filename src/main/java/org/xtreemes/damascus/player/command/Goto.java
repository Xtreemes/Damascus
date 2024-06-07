package org.xtreemes.damascus.player.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.xtreemes.damascus.player.PlayerMode;
import org.xtreemes.damascus.world.WorldDispatcher;

@RegisterCommand("goto")
public class Goto implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        WorldDispatcher.sendToWorld(args[0], p, PlayerMode.valueOf(args[1].toUpperCase()));
        p.sendMessage("Sending you to " + args[0] + " in " + args[1]);
        return true;
    }
}
