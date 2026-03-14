package net.hastiagames.reenchanted;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;

@Mod.EventBusSubscriber
public class AutoReplantCode {

    @SubscribeEvent
    public static void onBlockBreak(BreakEvent event) {

        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();

        Enchantment autoReplant = ForgeRegistries.ENCHANTMENTS.getValue(
                new ResourceLocation("reenchanted", "auto_replant")
        );

        if (autoReplant == null) return;

        // Vérifie toutes les armures du joueur
        int level = EnchantmentHelper.getEnchantmentLevel(autoReplant, player);

        if (level <= 0) return;

        Level world = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();

        if (!(state.getBlock() instanceof CropBlock crop)) return;

        // Vérifie que la culture est mature
        if (!crop.isMaxAge(state)) return;

        // Drop normal
        world.destroyBlock(pos, true, player);

        // replante
        world.setBlock(pos, crop.getStateForAge(0), 3);

        event.setCanceled(true);
    }
}