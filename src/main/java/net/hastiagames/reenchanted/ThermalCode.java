package net.hastiagames.reenchanted;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class ThermalCode {

    public static final Map<Block, ItemStack> smeltMap = new HashMap<>();

    static {
        // Exemples de blocs et drops fondus
        // smeltMap.put(Blocks.IRON_ORE, new ItemStack(Items.IRON_INGOT));
        // smeltMap.put(Blocks.DEEPSLATE_IRON_ORE, new ItemStack(Items.IRON_INGOT));
        // smeltMap.put(Blocks.GOLD_ORE, new ItemStack(Items.GOLD_INGOT));
        // smeltMap.put(Blocks.DEEPSLATE_GOLD_ORE, new ItemStack(Items.GOLD_INGOT));
        // smeltMap.put(Blocks.DIAMOND_ORE, new ItemStack(Items.DIAMOND));
        // smeltMap.put(Blocks.DEEPSLATE_DIAMOND_ORE, new ItemStack(Items.DIAMOND));
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakEvent event) {
        Object entity = event.getPlayer();
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;

        ItemStack tool = player.getMainHandItem();
        if (!(tool.getItem() instanceof TieredItem)) return;

        Block block = event.getState().getBlock();
        Level world = (Level) event.getLevel();

        if (smeltMap.containsKey(block)) {
            world.destroyBlock(event.getPos(), false);
            BlockPos pos = event.getPos();
            block = null; // Supprime référence ancienne
            player.addItem(smeltMap.get(block).copy());
            event.setCanceled(true);
        }
    }
}