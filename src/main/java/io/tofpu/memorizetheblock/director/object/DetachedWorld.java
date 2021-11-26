package io.tofpu.memorizetheblock.director.object;

import io.tofpu.memorizetheblock.director.GameDirector;
import io.tofpu.memorizetheblock.director.wrapper.GameLocation;
import io.tofpu.memorizetheblock.director.wrapper.GameWorld;

import java.util.Objects;
import java.util.UUID;

public class DetachedWorld {
    private final UUID mapUniqueId = UUID.randomUUID();
    private final GameDirector director;

    private final GameWorld world;
    private final GameLocation location;
    private final GameBlock[] gameBlocks = new GameBlock[3];

    private UUID takenBy;
    private boolean initialize = false;

    public DetachedWorld(final GameDirector director) {
        this.director = director;
        this.world = new GameWorld();
        this.location = new GameLocation();
    }

    public void initialize() {
        if (initialize) return;
        this.initialize = true;

        this.world.initialize();
        this.location.initialize(director, world.world());

        for (int i = 0; i < 3; i++) {
            final int position = i == 0 ? 0 : i == 1 ? 2 : 4;
            this.gameBlocks[i] = new GameBlock(location.location().clone().add(2 - position, 0, -4));
        }
    }

    public DetachedWorld takenBy(final UUID takenBy) {
        this.takenBy = takenBy;
        return this;
    }

    public GameWorld gameWorld() {
        return this.world;
    }

    public GameLocation gamelocation() {
        return this.location;
    }

    public GameBlock[] chosenBlocks() {
        return this.gameBlocks;
    }

    public UUID takenBy() {
        return this.takenBy;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof DetachedWorld)) return false;
        final DetachedWorld that = (DetachedWorld) o;
        return this.mapUniqueId.equals(that.mapUniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mapUniqueId);
    }
}
