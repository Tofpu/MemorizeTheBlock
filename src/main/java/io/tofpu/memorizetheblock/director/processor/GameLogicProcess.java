package io.tofpu.memorizetheblock.director.processor;

import com.avaje.ebean.validation.NotNull;
import io.tofpu.memorizetheblock.director.GameDirector;
import io.tofpu.memorizetheblock.director.object.GamePlayer;
import io.tofpu.memorizetheblock.director.object.DetachedWorld;
import io.tofpu.memorizetheblock.director.runnable.GameTimer;
import io.tofpu.memorizetheblock.director.runnable.MemorizeTicker;
import io.tofpu.memorizetheblock.util.GameUtil;
import io.tofpu.memorizetheblock.util.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLogicProcess {
    private final GameDirector director;
    private final GameTimer timer;

    private final List<GamePlayer> players = new ArrayList<>();
    private final Map<GamePlayer, Instant> playerDuration = new HashMap<>();

    public GameLogicProcess(final GameDirector director) {
        this.director = director;
        this.timer = new GameTimer(this, director.blockProspectProcessor());
    }

    private void join(final DetachedWorld detachedWorld, final GamePlayer gamePlayer) {
        final Player player = gamePlayer.player();

        gamePlayer.reset();
        players.add(gamePlayer);

        player.teleport(detachedWorld.gamelocation().location());

        final Inventory inventory = player.getInventory();
        inventory.clear();

        int index = 0;
        for (final XMaterial chosen : director.blockProspectProcessor().pick(gamePlayer)) {
            detachedWorld.chosenBlocks()[index].block(chosen.parseMaterial());
            index++;
        }
        player.sendMessage(GameUtil.colorize("&eMemorize the block type and their location!"));

        MemorizeTicker.start(director, gamePlayer);
    }

    public void join(final Player player) {
        final DetachedWorld detachedWorld = director.isolatedWorldLogic().isolate(player.getUniqueId());
        join(detachedWorld, get(player));
    }

    public GamePlayer get(final Player player) {
        for (final GamePlayer gamePlayer : players) {
            if (gamePlayer.uniqueId().equals(player.getUniqueId())) return gamePlayer;
        }
        return new GamePlayer(director, player.getUniqueId());
    }

    public void remove(final Player player, boolean quitting) {
        if (!isPlaying(player)) return;
        final GamePlayer gamePlayer = get(player);

        gamePlayer.player().getInventory().clear();

        players.remove(gamePlayer);
        playerDuration.remove(gamePlayer);
        if (quitting) director.isolatedWorldLogic().remove(gamePlayer.uniqueId());
    }

    public boolean isPlaying(final Player player) {
        for (final GamePlayer gamePlayer : players) {
            if (gamePlayer.uniqueId().equals(player.getUniqueId())) return true;
        }
        return false;
    }

    public GameDirector director() {
        return this.director;
    }

    public GameTimer timer() {
        return this.timer;
    }

    @NotNull
    public Map<GamePlayer, Instant> playerDuration() {
        return this.playerDuration;
    }

    @NotNull
    public List<GamePlayer> players() {
        return new ArrayList<>(players);
    }
}
