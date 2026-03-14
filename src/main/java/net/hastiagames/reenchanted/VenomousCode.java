package net.hastiagames.reenchanted;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@net.minecraftforge.fml.common.Mod.EventBusSubscriber(bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.FORGE)
public class VenomousCode {

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        // Vérification classique des types
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getTarget() instanceof LivingEntity)) return;

        Player player = (Player) event.getEntity();
        LivingEntity target = (LivingEntity) event.getTarget();

        ItemStack weapon = player.getMainHandItem();

        // Récupération du niveau de l'enchantement Venomous
        int venomLevel = EnchantmentHelper.getItemEnchantmentLevel(
                net.hastiagames.reenchanted.init.ReenchantedModEnchantments.VENOMOUS.get(),
                weapon
        );

        if (venomLevel <= 0) return;

        // Application de l'effet Poison
        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.POISON,
                60 * venomLevel,  // durée en ticks
                venomLevel - 1    // amplifier
        ));
    }
}