package net.hastiagames.reenchanted.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;

public class NightVisionEnchantment extends Enchantment {
	private static final EnchantmentCategory ENCHANTMENT_CATEGORY = EnchantmentCategory.create("reenchanted_night_vision", item -> Ingredient.of(new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.CHAINMAIL_HELMET),
			new ItemStack(Items.TURTLE_HELMET), new ItemStack(Items.IRON_HELMET), new ItemStack(Items.GOLDEN_HELMET), new ItemStack(Items.DIAMOND_HELMET), new ItemStack(Items.NETHERITE_HELMET)).test(new ItemStack(item)));

	public NightVisionEnchantment() {
		super(Enchantment.Rarity.COMMON, ENCHANTMENT_CATEGORY, new EquipmentSlot[]{EquipmentSlot.HEAD});
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