package me.tofpu.memorizetheblock.game.listener;

import me.tofpu.memorizetheblock.game.processor.GameLogicProcessor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    private final GameLogicProcessor logicProcessor;

    public PlayerMoveListener(final GameLogicProcessor logicProcessor) {
        this.logicProcessor = logicProcessor;
        GameListener.register(this);
    }

    @EventHandler
    private void onPlayerMove(final PlayerMoveEvent event) {
        if (!logicProcessor.isPlaying(event.getPlayer())) return;

        final int fromX = (int) event.getFrom().getX();
        final int toX = (int) event.getTo().getX();
        final int total = Math.abs(fromX - toX);

        if (total >= 1) {
            final Location location = event.getFrom().setDirection(event.getTo().getDirection());
            event.getPlayer().teleport(location);
        }
    }

    public GameLogicProcessor logicProcessor() {
        return this.logicProcessor;
    }
}
