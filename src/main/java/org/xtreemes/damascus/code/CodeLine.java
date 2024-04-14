package org.xtreemes.damascus.code;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.xtreemes.damascus.code.block.Nestable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CodeLine {

    private ArrayList<Location> BLOCKS = new ArrayList<>();
    private ArrayList<CodeBlock> CODE = new ArrayList<>();
    private Location LINE_STARTER;

    public CodeLine(Location line_start){
        this.LINE_STARTER = line_start;
    }

    public Location getLineStart(){
        return this.LINE_STARTER;
    }
    public void addCode(CodeBlock c, int insert){
        AtomicInteger current_index = new AtomicInteger(0);
        int code_index = 0;
        for(CodeBlock d : CODE){
            if(d instanceof Nestable nest){
                // If it's nestable, add to the Nestable's CODE value.
                int result = nest.addCodeAtIndex(current_index, insert, c);
                if(result == 1){
                    CODE.add(code_index, c);
                    return;
                } else if(result == 2){
                    return;
                } else if(result == 3){
                    CODE.add(code_index+1, c);
                    return;
                }
            } else {
                if(d.addCodeAtIndex(current_index, insert) == 1){
                    CODE.add(code_index, c);
                    return;
                }
            }
            code_index++;
        }
        CODE.add(c);
    }
    public boolean removeCode(int remove){
        AtomicInteger current_index = new AtomicInteger(0);
        int code_index = 0;
        for(CodeBlock d : CODE){
            if(d instanceof Nestable nest){
                // If it's nestable, add to the Nestable's CODE value.
                int result = nest.codeHasIndex(current_index, remove);
                if(result == 1){
                    CODE.remove(code_index);
                    break;
                } else if(result == 2){
                    break;
                } else if(result == 3){
                    CODE.remove(code_index+1);
                    break;
                }
            } else {
                if(d.addCodeAtIndex(current_index, remove) == 1){
                    CODE.remove(code_index);
                    break;
                }
            }
            code_index++;
        }
        return CODE.isEmpty();
    }
    public int getIndex(Location loc){
        int index = (int) Math.round((loc.z() - LINE_STARTER.z())/2);
        int size = 0;
        for(CodeBlock c : CODE){
            size++;
            if(c instanceof Nestable nest){
                size += nest.getSize();
            }
        }
        if(index > size){
            index = size;
        }
        return index;
    }

    public void runCode(@Nullable Entity target){
        for(CodeBlock c : CODE){
            c.run(target);
        }
    }

    public Material updateBlocks(Location loc){
        Material give_back = Material.AIR;
        for(Location b: BLOCKS){
            b.getBlock().setType(Material.AIR, false);
        }
        BLOCKS.clear();
        Location current = LINE_STARTER.clone();
        for(CodeBlock c : CODE){
            ArrayList<Location> tentative = c.placeBlocks(current);
            BLOCKS.addAll(tentative);
        }
        if(BLOCKS.contains(loc)){
            give_back = loc.getBlock().getType();
        }
        return give_back;
    }
}
