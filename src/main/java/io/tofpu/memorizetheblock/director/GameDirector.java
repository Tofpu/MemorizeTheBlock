package io.tofpu.memorizetheblock.director;

import io.tofpu.memorizetheblock.director.listener.*;
import io.tofpu.memorizetheblock.director.processor.BlockProspectProcess;
import io.tofpu.memorizetheblock.director.processor.GameLogicProcess;
import io.tofpu.memorizetheblock.director.processor.DetachedWorldLogic;
import org.bukkit.plugin.Plugin;

public final class GameDirector {
    private final Plugin plugin;

    private final BlockProspectProcess blockProspectProcess;
    private final GameLogicProcess logicProcessor;
    private final DetachedWorldLogic detachedWorldLogic;

    public GameDirector(final Plugin plugin) {
        this.plugin = plugin;

        this.blockProspectProcess = new BlockProspectProcess();
        this.logicProcessor = new GameLogicProcess(this);
        this.detachedWorldLogic = new DetachedWorldLogic(this);
    }

    public void prepare() {
        PlayerQuitListener.initialize(logicProcessor);
        BlockPlaceListener.initialize(logicProcessor);
        BlockBreakListener.initialize(logicProcessor);
        PlayerMoveListener.initialize(logicProcessor);
        GameListener.initialize(plugin);
    }

    public Plugin plugin() {
        return this.plugin;
    }

    public GameLogicProcess logicProcessor() {
        return this.logicProcessor;
    }

    public BlockProspectProcess blockProspectProcessor() {
        return this.blockProspectProcess;
    }

    public DetachedWorldLogic isolatedWorldLogic() {
        return this.detachedWorldLogic;
    }
}
