package org.xtreemes.damascus.player.listener;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.xtreemes.damascus.Damascus;
import org.xtreemes.damascus.code.CodeBlock;
import org.xtreemes.damascus.code.CodeLine;
import org.xtreemes.damascus.code.block.empty.EmptyAction;
import org.xtreemes.damascus.code.block.empty.EmptyConditional;
import org.xtreemes.damascus.code.block.empty.EmptyStarter;
import org.xtreemes.damascus.code.block.empty.EmptyTest;
import org.xtreemes.damascus.code.block.trigger.JoinTrigger;
import org.xtreemes.damascus.player.PlayerInfo;
import org.xtreemes.damascus.player.PlayerMode;
import org.xtreemes.damascus.player.inventory.CodeItemsInvHolder;
import org.xtreemes.damascus.world.DamascusWorld;
import org.xtreemes.damascus.world.WorldDispatcher;

import java.util.List;

public class CodeActionListener implements Listener {
    public static List<Material> CODE_BLOCK_MATERIALS = List.of(new Material[]{Material.CYAN_CONCRETE, Material.YELLOW_CONCRETE,
    Material.PINK_CONCRETE, Material.WHITE_CONCRETE});
    public static List<Material> LINE_STARTER_MATERIALS = List.of(new Material[]{Material.CYAN_CONCRETE});

    @EventHandler
    private void interactEvent(PlayerInteractEvent e){
        Action action = e.getAction();
        Player player = e.getPlayer();

        // Right Click
        if(action.isRightClick()){
            ItemStack item = e.getItem();
            ItemMeta item_meta = item.getItemMeta();
            PersistentDataContainer pdc = item_meta.getPersistentDataContainer();

            if(Boolean.TRUE.equals(pdc.get(NamespacedKey.fromString("code_item_container", Damascus.PLUGIN), PersistentDataType.BOOLEAN))){
                player.openInventory(new CodeItemsInvHolder().getInventory());
            }
        }
        // Left Click
        else if (action.isLeftClick()) {

        }
    }

    @EventHandler
    private void placeBlockEvent(BlockPlaceEvent e){
        Block block = e.getBlock();
        Player player = e.getPlayer();

        if(PlayerInfo.getMode(player) == PlayerMode.DEV){
            e.setCancelled(true);
            Material mat = block.getType();
            if(CODE_BLOCK_MATERIALS.contains(mat)){
                CodeLine cl = WorldDispatcher.getWorld(PlayerInfo.getLocation(player)).getAndAddCodeLine(block.getLocation(), mat);
                if(cl != null){
                    CodeBlock test = new EmptyAction();
                    if(mat == Material.PINK_CONCRETE){
                        test = new EmptyConditional();
                    } else if (mat == Material.WHITE_CONCRETE){
                        test = new EmptyTest();
                    }
                    cl.addCode(test, cl.getIndex(block.getLocation()));
                    e.setCancelled(false);
                    e.setBuild(true);
                    Material update = cl.updateBlocks(block.getLocation());
                    e.getBlock().setType(update);
                } else {
                    PlayerInfo.sendMessage(player, "Invalid code block placement!", PlayerInfo.MessageType.ERROR);
                }
            } else {
                PlayerInfo.sendMessage(player, "Invalid code block placement!", PlayerInfo.MessageType.ERROR);
            }
        }
    }

    @EventHandler
    private void breakBlockEvent(BlockBreakEvent e){
        Block block = e.getBlock();
        Player player = e.getPlayer();

        if(PlayerInfo.getMode(player) == PlayerMode.DEV){
            e.setCancelled(true);
            Material mat = block.getType();
            if(mat == Material.BARREL){
                DamascusWorld world = WorldDispatcher.getWorld(PlayerInfo.getLocation(player));
                CodeLine cl = world.getCodeLine(block.getLocation());
                if(cl != null) {
                    boolean delete = cl.removeCode(cl.getIndex(block.getLocation()));
                    Material update = cl.updateBlocks(block.getLocation());
                    e.getBlock().setType(update);
                    if(delete){
                        world.removeCodeLine(cl);
                    }
                }
            }
        }
    }
}
