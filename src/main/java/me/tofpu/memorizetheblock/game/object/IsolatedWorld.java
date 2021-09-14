package me.tofpu.memorizetheblock.game.object;

import me.tofpu.memorizetheblock.game.GameDirector;
import me.tofpu.memorizetheblock.game.wrapper.GameLocation;
import me.tofpu.memorizetheblock.game.wrapper.GameWorld;

import java.util.Objects;
import java.util.UUID;

public class IsolatedWorld {
    private final UUID mapUniqueId = UUID.randomUUID();
    private final GameDirector director;

    private final GameWorld world;
    private final GameLocation location;
    private final ChosenBlock[] chosenBlocks = new ChosenBlock[3];

    private UUID takenBy;
    private boolean initialize = false;

    public IsolatedWorld(final GameDirector director) {
        this.director = director;
        this.world = new GameWorld();
        this.location = new GameLocation();
    }

    public void initialize() {
        if (initialize) return;
        initialize = true;

        world.initialize();
        location.initialize(director, world.world());

        for (int i = 0; i < 3; i++) {
            final int position = i == 0 ? 0 : i == 1 ? 2 : 4;
            chosenBlocks[i] = new ChosenBlock(location.location().clone().add(2 - position, 0, -4));
        }
    }

    public IsolatedWorld takenBy(final UUID takenBy) {
        this.takenBy = takenBy;
        return this;
    }

    public GameWorld gameWorld() {
        return this.world;
    }

    public GameLocation gamelocation() {
        return this.location;
    }

    public ChosenBlock[] chosenBlocks() {
        return this.chosenBlocks;
    }

    public UUID takenBy() {
        return takenBy;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof IsolatedWorld)) return false;
        final IsolatedWorld that = (IsolatedWorld) o;
        return mapUniqueId.equals(that.mapUniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mapUniqueId);
    }
}
