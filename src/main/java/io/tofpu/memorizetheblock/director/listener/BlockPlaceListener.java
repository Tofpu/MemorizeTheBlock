package io.tofpu.memorizetheblock.director.listener;

import io.tofpu.memorizetheblock.director.object.GamePlayer;
import io.tofpu.memorizetheblock.director.process.GameLogicProcess;
import io.tofpu.memorizetheblock.util.GameUtil;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    private static BlockPlaceListener instance;
    private final GameLogicProcess logicProcessor;

    public synchronized static BlockPlaceListener initialize(final GameLogicProcess logicProcessor) {
        if (instance == null) {
            instance = new BlockPlaceListener(logicProcessor);
        }
        return instance;
    }

    public BlockPlaceListener(final GameLogicProcess logicProcessor) {
        this.logicProcessor = logicProcessor;
        GameListener.register(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onBlockPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        if (!logicProcessor.isPlaying(player)) return;

        final GamePlayer gamePlayer = logicProcessor.get(event.getPlayer());
        final Block block = event.getBlock();

        if (!gamePlayer.picked(logicProcessor.director(), block)) {
            event.setCancelled(true);
            return;
        }

        gamePlayer.popItem();
        GameUtil.playSound(gamePlayer, Sound.CLICK);
    }

    public GameLogicProcess logicProcessor() {
        return this.logicProcessor;
    }
}
