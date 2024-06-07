package org.xtreemes.damascus.code;

import org.bukkit.Material;
import org.xtreemes.damascus.code.block.empty.EmptyAction;
import org.xtreemes.damascus.code.block.empty.EmptyConditional;
import org.xtreemes.damascus.code.block.empty.EmptyTrigger;
import org.xtreemes.damascus.code.block.variable.SetVariableAction;
import org.xtreemes.damascus.code.block.world.WaitWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CodeItemsInfo {
    public static ArrayList<Material> MATERIALS = new ArrayList<>();
    public static ArrayList<Material> LINE_START = new ArrayList<>();
    private static final HashMap<Material, CodeBlock> MAT_TO_CLASS = new HashMap<>();
    public static void initialize(){

        MAT_TO_CLASS.put(Material.CYAN_CONCRETE, new EmptyTrigger());
        MAT_TO_CLASS.put(Material.YELLOW_CONCRETE, new EmptyAction());
        MAT_TO_CLASS.put(Material.PINK_CONCRETE, new EmptyConditional());
        MAT_TO_CLASS.put(Material.ORANGE_CONCRETE, new WaitWorld());
        MAT_TO_CLASS.put(Material.WHITE_CONCRETE, new SetVariableAction());

        MATERIALS = new ArrayList<>(MAT_TO_CLASS.keySet());

        LINE_START.add(Material.CYAN_CONCRETE);
    }
    public static CodeBlock getCodeblock(Material mat){
        return MAT_TO_CLASS.get(mat).clone();
    }
}
