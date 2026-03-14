/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.hastiagames.reenchanted as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.hastiagames.reenchanted;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MagneticCode {
	public MagneticCode() {
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		new MagneticCode();
	}

	@Mod.EventBusSubscriber
	private static class MagneticCodeForgeBusEvents {

		@SubscribeEvent
		public static void serverLoad(ServerStartingEvent event) {
		}

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void clientLoad(FMLClientSetupEvent event) {
		}

		/**
		 * Magnetic helmet behaviour
		 */
		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

			Player player = event.player;
			Level level = player.level();

			if (level.isClientSide)
				return;

			// casque
			ItemStack helmet = player.getInventory().armor.get(3);

			if (helmet.isEmpty())
				return;

			int magneticLevel = EnchantmentHelper.getItemEnchantmentLevel(
				net.hastiagames.reenchanted.init.ReenchantedModEnchantments.MAGNETIC.get(),
				helmet
			);

			if (magneticLevel <= 0)
				return;

			double radius = 4 + (magneticLevel * 2);

			AABB area = player.getBoundingBox().inflate(radius);

			List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, area);

			for (ItemEntity item : items) {

				Vec3 direction = player.position().subtract(item.position()).normalize();

				item.setDeltaMovement(direction.scale(0.25));
			}
		}
	}
}