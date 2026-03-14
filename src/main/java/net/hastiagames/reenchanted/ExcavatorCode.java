package net.hastiagames.reenchanted;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.HashSet;

@Mod.EventBusSubscriber
public class ExcavatorCode extends Enchantment {

    public ExcavatorCode(Rarity rarityIn, EquipmentSlot... slots) {
        super(rarityIn, EnchantmentCategory.DIGGER, slots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    private int getHorizontalRangeByLevel(int level) {
        switch(level) {
            case 1: return 1; // 3x?x3
            case 2: return 1; // 3x?x3
            case 3: return 1; // 3x?x3
            default: return 1;
        }
    }

    private int getVerticalRangeByLevel(int level) {
        switch(level) {
            case 1: return 1; // 3x1x3
            case 2: return 2; // 3x2x3
            case 3: return 3; // 3x3x3
            default: return 1;
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakEvent event) {
        Player player = event.getPlayer();
        LevelAccessor worldAccessor = event.getLevel();

        if (!(worldAccessor instanceof Level world)) return;

        BlockState state = event.getState();
        Block block = state.getBlock();
        ItemStack tool = player.getMainHandItem();

        if (!(tool.getItem() instanceof PickaxeItem)) return;

        Enchantment excavator = ForgeRegistries.ENCHANTMENTS.getValue(
                new net.minecraft.resources.ResourceLocation("reenchanted", "excavator")
        );

        int level = tool.getEnchantmentLevel(excavator);
        if (level == 0) return;

        ExcavatorCode instance = new ExcavatorCode(Rarity.UNCOMMON, EquipmentSlot.MAINHAND);
        int rangeX = instance.getHorizontalRangeByLevel(level);
        int rangeY = instance.getVerticalRangeByLevel(level);
        int rangeZ = rangeX;

        Set<BlockPos> toBreak = new HashSet<>();
        BlockPos origin = event.getPos();

        for (int x = -rangeX; x <= rangeX; x++) {
            int yStart, yEnd;

            if (level == 1) {
                yStart = 0;
                yEnd = 0;
            } else {
                yStart = -1;
                yEnd = rangeY - 2;
            }

            for (int y = yStart; y <= yEnd; y++) {
                for (int z = -rangeZ; z <= rangeZ; z++) {
                    BlockPos pos = origin.offset(x, y, z);
                    BlockState checkState = world.getBlockState(pos);

                    if (checkState.getBlock() == block &&
                        tool.getItem() instanceof TieredItem tieredTool &&
                        tieredTool.isCorrectToolForDrops(checkState)) {
                        toBreak.add(pos);
                    }
                }
            }
        }

        for (BlockPos pos : toBreak) {
            world.destroyBlock(pos, true, player);
            // Réduit la durabilité de l’outil d’1 pour chaque bloc cassé
            tool.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
        }
    }
}