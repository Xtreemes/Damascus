package org.xtreemes.damascus.world.gens;

import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DevGen extends ChunkGenerator {
    private final int DEV_SIZE = 4;


    @Override
    public int getBaseHeight(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull HeightMap heightMap) {
        return 50;
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        if(chunkX < DEV_SIZE && chunkX >= 0 && chunkZ < DEV_SIZE*6 && chunkZ >= 0) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Material material = Material.WHITE_STAINED_GLASS;
                    chunkData.setBlock(x, chunkData.getMinHeight(), z, material);
                }
            }
        }
    }
    @Override
    public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {

    }
}
