package net.hastiagames.reenchanted.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;

public class TreeFellerEnchantment extends Enchantment {
	private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("reenchanted_tree_feller", item -> Ingredient
			.of(new ItemStack(Items.WOODEN_AXE), new ItemStack(Items.STONE_AXE), new ItemStack(Items.IRON_AXE), new ItemStack(Items.GOLDEN_AXE), new ItemStack(Items.DIAMOND_AXE), new ItemStack(Items.NETHERITE_AXE)).test(new ItemStack(item)));

	public TreeFellerEnchantment() {
		super(Enchantment.Rarity.COMMON, ENCHANTMENT_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
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
}