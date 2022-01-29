package net.darktree.interference;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class AxeScrapeHelper {

	/**
	 * Call from {@link net.darktree.interference.api.AxeScrapeable#getScrapedState} to emulate the vanilla behavior of
	 * striping logs.
	 */
	public static Optional<BlockState> strip(World world, BlockPos pos, PlayerEntity player, BlockState state) {
		return from(world, pos, player, SoundEvents.ITEM_AXE_STRIP, state);
	}

	/**
	 * Call from {@link net.darktree.interference.api.AxeScrapeable#getScrapedState} to emulate the vanilla behavior of
	 * scraping oxidation from copper blocks.
	 */
	public static Optional<BlockState> scrape(World world, BlockPos pos, PlayerEntity player, BlockState state) {
		world.syncWorldEvent(player, 3005, pos, 0);
		return from(world, pos, player, SoundEvents.ITEM_AXE_SCRAPE, state);
	}

	/**
	 * Call from {@link net.darktree.interference.api.AxeScrapeable#getScrapedState} to emulate the vanilla behavior of
	 * scraping wax from copper blocks.
	 */
	public static Optional<BlockState> scrapeOff(World world, BlockPos pos, PlayerEntity player, BlockState state) {
		world.syncWorldEvent(player, 3004, pos, 0);
		return from(world, pos, player, SoundEvents.ITEM_AXE_WAX_OFF, state);
	}

	/**
	 * Call from {@link net.darktree.interference.api.AxeScrapeable#getScrapedState}.
	 * A simple helper for playing a sound effect.
	 */
	public static Optional<BlockState> from(World world, BlockPos pos, PlayerEntity player, SoundEvent effect, BlockState state) {
		world.playSound(player, pos, effect, SoundCategory.BLOCKS, 1.0F, 1.0F);
		return Optional.of(state);
	}

}
