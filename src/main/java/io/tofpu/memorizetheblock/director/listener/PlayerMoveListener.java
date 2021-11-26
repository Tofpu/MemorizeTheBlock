package io.tofpu.memorizetheblock.director.listener;

import io.tofpu.memorizetheblock.director.process.GameLogicProcess;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    private static PlayerMoveListener instance;
    private final GameLogicProcess logicProcessor;

    public synchronized static PlayerMoveListener initialize(final GameLogicProcess logicProcessor) {
        if (instance == null) {
            instance = new PlayerMoveListener(logicProcessor);
        }
        return instance;
    }

    public PlayerMoveListener(final GameLogicProcess logicProcessor) {
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

    public GameLogicProcess logicProcessor() {
        return this.logicProcessor;
    }
}
