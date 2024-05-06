package org.xtreemes.damascus.world;

import org.bukkit.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xtreemes.damascus.code.CodeItemsInfo;
import org.xtreemes.damascus.code.CodeLine;
import org.xtreemes.damascus.code.RunInfo;
import org.xtreemes.damascus.code.TriggerType;
import org.xtreemes.damascus.world.gens.DevGen;
import org.xtreemes.damascus.world.gens.PlayGen;

import java.util.ArrayList;

public class DamascusWorld {

    private World DEV;
    private World PLAY;
    private final String ID;
    private final ArrayList<CodeLine> CODE_LINES = new ArrayList<>();

    public World getDevWorld() {
        if(DEV == null){
            WorldCreator wc = setupWorldCreator("dev");
            wc.generator(new DevGen());
            World world = Bukkit.createWorld(wc);
            DEV = applyGamerules(world);
        }
        return DEV;
    }
    public World getPlayWorld() {
        if(PLAY == null){
            int size = 16;
            WorldCreator wc = setupWorldCreator("play");
            wc.generator(new PlayGen(size));
            World world = Bukkit.createWorld(wc);
            int middle =  (size * 16) /2;
            WorldBorder wb = world.getWorldBorder();
            wb.setCenter(middle,middle);
            wb.setSize(16*16);
            world.setSpawnLocation(middle, 50, middle);
            PLAY = applyGamerules(world);
        }
        return PLAY;
    }
    public DamascusWorld(String id){
        this.ID = id;
        this.PLAY = getPlayWorld();
        this.DEV = getDevWorld();
    }
    private WorldCreator setupWorldCreator(String name){
        WorldCreator wc = WorldCreator.name("plots/" + this.ID + "/" + name);
        wc.type(WorldType.FLAT);
        wc.generateStructures(false);
        return wc;
    }
    private World applyGamerules(World world){
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setTime(6000L);
        return world;
    }

    public CodeLine getAndAddCodeLine(Location location, Material material){
        boolean contains = CodeItemsInfo.LINE_START.contains(material);
        for(CodeLine cl : CODE_LINES){
            Location loc = cl.getLineStart();
            if(contains){
                if(Math.abs(loc.x() - location.x()) < 3) {
                    return null;
                }
            } else if(loc.x() == location.x() && location.z()>loc.z()) {
                return cl;
            }
        }
        if(contains){
            CodeLine cl = new CodeLine(location);
            CODE_LINES.add(cl);
            return cl;
        }
        return null;
    }
    public CodeLine getCodeLine(Location location){
        for(CodeLine cl : CODE_LINES) {
            Location loc = cl.getLineStart();

            if (loc.x() == location.x() && location.z() >= loc.z()) {
                return cl;
            }
        }
        return null;
    }
    public CodeLine getCodeLine(Location location, Material material){
        boolean contains = CodeItemsInfo.LINE_START.contains(material);
        for(CodeLine cl : CODE_LINES){
            Location loc = cl.getLineStart();
            if(contains){
                if(Math.abs(loc.x() - location.x()) < 3) {
                    return null;
                }
            } else if(loc.x() == location.x() && location.z()>loc.z()) {
                return cl;
            }
        }
        return null;
    }
    public void removeCodeLine(CodeLine c){
        CODE_LINES.remove(c);
    }
    public void trigger(TriggerType t, RunInfo info){
        for(CodeLine cl : CODE_LINES){
            if(cl.isTrigger(t)){
                cl.runCode(info);
                break;
            }
        }
    }
    public void parseJSON(JSONArray json){
        for(Object o : json){
            JSONObject element = (JSONObject) o;
            String loc_string = element.get("location").toString();
            String[] split_loc = loc_string.split(",");
            Location loc = getDevWorld().getSpawnLocation().clone();
            loc.set(Double.parseDouble(split_loc[0]), Double.parseDouble(split_loc[1]), Double.parseDouble(split_loc[2]));
            JSONArray array = (JSONArray) element.get("code");
            CodeLine cl = new CodeLine(loc, array);
            cl.updateBlocks(null);
            CODE_LINES.add(cl);
        }
    }
    public JSONArray getJSON(){
        JSONArray array = new JSONArray();
        for(CodeLine cl : CODE_LINES){
            array.add(cl.getCodeText());
        }
        return array;
    }
}
