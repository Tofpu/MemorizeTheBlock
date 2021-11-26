package io.tofpu.memorizetheblock.director.object;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class GameBlock {
    private final Location location;
    private final Block block;

    public static GameBlock of(final Location location) {
        return new GameBlock(location);
    }

    private GameBlock(final Location location) {
        this.location = location;
        this.block = location.clone().add(0, 1, 0).getBlock();

        this.location.getBlock().setType(Material.WOOL);
    }

    public void block(final Material material) {
        this.block.setType(material);
    }

    public Block block() {
        return this.block;
    }

    public Location location() {
        return this.location;
    }
}
