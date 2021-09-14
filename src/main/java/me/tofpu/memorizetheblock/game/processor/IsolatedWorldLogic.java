package me.tofpu.memorizetheblock.game.processor;

import me.tofpu.memorizetheblock.game.GameDirector;
import me.tofpu.memorizetheblock.game.object.IsolatedWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IsolatedWorldLogic {
    private final GameDirector gameDirector;
    private final List<IsolatedWorld> isolatedWorlds;

    public IsolatedWorldLogic(final GameDirector gameDirector) {
        this.gameDirector = gameDirector;
        this.isolatedWorlds = new ArrayList<>();
    }

    public IsolatedWorld isolate(final UUID uniqueId) {
        IsolatedWorld isolatedWorld = get(uniqueId);
        if (isolatedWorld == null) {
            isolatedWorld = availableWorld(uniqueId);
        }

        return isolatedWorld;
    }

    public IsolatedWorld get(final UUID uniqueId) {
        for (final IsolatedWorld isolatedWorld : isolatedWorlds) {
            if (isolatedWorld.takenBy() != null && isolatedWorld.takenBy().equals(uniqueId)) return isolatedWorld;
        }
        return null;
    }

    public IsolatedWorld availableWorld(UUID uniqueId) {
        for (final IsolatedWorld isolatedWorld : isolatedWorlds) {
            if (isolatedWorld.takenBy() == null) {
                isolatedWorld.takenBy(uniqueId);
                return isolatedWorld;
            }
        }
        final IsolatedWorld isolatedWorld = new IsolatedWorld(gameDirector);
        isolatedWorld.initialize();
        isolatedWorld.takenBy(uniqueId);
        isolatedWorlds.add(isolatedWorld);

        return isolatedWorld;
    }

    public void remove(final UUID uniqueId) {
        final IsolatedWorld isolatedWorld = get(uniqueId);
        isolatedWorld.takenBy(null);
    }

    public GameDirector director() {
        return this.gameDirector;
    }
}
