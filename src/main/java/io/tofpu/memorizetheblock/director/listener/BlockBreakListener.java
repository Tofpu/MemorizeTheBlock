package io.tofpu.memorizetheblock.director.listener;

import io.tofpu.memorizetheblock.director.process.GameLogicProcess;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public final class BlockBreakListener implements Listener {
    private static BlockBreakListener instance;
    private final GameLogicProcess logicProcessor;

    public synchronized static BlockBreakListener of(final GameLogicProcess logicProcessor) {
        if (instance == null) {
            instance = new BlockBreakListener(logicProcessor);
        }
        return instance;
    }

    private BlockBreakListener(final GameLogicProcess logicProcessor) {
        this.logicProcessor = logicProcessor;
        GameListener.register(this);
    }

    @EventHandler
    private void onBlockBreak(final BlockBreakEvent event) {
        if (logicProcessor.isPlaying(event.getPlayer())) event.setCancelled(true);
    }

    public GameLogicProcess logicProcessor() {
        return this.logicProcessor;
    }
}
