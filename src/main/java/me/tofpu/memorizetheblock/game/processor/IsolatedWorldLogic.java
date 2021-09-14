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

    public IsolatedWorld isolate(final Player player) {
        IsolatedWorld isolatedWorld = worldMap.get(player.getUniqueId());
        if (isolatedWorld == null) {
            isolatedWorld = new IsolatedWorld(gameDirector);
            isolatedWorld.initialize();
            worldMap.put(player.getUniqueId(), isolatedWorld);
        }

        return isolatedWorld;
    }

    public GameDirector director() {
        return this.gameDirector;
    }
}
