package me.tofpu.memorizetheblock.game.processor;

import com.avaje.ebean.validation.NotNull;
import me.tofpu.memorizetheblock.game.GameDirector;
import me.tofpu.memorizetheblock.game.object.GamePlayer;
import me.tofpu.memorizetheblock.game.object.IsolatedWorld;
import me.tofpu.memorizetheblock.game.runnable.GameTimer;
import me.tofpu.memorizetheblock.game.runnable.MemorizeTicker;
import me.tofpu.memorizetheblock.util.GameUtil;
import me.tofpu.memorizetheblock.util.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLogicProcessor {
    private final GameDirector director;
    private final GameTimer timer;

    private final List<GamePlayer> players = new ArrayList<>();
    private final Map<GamePlayer, Instant> playerDuration = new HashMap<>();

    public GameLogicProcessor(final GameDirector director) {
        this.director = director;
        this.timer = new GameTimer(this, director.blockProspectProcessor());
    }

    private void join(final IsolatedWorld isolatedWorld, final GamePlayer gamePlayer) {
        final Player player = gamePlayer.player();

        gamePlayer.reset();
        players.add(gamePlayer);

        player.teleport(isolatedWorld.gamelocation().location());

        final Inventory inventory = player.getInventory();
        inventory.clear();

        int index = 0;
        for (final XMaterial chosen : director.blockProspectProcessor().pick(gamePlayer)) {
            isolatedWorld.chosenBlocks()[index].block(chosen.parseMaterial());
            index++;
        }
        player.sendMessage(GameUtil.colorize("&eMemorize the block type and their location!"));

        MemorizeTicker.start(director, gamePlayer);
    }

    public void join(final Player player) {
        final IsolatedWorld isolatedWorld = director.isolatedWorldLogic().isolate(player);
        join(isolatedWorld, get(player));
    }

    public GamePlayer get(final Player player) {
        for (final GamePlayer gamePlayer : players) {
            if (gamePlayer.uniqueId().equals(player.getUniqueId())) return gamePlayer;
        }
        return new GamePlayer(director, player.getUniqueId());
    }

    public void remove(final Player player) {
        if (!isPlaying(player)) return;
        final GamePlayer gamePlayer = get(player);

        gamePlayer.player().getInventory().clear();

        players.remove(gamePlayer);
        playerDuration.remove(gamePlayer);
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
