package net.hastiagames.reenchanted;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber
public class ShockwaveCode extends Enchantment {

    public ShockwaveCode(Rarity rarityIn, EquipmentSlot... slots) {
        super(rarityIn, EnchantmentCategory.WEAPON, slots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    private double getRadiusByLevel(int level) {
        switch(level) {
            case 1: return 2.0D; // rayon 2 blocs
            case 2: return 3.0D; // rayon 3 blocs
            case 3: return 4.0D; // rayon 4 blocs
            default: return 2.0D;
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return; 
        Player player = (Player) event.getEntity(); 

        Level world = ((LivingEntity) player).level(); 

        ItemStack weapon = player.getMainHandItem();
        if (!(weapon.getItem() instanceof SwordItem || weapon.getItem() instanceof AxeItem)) return;

        Enchantment shockwave = ForgeRegistries.ENCHANTMENTS.getValue(
                new net.minecraft.resources.ResourceLocation("reenchanted", "shockwave")
        );
        int level = weapon.getEnchantmentLevel(shockwave);
        if (level == 0) return;

        double radius = new ShockwaveCode(Rarity.UNCOMMON, EquipmentSlot.MAINHAND).getRadiusByLevel(level);

        // Zone autour du joueur
        AABB area = player.getBoundingBox().inflate(radius, 1.0D, radius);

        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, area, e -> e != player);

        for (LivingEntity target : entities) {
            target.hurt(player.damageSources().playerAttack(player), 2.0F * level);
        }

        // --- Ajout des particules ---
        for (int i = 0; i < 20; i++) {
            double offsetX = (world.random.nextDouble() - 0.5D) * radius * 2;
            double offsetY = world.random.nextDouble() * 0.5D + 0.5D;
            double offsetZ = (world.random.nextDouble() - 0.5D) * radius * 2;
            world.addParticle(ParticleTypes.EXPLOSION, 
                              player.getX() + offsetX, 
                              player.getY() + offsetY, 
                              player.getZ() + offsetZ, 
                              0, 0.05, 0);
            world.addParticle(ParticleTypes.SWEEP_ATTACK, 
                              player.getX() + offsetX, 
                              player.getY() + 0.1, 
                              player.getZ() + offsetZ, 
                              0, 0.02, 0);
        }

        // --- Ajout d’un son ---
        world.playSound(null, 
                        player.getX(), player.getY(), player.getZ(), 
                        SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 
                        0.5F, 1.0F);

        // Optionnel : notifier les entités autour d’un game event (pour interactions Redstone ou avancées)
        world.gameEvent(player, GameEvent.ENTITY_INTERACT, player.blockPosition());
    }
}