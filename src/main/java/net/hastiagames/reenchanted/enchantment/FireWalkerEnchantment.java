package net.hastiagames.reenchanted.enchantment;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.List;

public class FireWalkerEnchantment extends Enchantment {
	private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("reenchanted_fire_walker",
			item -> Ingredient.of(new ItemStack(Items.CHAINMAIL_BOOTS), new ItemStack(Items.IRON_BOOTS), new ItemStack(Items.GOLDEN_BOOTS), new ItemStack(Items.DIAMOND_BOOTS), new ItemStack(Items.NETHERITE_BOOTS)).test(new ItemStack(item)));

	public FireWalkerEnchantment() {
		super(Enchantment.Rarity.COMMON, ENCHANTMENT_CATEGORY, EquipmentSlot.values());
	}

	@Override
	public int getMinCost(int level) {
		return 1 + level * 10;
	}

	@Override
	public int getMaxCost(int level) {
		return 6 + level * 10;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		return super.checkCompatibility(enchantment) && !List.of(Enchantments.FROST_WALKER).contains(enchantment);
	}
}