package io.tofpu.memorizetheblock.director.process;

import io.tofpu.memorizetheblock.director.GameDirector;
import io.tofpu.memorizetheblock.director.object.DetachedWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class DetachedWorldLogic {
    private static DetachedWorldLogic instance;
    private final GameDirector gameDirector;
    private final List<DetachedWorld> detachedWorlds;

    public synchronized static DetachedWorldLogic of(final GameDirector director) {
        if (instance == null) {
            instance = new DetachedWorldLogic(director);
        }
        return instance;
    }

    private DetachedWorldLogic(final GameDirector gameDirector) {
        this.gameDirector = gameDirector;
        this.detachedWorlds = new ArrayList<>();
    }

    public DetachedWorld isolate(final UUID uniqueId) {
        DetachedWorld detachedWorld = get(uniqueId);
        if (detachedWorld == null) {
            detachedWorld = availableWorld(uniqueId);
        }

        return detachedWorld;
    }

    public DetachedWorld get(final UUID uniqueId) {
        for (final DetachedWorld detachedWorld : detachedWorlds) {
            if (detachedWorld.takenBy() != null && detachedWorld.takenBy().equals(uniqueId)) return detachedWorld;
        }
        return null;
    }

    public DetachedWorld availableWorld(UUID uniqueId) {
        for (final DetachedWorld detachedWorld : detachedWorlds) {
            if (detachedWorld.takenBy() == null) {
                detachedWorld.takenBy(uniqueId);
                return detachedWorld;
            }
        }
        final DetachedWorld detachedWorld = DetachedWorld.of(gameDirector);
        detachedWorld.initialize();
        detachedWorld.takenBy(uniqueId);
        detachedWorlds.add(detachedWorld);

        return detachedWorld;
    }

    public void remove(final UUID uniqueId) {
        final DetachedWorld detachedWorld = get(uniqueId);
        detachedWorld.takenBy(null);
    }

    public GameDirector director() {
        return this.gameDirector;
    }
}
