package org.xtreemes.damascus.code;

import org.bukkit.Material;
import org.xtreemes.damascus.code.block.empty.EmptyAction;
import org.xtreemes.damascus.code.block.empty.EmptyConditional;
import org.xtreemes.damascus.code.block.empty.EmptyTrigger;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeItemsInfo {
    public static ArrayList<Material> MATERIALS = new ArrayList<>();
    public static ArrayList<Material> LINE_START = new ArrayList<>();
    public static void initialize(){

        MATERIALS.add(Material.CYAN_CONCRETE);
        MATERIALS.add(Material.YELLOW_CONCRETE);
        MATERIALS.add(Material.PINK_CONCRETE);

        LINE_START.add(Material.CYAN_CONCRETE);
    }
    public static CodeBlock getCodeblock(Material mat){
        HashMap<Material, CodeBlock> MAT_TO_CLASS = new HashMap<>();

        MAT_TO_CLASS.put(Material.CYAN_CONCRETE, new EmptyTrigger());
        MAT_TO_CLASS.put(Material.YELLOW_CONCRETE, new EmptyAction());
        MAT_TO_CLASS.put(Material.PINK_CONCRETE, new EmptyConditional());
        return MAT_TO_CLASS.get(mat);
    }
}
