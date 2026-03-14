package net.hastiagames.reenchanted;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

@net.minecraftforge.fml.common.Mod.EventBusSubscriber(bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.FORGE)
public class ImpactCode {

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getTarget() instanceof LivingEntity)) return;

        Player player = (Player) event.getEntity();
        LivingEntity target = (LivingEntity) event.getTarget();

        int impactLevel = EnchantmentHelper.getItemEnchantmentLevel(
                net.hastiagames.reenchanted.init.ReenchantedModEnchantments.IMPACT.get(),
                player.getMainHandItem()
        );

        if (impactLevel <= 0) return;

        // Effet supplémentaire (exemple : faibles dégâts de poison / lentement)
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * impactLevel, 0));

        // Particules en cercle autour de la cible
        double radius = 1.0;
        for (int i = 0; i < 20; i++) {
            double angle = 2 * Math.PI * i / 20;
            double dx = radius * Math.cos(angle);
            double dz = radius * Math.sin(angle);
            target.level().addParticle(
                    ParticleTypes.CRIT, 
                    target.getX() + dx, 
                    target.getY() + 0.5, 
                    target.getZ() + dz, 
                    0, 0.05, 0
            );
        }

        // Son léger
        target.level().playSound(
                null, 
                target.getX(), target.getY(), target.getZ(), 
                SoundEvents.PLAYER_ATTACK_CRIT, 
                SoundSource.PLAYERS, 
                0.5f, 1.0f
        );
    }
}