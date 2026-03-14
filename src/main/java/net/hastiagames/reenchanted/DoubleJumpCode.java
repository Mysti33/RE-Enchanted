package net.hastiagames.reenchanted;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;

@Mod.EventBusSubscriber(modid = "reenchanted")
public class DoubleJumpCode {

    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        // Vérifie que l'entité est un joueur
        if (!(event.getEntity() instanceof Player player)) return;

        // Récupère les bottes
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        // Récupère le niveau de l'enchant Double Jump sur les bottes
        Enchantment doubleJumpEnch = ForgeRegistries.ENCHANTMENTS.getValue(
                new ResourceLocation("reenchanted", "double_jump")
        );
        int level = EnchantmentHelper.getItemEnchantmentLevel(doubleJumpEnch, boots);

        // Si niveau > 0, augmente la hauteur du saut
        if (level > 0) {
            // Chaque level ajoute 0.2 au saut
            player.setDeltaMovement(
                player.getDeltaMovement().x,
                player.getDeltaMovement().y + 0.2 * level,
                player.getDeltaMovement().z
            );
        }
    }
}