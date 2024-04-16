package org.xtreemes.damascus.player;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.xtreemes.damascus.Rank;
import org.xtreemes.damascus.code.TriggerType;
import org.xtreemes.damascus.world.WorldDispatcher;

import java.util.HashMap;

public class PlayerInfo {
    private static final HashMap<Player, Rank> PLAYER_RANKS = new HashMap<>();
    private static final HashMap<Player, PlayerMode> PLAYER_MODES = new HashMap<>();
    private static final HashMap<Player, String> PLAYER_LOCATIONS = new HashMap<>();

    // Ranks
    public static void setRank(Player p, Rank r) {
        PLAYER_RANKS.put(p, r);
        Component formatted_name = r.getFormattedPrefix().append(Component.text((r == Rank.DEFAULT ? "" : " ") + p.getName(), NamedTextColor.WHITE));
        p.playerListName(formatted_name);
        p.customName(formatted_name);
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(r.getSort()+r.name()).addPlayer(p);
    }
    public static Rank getRank(Player p){return PLAYER_RANKS.get(p);}

    // Location (ID)
    public static void setLocation(Player p, String id){
        PLAYER_LOCATIONS.put(p, id);
    }
    public static String getLocation(Player p){
        return PLAYER_LOCATIONS.getOrDefault(p, "lobby");
    }

    // Modes
    public static void setMode(Player p, PlayerMode m){
        PLAYER_MODES.put(p, m);
        if(m == PlayerMode.LOBBY){return;}
        sendMessage(p, "You are now in " + m.name() + " mode.", MessageType.SUCCESS);
        if(m == PlayerMode.PLAY){
            WorldDispatcher.getWorld(PLAYER_LOCATIONS.get(p)).trigger(TriggerType.JOIN, p);
        }
    }
    public static PlayerMode getMode(Player p){return PLAYER_MODES.get(p);}


    public static void sendMessage(Player p, String s, MessageType t){
        switch(t){
            case SUCCESS -> p.sendMessage(
                    Component.text("\u2714",NamedTextColor.GREEN)
                            .append(Component.text(" "+s,NamedTextColor.GRAY))
            );
            case ERROR -> {
                p.sendMessage(
                        Component.text("\u2718", NamedTextColor.RED)
                                .append(Component.text(" " + s, NamedTextColor.GRAY))
                );
                p.playSound(Sound.sound(Key.key("entity.item_frame.remove_item"),Sound.Source.MASTER,1.0f,1.0f));
            }
            case INFO -> p.sendMessage(
                    Component.text("\u2139", TextColor.fromHexString("#0ab6ff"))
                            .append(Component.text(" "+s,NamedTextColor.GRAY))
            );
        }
    }
    public enum MessageType {
        SUCCESS,
        INFO,
        ERROR
    }
}
