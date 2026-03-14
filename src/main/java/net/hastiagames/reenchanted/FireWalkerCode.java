package net.hastiagames.reenchanted;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class FireWalkerCode extends Enchantment {

    public FireWalkerCode(Rarity rarityIn, EquipmentSlot... slots) {
        super(rarityIn, EnchantmentCategory.ARMOR_FEET, slots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    private int getRadiusByLevel(int level) {
        switch(level) {
            case 1: return 2;
            case 2: return 3;
            case 3: return 4;
            default: return 2;
        }
    }

    private int getDurationByLevel(int level) {
        return level * 40; // 20 ticks = 1 seconde
    }

    // Map pour stocker les blocs temporaires et le tick restant
    private static final Map<BlockPos, Integer> tempBlocks = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerWalk(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        Level world = player.level();

        // 1️⃣ Transformation uniquement si les bottes sont équipées
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (!boots.isEmpty()) {
            Enchantment fireWalker = ForgeRegistries.ENCHANTMENTS.getValue(
                    new net.minecraft.resources.ResourceLocation("reenchanted", "fire_walker")
            );
            int level = boots.getEnchantmentLevel(fireWalker);
            if (level > 0) {
                int radius = new FireWalkerCode(Rarity.UNCOMMON, EquipmentSlot.FEET).getRadiusByLevel(level);
                int duration = new FireWalkerCode(Rarity.UNCOMMON, EquipmentSlot.FEET).getDurationByLevel(level);

                BlockPos playerPos = player.blockPosition();

                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos pos = playerPos.offset(x, -1, z);
                        BlockState state = world.getBlockState(pos);
                        if (state.getBlock() == Blocks.LAVA || state.getBlock() == Blocks.FIRE) {
                            world.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
                            tempBlocks.put(pos, duration); // stocke la durée restante
                        }
                    }
                }
            }
        }

        // 2️⃣ Tick update pour remettre la lave, toujours exécuté
        Map<BlockPos, Integer> toRemove = new HashMap<>();
        for (Map.Entry<BlockPos, Integer> entry : tempBlocks.entrySet()) {
            BlockPos pos = entry.getKey();
            int ticksLeft = entry.getValue() - 1;
            if (ticksLeft <= 0) {
                world.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
                toRemove.put(pos, ticksLeft);
            } else {
                entry.setValue(ticksLeft);
            }
        }
        for (BlockPos pos : toRemove.keySet()) {
            tempBlocks.remove(pos);
        }
    }
}