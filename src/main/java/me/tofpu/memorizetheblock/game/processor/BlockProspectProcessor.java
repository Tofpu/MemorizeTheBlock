package me.tofpu.memorizetheblock.game.processor;

import com.avaje.ebean.validation.NotNull;
import me.tofpu.memorizetheblock.game.object.ChosenBlock;
import me.tofpu.memorizetheblock.game.object.GamePlayer;
import me.tofpu.memorizetheblock.util.GameUtil;
import me.tofpu.memorizetheblock.util.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public final class BlockProspectProcessor {
    public static final SplittableRandom SPLITTABLE_RANDOM = new SplittableRandom();

    private final int amount = 3;
    private final List<XMaterial> materials = new ArrayList<>();

    public BlockProspectProcessor() {
        for (final XMaterial material : XMaterial.values()) {
            switch (material) {
                case STONE:
                case COBBLESTONE:
                case REDSTONE_BLOCK:
                case OAK_LOG:
                case ANVIL:
                case BEDROCK:
                case BOOKSHELF:
                case COAL_BLOCK:
                case IRON_BLOCK:
                case GOLD_BLOCK:
                case DIAMOND_BLOCK:
                    materials.add(material);
            }
        }
    }

    public void result(final GameLogicProcessor logicProcessor, final GamePlayer gamePlayer) {
        final Player player = gamePlayer.player();
        logicProcessor.remove(player);

        final boolean[] guesses = new boolean[]{false, false, false};

        int index = 0;
        for (final XMaterial material : gamePlayer.chosenBlocks()) {
            final ChosenBlock guess = gamePlayer.pickedBlocks().get(index);
            if (guess == null) continue;
            guesses[index] = guess.block().getType() == material.parseMaterial();
            index++;
        }
        index = 0;

        final StringBuilder builder = new StringBuilder();
        builder.append("&6&l&m*&r &eYour guesses:").append("\n");
        // Your guesses:
        // Diamond(GOLD) Gold(DIAMOND) Iron(IRON)

        // > Your Guess
        // | DIAMOND X - IRON
        // | GOLD Y
        // | IRON X - DIAMOND

        for (final XMaterial material : gamePlayer.chosenBlocks()) {
            final boolean guess = guesses[index];
            final Material guessMaterial = gamePlayer.pickedBlocks().get(index).block().getType();
            if (index != 0) builder.append("\n");

            builder.append("&6&l&m|&r").append(" ").append("&e").append(guess ? "" : "&m").append(guessMaterial.name()).append(guess ? "" : "&6 ").append(guess ? "" : material.name()).append(" ").append(guess ? "&a✓" : "&c✕");
            index++;
        }

        player.sendMessage(GameUtil.colorize(builder.toString()));
        logicProcessor.join(player);
    }

    @NotNull
    public Collection<XMaterial> pick(final GamePlayer player) {
        final List<XMaterial> picked = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            // random generated number
            final int random = SPLITTABLE_RANDOM.nextInt(this.materials.size() - 1);

            picked.add(materials.get(random));
        }
        player.chosen(new ArrayList<>(picked));

        // returns the materials that was picked
        return picked;
    }

    public int getAmount() {
        return this.amount;
    }

    @NotNull
    public EnumSet<XMaterial> materials() {
        return EnumSet.copyOf(materials);
    }
}
