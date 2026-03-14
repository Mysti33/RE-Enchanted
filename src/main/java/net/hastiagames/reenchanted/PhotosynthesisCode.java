package net.hastiagames.reenchanted;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import net.minecraft.core.BlockPos;

public class PhotosynthesisCode {

    public PhotosynthesisCode() {
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        new PhotosynthesisCode();
    }

    @Mod.EventBusSubscriber
    private static class PhotosynthesisCodeForgeBusEvents {

        @SubscribeEvent
        public static void serverLoad(ServerStartingEvent event) {
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void clientLoad(FMLClientSetupEvent event) {
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

            Player player = event.player;
            Level level = player.level();

            if (level.isClientSide)
                return;

            // Vérifier si un objet équipé possède Photosynthesis
            int levelEnchant = 0;

            // Main principale
            levelEnchant = Math.max(levelEnchant,
                EnchantmentHelper.getItemEnchantmentLevel(
                    net.hastiagames.reenchanted.init.ReenchantedModEnchantments.PHOTOSYNTHESIS.get(),
                    player.getMainHandItem()
                )
            );

            // Offhand
            levelEnchant = Math.max(levelEnchant,
                EnchantmentHelper.getItemEnchantmentLevel(
                    net.hastiagames.reenchanted.init.ReenchantedModEnchantments.PHOTOSYNTHESIS.get(),
                    player.getOffhandItem()
                )
            );

            // Armure
            for (ItemStack armor : player.getArmorSlots()) {
                levelEnchant = Math.max(levelEnchant,
                    EnchantmentHelper.getItemEnchantmentLevel(
                        net.hastiagames.reenchanted.init.ReenchantedModEnchantments.PHOTOSYNTHESIS.get(),
                        armor
                    )
                );
            }

            if (levelEnchant <= 0)
                return;

            // Vérifier que le joueur est exposé au soleil
            BlockPos pos = player.blockPosition();

            if (!level.isDay())
                return;

            if (!level.canSeeSky(pos))
                return;

            // Réparer les objets toutes les 80 ticks (~4 secondes)
            if (player.tickCount % 80 != 0)
                return;

            // Réparer main principale
            ItemStack main = player.getMainHandItem();
            if (main.isDamageableItem() && main.getDamageValue() > 0)
                main.setDamageValue(main.getDamageValue() - 1);

            // Réparer main secondaire
            ItemStack off = player.getOffhandItem();
            if (off.isDamageableItem() && off.getDamageValue() > 0)
                off.setDamageValue(off.getDamageValue() - 1);

            // Réparer armure
            for (ItemStack armor : player.getArmorSlots()) {
                if (armor.isDamageableItem() && armor.getDamageValue() > 0)
                    armor.setDamageValue(armor.getDamageValue() - 1);
            }
        }
    }
}