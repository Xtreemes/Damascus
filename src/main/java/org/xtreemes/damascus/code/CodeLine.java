package org.xtreemes.damascus.code;

import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xtreemes.damascus.code.block.Nestable;
import org.xtreemes.damascus.code.block.trigger.Trigger;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CodeLine {

    private final ArrayList<Location> BLOCKS = new ArrayList<>();
    private final ArrayList<CodeBlock> CODE = new ArrayList<>();
    private final Location LINE_STARTER;

    public CodeLine(Location line_start){
        this.LINE_STARTER = line_start;
    }

    public CodeLine(Location line_start, JSONArray json){
        this.LINE_STARTER = line_start;
        for(Object o : json){
            JSONObject j = (JSONObject) o;
            CodeBlock cb = CodeList.valueOf((String) j.get("code")).getCodeBlock();
            Object nest_check = j.get("nested");
            if(nest_check != null){
                JSONArray json_array = (JSONArray) nest_check;
                ((Nestable) cb).addJsonArray(json_array);
            }
            CODE.add(cb);
        }
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

    public void runCode(RunInfo info){
        for(CodeBlock c : CODE){
            c.run(info);
        }
    }

    public Material updateBlocks(@Nullable Location loc){
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
        if(loc != null) {
            if (BLOCKS.contains(loc)) {
                give_back = loc.getBlock().getType();
            }
        }
        return give_back;
    }
    public boolean isTrigger(TriggerType t){
        CodeBlock c = CODE.get(0);
        if(c instanceof Trigger trigger){
            return trigger.getTrigger() == t;
        }
        return false;
    }

    public JSONObject getCodeText(){
        JSONObject code_line = new JSONObject();
        JSONArray array = new JSONArray();
        String loc_string = LINE_STARTER.x() + "," + LINE_STARTER.y() + "," + LINE_STARTER.z();
        code_line.put("location",loc_string);
        for(CodeBlock c : CODE){
            array.add(c.getJson());
        }
        code_line.put("code", array);
        return code_line;
    }
}
