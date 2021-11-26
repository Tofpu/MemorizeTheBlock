package io.tofpu.memorizetheblock.director.wrapper;

import io.tofpu.memorizetheblock.director.GameDirector;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public final class GameLocation {
    private Location location;

    public void initialize(final GameDirector director, final World world) {
        if (location != null) throw new IllegalStateException("You cannot reinitialize the game's location twice.");
        this.location = new Location(world, 98 + (director.logicProcessor().players().size() * 8), 100, 100, -180, 2);
        this.location.clone().add(0, -1, 0).getBlock().setType(Material.WOOL);
    }

    public Location location() {
        return this.location;
    }
}
