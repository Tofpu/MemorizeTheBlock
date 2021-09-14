package me.tofpu.memorizetheblock.game.listener;

import me.tofpu.memorizetheblock.game.object.GamePlayer;
import me.tofpu.memorizetheblock.game.processor.GameLogicProcessor;
import me.tofpu.memorizetheblock.util.GameUtil;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    private final GameLogicProcessor logicProcessor;

    public BlockPlaceListener(final GameLogicProcessor logicProcessor) {
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

    public GameLogicProcessor logicProcessor() {
        return this.logicProcessor;
    }
}
