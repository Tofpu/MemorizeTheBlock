package me.tofpu.memorizetheblock.game.object;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ChosenBlock {
    private final Location location;
    private final Block block;

    public ChosenBlock(final Location location) {
        this.location = location;
        this.block = location.clone().add(0, 1, 0).getBlock();

        this.location.getBlock().setType(Material.WOOL);
    }

    public void block(final Material material) {
        block.setType(material);
    }

    public Block block() {
        return this.block;
    }

    public Location location() {
        return this.location;
    }
}
