package me.tofpu.memorizetheblock;

import me.tofpu.memorizetheblock.game.GameDirector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MemorizeTheBlock {
    private static MemorizeTheBlock instance;

    private final Plugin plugin;
    private final GameDirector gameDirector;

    public MemorizeTheBlock(final Plugin plugin) {
        this.plugin = plugin;
        this.gameDirector = new GameDirector(plugin);
    }

    public void initialize() {
        this.gameDirector.prepare();
    }

    public static MemorizeTheBlock initialize(final Plugin plugin) {
        if (Bukkit.isPrimaryThread()) {
            if (instance != null) return instance;
            instance = new MemorizeTheBlock(plugin);
            instance.initialize();
            return instance;
        }

        synchronized (instance) {
            if (instance == null) {
                instance = new MemorizeTheBlock(plugin);
                instance.initialize();
            }
            return instance;
        }
    }

    public GameDirector gameDirector() {
        return this.gameDirector;
    }

    public Plugin plugin() {
        return this.plugin;
    }
}
