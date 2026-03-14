package net.hastiagames.reenchanted.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;

import net.hastiagames.reenchanted.init.ReenchantedModEnchantments;

import java.util.List;

public class ShockwaveEnchantment extends Enchantment {
	private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("reenchanted_shockwave",
			item -> Ingredient
					.of(new ItemStack(Items.WOODEN_SWORD), new ItemStack(Items.STONE_SWORD), new ItemStack(Items.IRON_SWORD), new ItemStack(Items.GOLDEN_SWORD), new ItemStack(Items.DIAMOND_SWORD), new ItemStack(Items.NETHERITE_SWORD),
							new ItemStack(Items.WOODEN_AXE), new ItemStack(Items.STONE_AXE), new ItemStack(Items.IRON_AXE), new ItemStack(Items.GOLDEN_AXE), new ItemStack(Items.DIAMOND_AXE), new ItemStack(Items.NETHERITE_AXE))
					.test(new ItemStack(item)));

	public ShockwaveEnchantment() {
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
		return super.checkCompatibility(enchantment) && !List.of(ReenchantedModEnchantments.IMPACT.get()).contains(enchantment);
	}

	@Override
	public boolean isDiscoverable() {
		return false;
	}
}