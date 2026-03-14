package net.hastiagames.reenchanted;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber
public class VeinMinerCode extends Enchantment {

    public VeinMinerCode(Rarity rarityIn, EnchantmentCategory type, EquipmentSlot[] slots) {
        super(rarityIn, type, slots);
    }

    private static final Set<Block> MINABLE_BLOCKS = new HashSet<>();

    static {
        MINABLE_BLOCKS.add(Blocks.DIAMOND_ORE);
        MINABLE_BLOCKS.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        MINABLE_BLOCKS.add(Blocks.IRON_ORE);
        MINABLE_BLOCKS.add(Blocks.DEEPSLATE_IRON_ORE);
        MINABLE_BLOCKS.add(Blocks.GOLD_ORE);
        MINABLE_BLOCKS.add(Blocks.DEEPSLATE_GOLD_ORE);
        MINABLE_BLOCKS.add(Blocks.EMERALD_ORE);
        MINABLE_BLOCKS.add(Blocks.DEEPSLATE_EMERALD_ORE);
        MINABLE_BLOCKS.add(Blocks.NETHER_QUARTZ_ORE);
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakEvent event) {
        Object entity = event.getPlayer();
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;

        ItemStack tool = player.getMainHandItem();
        if (tool.isEmpty() || !(tool.getItem() instanceof TieredItem)) return;

        Enchantment veinMiner = ForgeRegistries.ENCHANTMENTS.getValue(
                new net.minecraft.resources.ResourceLocation("reenchanted", "vein_miner")
        );
        if (veinMiner == null) return;
        if (EnchantmentHelper.getItemEnchantmentLevel(veinMiner, tool) == 0) return;

        BlockPos pos = event.getPos();
        Level level = (Level) event.getLevel();
        Block targetBlock = event.getState().getBlock();

        if (!MINABLE_BLOCKS.contains(targetBlock)) return;

        // Thermal doit déjà avoir fondu le bloc
        if (ThermalCode.smeltMap.containsKey(targetBlock)) return;

        Set<BlockPos> visited = new HashSet<>();
        mineAdjacentBlocks(level, pos, targetBlock, player, visited);
    }

    private static void mineAdjacentBlocks(Level level, BlockPos pos, Block targetBlock, Player player, Set<BlockPos> visited) {
        if (visited.contains(pos)) return;
        visited.add(pos);

        if (level.getBlockState(pos).getBlock() != targetBlock) return;

        level.destroyBlock(pos, true, player);

        mineAdjacentBlocks(level, pos.north(), targetBlock, player, visited);
        mineAdjacentBlocks(level, pos.south(), targetBlock, player, visited);
        mineAdjacentBlocks(level, pos.east(), targetBlock, player, visited);
        mineAdjacentBlocks(level, pos.west(), targetBlock, player, visited);
        mineAdjacentBlocks(level, pos.above(), targetBlock, player, visited);
        mineAdjacentBlocks(level, pos.below(), targetBlock, player, visited);
    }
}