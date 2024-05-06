package org.xtreemes.damascus.code;

import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.json.simple.JSONObject;
import org.xtreemes.damascus.code.parameters.Parameters;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class CodeBlock implements Cloneable {

    //TODO: add barrel-params as a param
    protected Parameters PARAMS = new Parameters();
    abstract public void run(RunInfo info);

    public Material getSign(){
        return Material.OAK_WALL_SIGN;
    }
    public DyeColor getSignColour(){
        return DyeColor.WHITE;
    }
    public String getSignMain(){
        return "Placeholder";
    }
    public String getSignSub(){
        return "";
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
         if(block.getState() instanceof Sign s){
             SignSide side = s.getSide(Side.FRONT);
             side.line(0, Component.text(getSignMain().toUpperCase()));
             side.line(2, Component.text(getSignSub()));
             side.setGlowingText(true);
             side.setColor(getSignColour());

             Directional directional = (Directional) s.getBlockData();
             directional.setFacing(BlockFace.WEST);
             s.setBlockData(directional);
             s.update();
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

    public JSONObject getJson(){
        JSONObject json = new JSONObject();
        json.put("code",CodeList.classToEnum(this).name());
        return json;
    }

    @Override
    public CodeBlock clone() {
        try {
            CodeBlock clone = (CodeBlock) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
