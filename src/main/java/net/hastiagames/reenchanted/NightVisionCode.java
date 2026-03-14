package net.hastiagames.reenchanted;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;

@Mod.EventBusSubscriber
public class NightVisionCode {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        Player player = event.player;

        // Casque équipé
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

        Enchantment nightVision = ForgeRegistries.ENCHANTMENTS.getValue(
                new ResourceLocation("reenchanted", "night_vision")
        );

        if (nightVision == null) return;

        int level = EnchantmentHelper.getItemEnchantmentLevel(nightVision, helmet);

        if (level > 0) {

            // ajoute vision nocturne (durée courte pour éviter le clignotement)
            player.addEffect(new MobEffectInstance(
                    MobEffects.NIGHT_VISION,
                    220,
                    0,
                    true,
                    false,
                    false
            ));

        } else {

            // retire l'effet si présent
            if (player.hasEffect(MobEffects.NIGHT_VISION)) {
                player.removeEffect(MobEffects.NIGHT_VISION);
            }

        }
    }
}