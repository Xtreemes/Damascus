package org.xtreemes.damascus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.xtreemes.damascus.code.CodeItemsInfo;
import org.xtreemes.damascus.databse.DatabaseHandler;
import org.xtreemes.damascus.player.command.*;
import org.xtreemes.damascus.player.listener.ChatListener;
import org.xtreemes.damascus.player.listener.CodeActionListener;
import org.xtreemes.damascus.player.listener.JoinListener;
import org.xtreemes.damascus.world.DamascusWorld;
import org.xtreemes.damascus.world.WorldDispatcher;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class Damascus extends JavaPlugin {


    public static boolean DB_STATUS;
    public static Plugin PLUGIN;
    public static ItemStack ITEM_MY_PLOTS = formatItem(Material.DIAMOND, Component.text("My Plots", NamedTextColor.AQUA));

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

        registerCommands();

        getLogger().warning("hello world :3");

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
    private static ItemStack formatItem(Material material, Component name){
        ItemStack item = new ItemStack(material);
        ItemMeta im = item.getItemMeta();
        im.displayName(name.decoration(TextDecoration.ITALIC, false));
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        pdc.set(NamespacedKey.fromString("item_instance", PLUGIN), PersistentDataType.STRING,UUID.randomUUID().toString());
        item.setItemMeta(im);
        return item;
    }
    private void registerCommands(){
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(this.getClass().getPackage().getName()))
                .setScanners(new TypeAnnotationsScanner(), new SubTypesScanner())
        );
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(RegisterCommand.class);
        for(Class<?> clazz : classes){
            if(CommandExecutor.class.isAssignableFrom(clazz)){
                RegisterCommand rc = clazz.getAnnotation(RegisterCommand.class);
                String[] command_names = rc.value();
                for(String command_name : command_names) {
                    try {
                        CommandExecutor command = (CommandExecutor) clazz.getDeclaredConstructor().newInstance();
                        this.getCommand(command_name).setExecutor(command);
                    } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                             NoSuchMethodException e) {
                        System.out.println("uh oh");
                    }
                }
            }
        }
    }
}
