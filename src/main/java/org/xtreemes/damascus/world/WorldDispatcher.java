package org.xtreemes.damascus.world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.json.simple.JSONArray;
import org.xtreemes.damascus.Damascus;
import org.xtreemes.damascus.databse.DatabaseHandler;
import org.xtreemes.damascus.player.PlayerInfo;
import org.xtreemes.damascus.player.PlayerMode;

import java.util.HashMap;

public class WorldDispatcher {
    private static final World LOBBY = Bukkit.getWorld("world");
    private static final HashMap<String, DamascusWorld> WORLDS = new HashMap<>();

    public static DamascusWorld getWorld(String id){
        if(WORLDS.containsKey(id)){
            return WORLDS.get(id);
        } else {
            DamascusWorld world = new DamascusWorld(id);
            JSONArray json = DatabaseHandler.getPlotJSON(id);
            if(json != null){
                world.parseJSON(json);
            }
            WORLDS.put(id, world);
            return world;
        }
    }
    public static World getLobby(){return LOBBY;}

    // Player related methods
    public static void sendToWorld(String id, Player p, PlayerMode m){
        ItemStack code_item_container = new ItemStack(Material.SHULKER_SHELL);
        ItemMeta meta = code_item_container.getItemMeta();
        meta.getPersistentDataContainer().set(NamespacedKey.fromString("code_item_container", Damascus.PLUGIN), PersistentDataType.BOOLEAN, true);
        meta.displayName(Component.text("Code Blocks", Style.empty()
                .color(TextColor.color(0xC77BFF))
                .decoration(TextDecoration.ITALIC, false)
        ));
        code_item_container.setItemMeta(meta);

        PlayerInfo.setLocation(p, id);
        PlayerInfo.setMode(p, m);
        if(m == PlayerMode.DEV){
            p.teleport(getWorld(id).getDevWorld().getSpawnLocation());
            p.getInventory().setItem(8, code_item_container);
            p.setGameMode(GameMode.CREATIVE);
        } else if(m == PlayerMode.BUILD){
            p.teleport(getWorld(id).getPlayWorld().getSpawnLocation());
            p.getInventory().setItem(8, code_item_container);
            p.setGameMode(GameMode.CREATIVE);
        } else if(m == PlayerMode.PLAY){
            p.teleport(getWorld(id).getPlayWorld().getSpawnLocation());
            p.getInventory().clear();
            p.setGameMode(GameMode.SURVIVAL);
            p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            p.setSaturation(20);
            p.setFoodLevel(20);
        }
    }
    public static HashMap<String, DamascusWorld> getWorlds(){
        return WORLDS;
    }
}