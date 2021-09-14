package me.tofpu.memorizetheblock.game.wrapper;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public final class GameWorld {
    private World world;

    public void initialize() {
        if (world != null) throw new IllegalStateException("You cannot reinitialize the game's world twice.");
        this.world = WorldCreator.name("MTB").type(WorldType.FLAT).generatorSettings("2;0;1").createWorld();
    }

    public World world() {
        return this.world;
    }
}
