package net.hastiagames.reenchanted;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;

@Mod.EventBusSubscriber
public class StepAssistCode {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        Player player = event.player;

        // Bottes équipées
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        Enchantment stepAssist = ForgeRegistries.ENCHANTMENTS.getValue(
                new ResourceLocation("reenchanted", "step_assist")
        );

        if (stepAssist == null) return;

        int level = EnchantmentHelper.getItemEnchantmentLevel(stepAssist, boots);

        if (level > 0) {
            player.setMaxUpStep(1.0F);
        } else {
            // valeur normale vanilla
            player.setMaxUpStep(0.6F);
        }
    }
}