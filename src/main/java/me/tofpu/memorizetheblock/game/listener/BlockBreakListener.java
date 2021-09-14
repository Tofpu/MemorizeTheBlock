package me.tofpu.memorizetheblock.game.listener;

import me.tofpu.memorizetheblock.game.processor.GameLogicProcessor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private final GameLogicProcessor logicProcessor;

    public BlockBreakListener(final GameLogicProcessor logicProcessor) {
        this.logicProcessor = logicProcessor;
        GameListener.register(this);
    }

    @EventHandler
    private void onBlockBreak(final BlockBreakEvent event) {
        if (logicProcessor.isPlaying(event.getPlayer())) event.setCancelled(true);
    }

    public GameLogicProcessor logicProcessor() {
        return this.logicProcessor;
    }
}
