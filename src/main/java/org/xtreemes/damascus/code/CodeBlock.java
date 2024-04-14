package org.xtreemes.damascus.code;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class CodeBlock {

    //TODO: add barrel-params as a param
    abstract public void run(@Nullable Entity target);

    public Material getSign(){
        return Material.OAK_WALL_SIGN;
    }
    public Material getConnector(){
        return Material.SPRUCE_PLANKS;
    }

    // Edits the origin directly (Reference) so no extra value needs to return
    public ArrayList<Location> placeBlocks(Location origin){
         ArrayList<Location> locs = new ArrayList<>();
         locs.add(origin.clone());
         origin.getBlock().setType(Material.BARREL);

         origin.add(-1, 0, 0);
         Block block = origin.getBlock();
         block.setType(getSign());
         BlockData bd = block.getBlockData();
         if(bd instanceof Directional directional){
             directional.setFacing(BlockFace.WEST);
             block.setBlockData(directional);
         }
         locs.add(origin.clone());
         origin.add(1, 0, 0);

         origin.add(0,0,1);
         origin.getBlock().setType(getConnector());
         locs.add(origin.clone());

         origin.add(0,0,1);
         return locs;
    }
    // 1 yes, 0 no, 2 nested success
    public int addCodeAtIndex(AtomicInteger current_index, int index){
        if(index == current_index.intValue()){
            return 1;
        }
        current_index.incrementAndGet();
        return 0;
    }
    public int codeHasIndex(AtomicInteger current_index, int index){
        if(index == current_index.intValue()){
            return 1;
        }
        current_index.incrementAndGet();
        return 0;
    }
}
