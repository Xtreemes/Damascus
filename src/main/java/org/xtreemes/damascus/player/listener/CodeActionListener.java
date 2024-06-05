package org.xtreemes.damascus.player.listener;

import io.papermc.paper.event.player.PlayerOpenSignEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.xtreemes.damascus.Damascus;
import org.xtreemes.damascus.code.*;
import org.xtreemes.damascus.player.PlayerInfo;
import org.xtreemes.damascus.player.PlayerMode;
import org.xtreemes.damascus.player.inventory.CancelClickInv;
import org.xtreemes.damascus.player.inventory.CodeItemsInvHolder;
import org.xtreemes.damascus.player.inventory.code.*;
import org.xtreemes.damascus.world.DamascusWorld;
import org.xtreemes.damascus.world.WorldDispatcher;


public class CodeActionListener implements Listener {

    @EventHandler
    private void interactEvent(PlayerInteractEvent e){
        Action action = e.getAction();
        Player player = e.getPlayer();
        PlayerMode pmode = PlayerInfo.getMode(player);

        // Right Click
        if(action.isRightClick()){
            ItemStack item = e.getItem();
            if(item != null) {
                ItemMeta item_meta = item.getItemMeta();
                PersistentDataContainer pdc = item_meta.getPersistentDataContainer();

                if (pmode == PlayerMode.DEV) {
                    if (Boolean.TRUE.equals(pdc.get(NamespacedKey.fromString("code_item_container", Damascus.PLUGIN), PersistentDataType.BOOLEAN))) {
                        player.openInventory(new CodeItemsInvHolder().getInventory());
                    }
                } else if(pmode == PlayerMode.LOBBY){

                }
            }
            if (pmode == PlayerMode.PLAY) {
                RunInfo run_info = new RunInfo(e, player, false);
                if(WorldDispatcher.getWorld(PlayerInfo.getLocation(player)).trigger(TriggerType.RCLICK, run_info)){
                    e.setCancelled(run_info.shouldCancel().join());
                }
            }
        }
        // Left Click
        else if (action.isLeftClick()) {
            if(pmode == PlayerMode.PLAY){
                RunInfo run_info = new RunInfo(e, player, false);
                if(WorldDispatcher.getWorld(PlayerInfo.getLocation(player)).trigger(TriggerType.LCLICK, run_info)){
                    e.setCancelled(run_info.shouldCancel().join());
                }
            }
        }
    }

    @EventHandler
    private void clickSign(PlayerOpenSignEvent e){
        Player player =  e.getPlayer();
        Block block = e.getSign().getBlock();

        if(PlayerInfo.getMode(player) == PlayerMode.DEV){
            e.setCancelled(true);
            Inventory inv = new CodeItemsInvHolder().getInventory();
            Material mat = block.getLocation().clone().add(1,0,1).getBlock().getType();
            Location code_loc = block.getLocation().clone().add(1,0,0);
            switch(mat){
                case CYAN_CONCRETE -> inv = new TriggerInvHolder(code_loc).getInventory();
                case YELLOW_CONCRETE -> inv = new ActionInvHolder(code_loc).getInventory();
                case PINK_CONCRETE -> inv = new ConditionalInvHolder(code_loc).getInventory();
                case ORANGE_CONCRETE -> inv = new WorldInvHolder(code_loc).getInventory();
            }
            player.openInventory(inv);
        }
    }

    @EventHandler
    private void placeBlockEvent(BlockPlaceEvent e){
        Block block = e.getBlock();
        Player player = e.getPlayer();

        if(PlayerInfo.getMode(player) == PlayerMode.DEV){
            e.setCancelled(true);
            Material mat = block.getType();
            if(CodeItemsInfo.MATERIALS.contains(mat)){
                CodeLine cl = WorldDispatcher.getWorld(PlayerInfo.getLocation(player)).getAndAddCodeLine(block.getLocation(), mat);
                if(cl != null){
                    CodeBlock codeblock = CodeItemsInfo.getCodeblock(mat);
                    cl.addCode(codeblock, cl.getIndex(block.getLocation()));
                    e.setCancelled(false);
                    e.setBuild(true);
                    Material update = cl.updateBlocks(block.getLocation(), false);
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
                    boolean delete = cl.removeCode(cl.getIndex(block.getLocation()), null);
                    Material update = cl.updateBlocks(block.getLocation(), false);
                    e.getBlock().setType(update);
                    if(delete){
                        world.removeCodeLine(cl);
                    }
                }
            }
        }
    }

    @EventHandler
    private void clickInventoryEvent(InventoryClickEvent e){
        Inventory inv = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        InventoryHolder holder = inv.getHolder();
        if(holder instanceof CancelClickInv) {
            e.setCancelled(true);
        }
        if(PlayerInfo.getMode(player) == PlayerMode.DEV){
            if(holder instanceof CodeSelection cs){
                ItemStack item = e.getCurrentItem();
                if(item != null){
                    CodeBlock code = cs.getCode(item);
                    Location loc = cs.getLocation();
                    CodeLine cl = WorldDispatcher.getWorld(PlayerInfo.getLocation(player)).getCodeLine(loc);
                    if(cl != null){
                        int index = cl.getIndex(loc);
                        cl.removeCode(index, code);
                        cl.addCode(code, index);
                        cl.updateBlocks(null, false);
                    }
                    inv.close();
                }
            }
        }
    }

    @EventHandler
    private void closeInventoryEvent(InventoryCloseEvent e){
        Inventory inv = e.getInventory();
        Player p = (Player) e.getPlayer();
        Location loc = inv.getLocation();
        if(PlayerInfo.getMode(p) == PlayerMode.DEV && loc != null) {
            if (loc.getBlock().getType() == Material.BARREL) {
                CodeLine cl = WorldDispatcher.getWorld(PlayerInfo.getLocation(p)).getAndAddCodeLine(loc, null);
                if(cl != null){
                    int index = cl.getIndex(loc);
                    CodeBlock cb = cl.getCodeBlock(index);
                    cb.setBarrelContents(inv.getContents().clone());
                }
            }
        }
    }
    @EventHandler
    private void sneakToggle(PlayerToggleSneakEvent e){
        Player p = e.getPlayer();
        if(PlayerInfo.getMode(p) == PlayerMode.PLAY) {
            if (e.isSneaking()) {
                RunInfo run_info = new RunInfo(e, p, false);
                WorldDispatcher.getWorld(PlayerInfo.getLocation(p)).trigger(TriggerType.SNEAK, run_info);
            }
        }
    }
}
