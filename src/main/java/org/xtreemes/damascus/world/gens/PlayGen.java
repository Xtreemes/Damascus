package org.xtreemes.damascus.world.gens;

import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class PlayGen extends ChunkGenerator {

    private int PLAY_SIZE;

    public PlayGen(int size){
        this.PLAY_SIZE = size;
    }

    @Override
    public int getBaseHeight(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull HeightMap heightMap) {
        return 50;
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        if(chunkX < PLAY_SIZE && chunkX >= 0 && chunkZ < PLAY_SIZE && chunkZ >= 0) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {

                    Material material = Material.GRASS_BLOCK;

                    chunkData.setBlock(x, 49, z, material);
                    for (int y = chunkData.getMinHeight() + 1; y < 49; y++) {
                        if (y > 45) {
                            chunkData.setBlock(x, y, z, Material.DIRT);
                        } else {
                            chunkData.setBlock(x, y, z, Material.STONE);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        if(chunkX < PLAY_SIZE && chunkX >= 0 && chunkZ < PLAY_SIZE && chunkZ >= 0) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunkData.setBlock(x, chunkData.getMinHeight(), z, Material.BEDROCK);
                }
            }
        }
    }
}
