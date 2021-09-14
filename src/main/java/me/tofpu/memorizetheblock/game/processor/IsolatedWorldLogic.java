package me.tofpu.memorizetheblock.game.processor;

import me.tofpu.memorizetheblock.game.GameDirector;
import me.tofpu.memorizetheblock.game.object.IsolatedWorld;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IsolatedWorldLogic {
    private final GameDirector gameDirector;
    private final Map<UUID, IsolatedWorld> worldMap;

    public IsolatedWorldLogic(final GameDirector gameDirector) {
        this.gameDirector = gameDirector;
        this.worldMap = new HashMap<>();
    }

    public IsolatedWorld isolate(final UUID uuid) {
        IsolatedWorld isolatedWorld = worldMap.get(uuid);
        if (isolatedWorld == null) {
            isolatedWorld = new IsolatedWorld(gameDirector);
            isolatedWorld.initialize();
            worldMap.put(uuid, isolatedWorld);
        }

        return isolatedWorld;
    }

    public void remove(final UUID uuid) {
        worldMap.remove(uuid);
    }

    public GameDirector director() {
        return this.gameDirector;
    }
}
