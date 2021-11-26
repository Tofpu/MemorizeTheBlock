package io.tofpu.memorizetheblock.director;

import io.tofpu.memorizetheblock.director.listener.*;
import io.tofpu.memorizetheblock.director.process.BlockProspectProcess;
import io.tofpu.memorizetheblock.director.process.GameLogicProcess;
import io.tofpu.memorizetheblock.director.process.DetachedWorldLogic;
import org.bukkit.plugin.Plugin;

public final class GameDirector {
    private final Plugin plugin;

    private final BlockProspectProcess blockProspectProcess;
    private final GameLogicProcess logicProcessor;
    private final DetachedWorldLogic detachedWorldLogic;

    public GameDirector(final Plugin plugin) {
        this.plugin = plugin;

        this.blockProspectProcess = BlockProspectProcess.of();
        this.logicProcessor = GameLogicProcess.of(this);
        this.detachedWorldLogic = DetachedWorldLogic.of(this);
    }

    public void prepare() {
        PlayerQuitListener.of(logicProcessor);
        BlockPlaceListener.of(logicProcessor);
        BlockBreakListener.of(logicProcessor);
        PlayerMoveListener.of(logicProcessor);

        // registering all the listeners
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
