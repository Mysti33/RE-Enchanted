package net.hastiagames.reenchanted.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;

public class StepAssistEnchantment extends Enchantment {
	private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("reenchanted_step_assist",
			item -> Ingredient.of(new ItemStack(Items.LEATHER_BOOTS), new ItemStack(Items.CHAINMAIL_BOOTS), new ItemStack(Items.IRON_BOOTS), new ItemStack(Items.GOLDEN_BOOTS), new ItemStack(Items.DIAMOND_BOOTS), new ItemStack(Items.NETHERITE_BOOTS))
					.test(new ItemStack(item)));

	public StepAssistEnchantment() {
		super(Enchantment.Rarity.COMMON, ENCHANTMENT_CATEGORY, new EquipmentSlot[]{EquipmentSlot.FEET});
	}

	@Override
	public int getMinCost(int level) {
		return 1 + level * 10;
	}

	@Override
	public int getMaxCost(int level) {
		return 6 + level * 10;
	}
}