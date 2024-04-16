package org.xtreemes.damascus.code.block;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.xtreemes.damascus.code.CodeBlock;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Nestable extends MidLine {
    public ArrayList<CodeBlock> CODE = new ArrayList<>();
    public int getSize(){
        int sum = 1;
        for(CodeBlock c : CODE){
            sum++;
            if(c instanceof Nestable n){
                sum += n.getSize();
            }
        }
        return sum;
    }
    @Override
    public void run(@Nullable Entity target) {

    }

    @Override
    public Material getSign(){return Material.CHERRY_WALL_SIGN;}
    @Override
    public Material getConnector(){
        return Material.PINK_CONCRETE;
    }

    @Override
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
        origin.add(0,1,0);
        origin.getBlock().setType(getConnector());
        locs.add(origin.clone());
        origin.add(0,0,1);
        for(CodeBlock c : CODE){
            ArrayList<Location> append = c.placeBlocks(origin);
            locs.addAll(append);
        }

        origin.add(0,0,1);
        origin.getBlock().setType(getConnector());
        locs.add(origin.clone());
        origin.add(0,-1,0);
        origin.getBlock().setType(getConnector());
        locs.add(origin.clone());

        origin.add(0,0,1);
        return locs;
    }

    public int addCodeAtIndex(AtomicInteger current_index, int index, CodeBlock code_to_add){
        if(index == current_index.intValue()){
            return 1;
        }
        current_index.incrementAndGet();
        int code_index = 0;
        for(CodeBlock c : CODE){
            if(c instanceof Nestable nest){
                int result = nest.addCodeAtIndex(current_index, index, code_to_add);
                if(result == 1){
                    CODE.add(code_index, code_to_add);
                    return 2;
                } else if(result == 2){
                    return result;
                } else if(result == 3){
                    CODE.add(code_index+1, code_to_add);
                    return 2;
                }
            } else {
                if(c.addCodeAtIndex(current_index, index) == 1){
                    CODE.add(code_index, code_to_add);
                    return 2;
                }
            }
            code_index++;
        }
        if(index == current_index.intValue()){
            CODE.add(code_to_add);
            return 2;
        } else if(index == current_index.intValue()+1) {
            return 3;
        }
        current_index.incrementAndGet();
        return 0;
    }
    public int codeHasIndex(AtomicInteger current_index, int index){
        if(index == current_index.intValue()){
            return 1;
        }
        current_index.incrementAndGet();
        int code_index = 0;
        for(CodeBlock c : CODE){
            if(c instanceof Nestable nest){
                int result = nest.codeHasIndex(current_index, index);
                if(result == 1){
                    CODE.remove(code_index);
                    return 2;
                } else if(result == 2){
                    return result;
                } else if(result == 3){
                    CODE.remove(CODE.size()-1);
                    return 2;
                }
            } else {
                if(c.addCodeAtIndex(current_index, index) == 1){
                    CODE.remove(code_index);
                    return 2;
                }
            }
            code_index++;
        }
        code_index--;
        if(index == current_index.intValue()){
            CODE.remove(code_index);
            return 2;
        } else if(index == current_index.intValue()+1) {
            return 3;
        }
        current_index.incrementAndGet();
        return 0;
    }
}
