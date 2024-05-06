package org.xtreemes.damascus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.xtreemes.damascus.code.CodeItemsInfo;
import org.xtreemes.damascus.databse.DatabaseHandler;
import org.xtreemes.damascus.player.command.Goto;
import org.xtreemes.damascus.player.command.ModeCommand;
import org.xtreemes.damascus.player.command.Setrank;
import org.xtreemes.damascus.player.listener.ChatListener;
import org.xtreemes.damascus.player.listener.CodeActionListener;
import org.xtreemes.damascus.player.listener.JoinListener;
import org.xtreemes.damascus.world.DamascusWorld;
import org.xtreemes.damascus.world.WorldDispatcher;

import java.util.Map;

public final class Damascus extends JavaPlugin {


    public static boolean DB_STATUS;
    public static Plugin PLUGIN;

    @Override
    public void onEnable() {

        DB_STATUS = DatabaseHandler.connect();
        PLUGIN = this;
        // Plugin startup login
        registerListeners(
                new JoinListener(),
                new ChatListener(),
                new CodeActionListener()
        );
        CodeItemsInfo.initialize();

        getCommand("setrank").setExecutor(new Setrank());
        getCommand("goto").setExecutor(new Goto());
        getCommand("dev").setExecutor(new ModeCommand());
        getCommand("build").setExecutor(new ModeCommand());
        getCommand("play").setExecutor(new ModeCommand());

        setupScoreboard();
        getServer().motd(Component
                .text("Damascus", TextColor.color(0xFF6462))
                        .append(Component.text(" INDEV", Style.empty().decorate(TextDecoration.ITALIC).color(NamedTextColor.DARK_GRAY)))
                .append(Component.text("\nmost skibidi df clone!",TextColor.color(0xFFC6B7)))
        );
    }

    @Override
    public void onDisable() {
        for(Map.Entry<String, DamascusWorld> entry : WorldDispatcher.getWorlds().entrySet()){
            String key = entry.getKey();
            DamascusWorld world = entry.getValue();
            DatabaseHandler.setPlotJSON(world.getJSON(),key);
        }
        DatabaseHandler.disconnect();
    }

    private static void registerListeners(Listener... listeners){
        for(Listener listener:listeners) {
            PLUGIN.getServer().getPluginManager().registerEvents(listener, PLUGIN);
        }
    }
    private static void setupScoreboard(){
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        for(Team team : sb.getTeams()){
            team.unregister();
        }
        for(Rank r : Rank.values()){
            Team t = sb.registerNewTeam(r.getSort()+r.name());
            if(r == Rank.DEFAULT){continue;}
            t.prefix(r.getFormattedPrefix().append(Component.text(" ")));
        }
    }
}
