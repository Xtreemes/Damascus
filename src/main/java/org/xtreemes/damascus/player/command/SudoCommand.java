package org.xtreemes.damascus.player.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.xtreemes.damascus.player.PlayerInfo;

@RegisterCommand("sudo")
public class SudoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            PlayerInfo.sendMessage(p, "Player not found!", PlayerInfo.MessageType.ERROR);
            return true;
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < args.length; i++){
            builder.append(args[i]).append(" ");
        }
        String cmd = builder.toString().trim();
        target.chat(cmd);
        return true;
    }
}
