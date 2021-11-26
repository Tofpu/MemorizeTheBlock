package io.tofpu.memorizetheblock.director.listener;

import io.tofpu.memorizetheblock.director.process.GameLogicProcess;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private static PlayerQuitListener instance;
    private final GameLogicProcess logicProcessor;

    public synchronized static PlayerQuitListener of(final GameLogicProcess logicProcessor) {
        if (instance == null) {
            instance = new PlayerQuitListener(logicProcessor);
        }
        return instance;
    }

    public PlayerQuitListener(final GameLogicProcess logicProcessor) {
        this.logicProcessor = logicProcessor;
        GameListener.register(this);
    }

    @EventHandler
    private void onPlayerQuit(final PlayerQuitEvent event) {
        this.logicProcessor.remove(event.getPlayer(), true);
    }

    public GameLogicProcess logicProcessor() {
        return this.logicProcessor;
    }
}
