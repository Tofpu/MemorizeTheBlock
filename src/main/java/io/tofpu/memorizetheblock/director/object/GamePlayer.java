package io.tofpu.memorizetheblock.director.object;

import com.avaje.ebean.validation.NotNull;
import io.tofpu.memorizetheblock.director.GameDirector;
import io.tofpu.memorizetheblock.util.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class GamePlayer {
    private final UUID uniqueId;
    private final Player player;

    private final List<XMaterial> chosenBlocks;
    private final List<GameBlock> pickedBlocks;
    private final Stack<ItemStack> itemStacks;

    public static GamePlayer of(final GameDirector director, final UUID uniqueId) {
        return new GamePlayer(director, uniqueId);
    }

    private GamePlayer(final GameDirector director, final UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.player = Bukkit.getPlayer(uniqueId);

        this.chosenBlocks = new ArrayList<>();
        this.pickedBlocks = new ArrayList<>(Arrays.asList(director.isolatedWorldLogic().isolate(uniqueId).chosenBlocks()));
        this.itemStacks = new Stack<>();
    }

    private int size() {
        int size = 0;
        for (final GameBlock block : pickedBlocks) {
            final Material material = block.block().getType();
            if (material == Material.AIR) continue;
            size++;
        }
        return size;
    }

    public void chosen(final List<XMaterial> materials) {
        this.chosenBlocks.addAll(materials);

        Collections.shuffle(materials);
        for (final XMaterial material : materials) {
            itemStacks.push(new ItemStack(material.parseMaterial(), 1));
        }
    }

    public boolean picked(final GameDirector gameDirector, final Block block) {
        boolean validSpot = false;
        for (final GameBlock gameBlock : pickedBlocks) {
            if (!gameBlock.block().getLocation().equals(block.getLocation())) continue;
            gameBlock.block(block.getType());
            validSpot = true;
        }
        if (size() == 3) gameDirector.blockProspectProcessor().result(gameDirector.logicProcessor(), this);
        return validSpot;
    }

    public void popItem() {
        if (itemStacks.empty()) return;
        player.getInventory().setItem(0, itemStacks.pop());
    }

    public void reset() {
        chosenBlocks.clear();
    }

    @NotNull
    public UUID uniqueId() {
        return this.uniqueId;
    }

    @NotNull
    public Player player() {
        return this.player;
    }

    @NotNull
    public List<XMaterial> chosenBlocks() {
        return new ArrayList<>(chosenBlocks);
    }

    @NotNull
    public List<GameBlock> pickedBlocks() {
        return new ArrayList<>(pickedBlocks);
    }
}
