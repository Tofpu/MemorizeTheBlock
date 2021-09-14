package me.tofpu.memorizetheblock.game.listener;

import me.tofpu.memorizetheblock.game.processor.GameLogicProcessor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final GameLogicProcessor logicProcessor;

    public PlayerQuitListener(final GameLogicProcessor logicProcessor) {
        this.logicProcessor = logicProcessor;
        GameListener.register(this);
    }

    @EventHandler
    private void onPlayerQuit(final PlayerQuitEvent event) {
        logicProcessor.remove(event.getPlayer(), true);
    }

    public GameLogicProcessor logicProcessor() {
        return this.logicProcessor;
    }
}
