/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.hastiagames.reenchanted.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.enchantment.Enchantment;

import net.hastiagames.reenchanted.enchantment.*;
import net.hastiagames.reenchanted.ReenchantedMod;

public class ReenchantedModEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ReenchantedMod.MODID);
	public static final RegistryObject<Enchantment> MAGNETIC = REGISTRY.register("magnetic", () -> new MagneticEnchantment());
	public static final RegistryObject<Enchantment> PHOTOSYNTHESIS = REGISTRY.register("photosynthesis", () -> new PhotosynthesisEnchantment());
	public static final RegistryObject<Enchantment> IMPACT = REGISTRY.register("impact", () -> new ImpactEnchantment());
	public static final RegistryObject<Enchantment> VENOMOUS = REGISTRY.register("venomous", () -> new VenomousEnchantment());
	public static final RegistryObject<Enchantment> THERMAL = REGISTRY.register("thermal", () -> new ThermalEnchantment());
	public static final RegistryObject<Enchantment> VEIN_MINER = REGISTRY.register("vein_miner", () -> new VeinMinerEnchantment());
	public static final RegistryObject<Enchantment> TREE_FELLER = REGISTRY.register("tree_feller", () -> new TreeFellerEnchantment());
	public static final RegistryObject<Enchantment> AUTO_REPLANT = REGISTRY.register("auto_replant", () -> new AutoReplantEnchantment());
	public static final RegistryObject<Enchantment> STEP_ASSIST = REGISTRY.register("step_assist", () -> new StepAssistEnchantment());
	public static final RegistryObject<Enchantment> NIGHT_VISION = REGISTRY.register("night_vision", () -> new NightVisionEnchantment());
	public static final RegistryObject<Enchantment> DOUBLE_JUMP = REGISTRY.register("double_jump", () -> new DoubleJumpEnchantment());
	public static final RegistryObject<Enchantment> EXCAVATOR = REGISTRY.register("excavator", () -> new ExcavatorEnchantment());
	public static final RegistryObject<Enchantment> SHOCKWAVE = REGISTRY.register("shockwave", () -> new ShockwaveEnchantment());
	public static final RegistryObject<Enchantment> FIRE_WALKER = REGISTRY.register("fire_walker", () -> new FireWalkerEnchantment());
	public static final RegistryObject<Enchantment> VAMPIRIC = REGISTRY.register("vampiric", () -> new VampiricEnchantment());
}