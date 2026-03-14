package net.hastiagames.reenchanted;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class VampiricCode extends Enchantment {

    public VampiricCode(Rarity rarityIn, EquipmentSlot... slots) {
        super(rarityIn, EnchantmentCategory.WEAPON, slots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    private double getHealRatioByLevel(int level) {
        switch(level) {
            case 1: return 0.10; // 10%
            case 2: return 0.15; // 15%
            case 3: return 0.20; // 20%
            default: return 0.10;
        }
    }

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        // Récupère le joueur à partir de l'entité
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        ItemStack weapon = player.getMainHandItem();
        if (!(weapon.getItem() instanceof SwordItem || weapon.getItem() instanceof AxeItem)) return;

        // Récupère l'enchant Vampiric
        Enchantment vampiric = ForgeRegistries.ENCHANTMENTS.getValue(
                new net.minecraft.resources.ResourceLocation("reenchanted", "vampiric")
        );

        int level = weapon.getEnchantmentLevel(vampiric);
        if (level == 0) return;

        // Récupère l'entité attaquée
        if (!(event.getTarget() instanceof LivingEntity target)) return;

        // Dégâts infligés
        float damage = (float) player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getValue();

        // Calcul de la régénération
        double healRatio = new VampiricCode(Rarity.UNCOMMON, EquipmentSlot.MAINHAND).getHealRatioByLevel(level);
        float healAmount = (float) (damage * healRatio);

        player.heal(healAmount);
    }
}