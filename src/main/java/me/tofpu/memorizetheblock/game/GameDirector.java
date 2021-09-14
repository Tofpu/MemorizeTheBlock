package me.tofpu.memorizetheblock.game;

import me.tofpu.memorizetheblock.game.listener.*;
import me.tofpu.memorizetheblock.game.processor.BlockProspectProcessor;
import me.tofpu.memorizetheblock.game.processor.GameLogicProcessor;
import me.tofpu.memorizetheblock.game.processor.IsolatedWorldLogic;
import org.bukkit.plugin.Plugin;

public final class GameDirector {
    private final Plugin plugin;

    private final BlockProspectProcessor blockProspectProcessor;
    private final GameLogicProcessor logicProcessor;
    private final IsolatedWorldLogic isolatedWorldLogic;

    public GameDirector(final Plugin plugin) {
        this.plugin = plugin;

        this.blockProspectProcessor = new BlockProspectProcessor();
        this.logicProcessor = new GameLogicProcessor(this);
        this.isolatedWorldLogic = new IsolatedWorldLogic(this);

        new PlayerQuitListener(logicProcessor);
        new BlockPlaceListener(logicProcessor);
        new BlockBreakListener(logicProcessor);
        new PlayerMoveListener(logicProcessor);
    }

    public void prepare() {
        GameListener.initialize(plugin);
    }

    public Plugin plugin() {
        return this.plugin;
    }

    public GameLogicProcessor logicProcessor() {
        return this.logicProcessor;
    }

    public BlockProspectProcessor blockProspectProcessor() {
        return this.blockProspectProcessor;
    }

    public IsolatedWorldLogic isolatedWorldLogic() {
        return this.isolatedWorldLogic;
    }
}
