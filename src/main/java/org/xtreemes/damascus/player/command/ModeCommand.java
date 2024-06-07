package org.xtreemes.damascus.player.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.xtreemes.damascus.player.PlayerInfo;
import org.xtreemes.damascus.player.PlayerMode;
import org.xtreemes.damascus.world.WorldDispatcher;

@RegisterCommand({"dev","build","play"})
public class ModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        if(PlayerInfo.getLocation(p).equals("lobby")){
            PlayerInfo.sendMessage(p, "You must be on a world!", PlayerInfo.MessageType.ERROR);
            return true;
        }
        switch(command.getName()){
            case "dev" -> WorldDispatcher.sendToWorld(PlayerInfo.getLocation(p), p, PlayerMode.DEV);
            case "build" -> WorldDispatcher.sendToWorld(PlayerInfo.getLocation(p), p, PlayerMode.BUILD);
            case "play" -> WorldDispatcher.sendToWorld(PlayerInfo.getLocation(p), p, PlayerMode.PLAY);
        }
        return true;
    }
}
