package net.hastiagames.reenchanted;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber
public class TreeFellerCode extends Enchantment {

    public TreeFellerCode(Rarity rarityIn, EnchantmentCategory type, EquipmentSlot[] slots) {
        super(rarityIn, type, slots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakEvent event) {

        Object entity = event.getPlayer();
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;

        ItemStack tool = player.getMainHandItem();

        // fonctionne seulement avec une hache
        if (!(tool.getItem() instanceof AxeItem)) return;

        Enchantment treeFeller = ForgeRegistries.ENCHANTMENTS.getValue(
                new ResourceLocation("reenchanted", "tree_feller")
        );

        if (treeFeller == null) return;

        int level = EnchantmentHelper.getItemEnchantmentLevel(treeFeller, tool);
        if (level <= 0) return;

        Level levelWorld = (Level) event.getLevel();
        BlockPos startPos = event.getPos();
        BlockState state = event.getState();

        // vérifie si c'est une bûche
        if (!state.is(BlockTags.LOGS)) return;

        int limit = 16;

        if (level == 2) limit = 32;
        if (level >= 3) limit = 64;

        Set<BlockPos> visited = new HashSet<>();

        breakConnectedLogs(levelWorld, startPos, player, tool, visited, limit);
    }

    private static void breakConnectedLogs(Level world, BlockPos pos, Player player, ItemStack tool, Set<BlockPos> visited, int limit) {

        if (visited.size() >= limit) return;
        if (visited.contains(pos)) return;

        BlockState state = world.getBlockState(pos);

        if (!state.is(BlockTags.LOGS)) return;

        visited.add(pos);

        world.destroyBlock(pos, true, player);

        // consomme durabilité
        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));

        breakConnectedLogs(world, pos.north(), player, tool, visited, limit);
        breakConnectedLogs(world, pos.south(), player, tool, visited, limit);
        breakConnectedLogs(world, pos.east(), player, tool, visited, limit);
        breakConnectedLogs(world, pos.west(), player, tool, visited, limit);
        breakConnectedLogs(world, pos.above(), player, tool, visited, limit);
        breakConnectedLogs(world, pos.below(), player, tool, visited, limit);
    }
}