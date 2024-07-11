package org.xtreemes.damascus.code.value;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class SelectionValue extends Value {
    private final ArrayList<UUID> SELECTION = new ArrayList<>();
    @Override
    public String getAsString() {
        StringBuilder s = new StringBuilder();
        for(UUID uuid : SELECTION){
            s.append(uuid.toString());
        }
        return s.toString();
    }

    @Override
    public Value setFromString(String value) {
        for(String s : value.split(",")){
            SELECTION.add(UUID.fromString(s));
        }
        return this;
    }

    @Override
    public ItemStack getAsItem() {
        return new ItemStack(Material.AIR);
    }
    public void addEntity(Entity entity){
        SELECTION.add(entity.getUniqueId());
    }
    public void removeEntity(Entity entity){
        UUID uuid = entity.getUniqueId();
        SELECTION.remove(uuid);
    }
    public Entity[] getEntities(){
        ArrayList<Entity> entities = new ArrayList<>();
        for(UUID uuid : SELECTION){
            Entity e = Bukkit.getEntity(uuid);
            if(e != null){
                entities.add(e);
            }
        }
        return entities.toArray(Entity[]::new);
    }
}
