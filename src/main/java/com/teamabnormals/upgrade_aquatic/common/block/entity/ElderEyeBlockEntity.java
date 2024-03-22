package com.teamabnormals.upgrade_aquatic.common.block.entity;

import com.teamabnormals.upgrade_aquatic.common.block.ElderEyeBlock;
import com.teamabnormals.upgrade_aquatic.core.registry.UABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.stream.IntStream;

public class ElderEyeBlockEntity extends BlockEntity {

	public ElderEyeBlockEntity(BlockPos pos, BlockState state) {
		super(UABlockEntityTypes.ELDER_EYE.get(), pos, state);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, ElderEyeBlockEntity elderEye) {
		if (level.getGameTime() % 4 == 0 && !level.isClientSide) {
			if (!(state.getBlock() instanceof ElderEyeBlock)) return;

			Direction facing = state.getValue(ElderEyeBlock.FACING);
			AABB bb = new AABB(0, 0, 0, 1, 1, 1).move(pos.relative(facing)).expandTowards(facing.getStepX() * calcRange(level, pos, facing), facing.getStepY() * calcRange(level, pos, facing), facing.getStepZ() * calcRange(level, pos, facing));
			List<Entity> entities = level.getEntitiesOfClass(Entity.class, bb);

			int entityCount = entities.size();
			boolean hasEntity = entityCount > 0;

			if (hasEntity) {

				for (Entity entity : entities) {
					if (!(entity instanceof LivingEntity) || entity instanceof ArmorStand) {
						entityCount--;
					}

					if (entityCount <= 0) {
						hasEntity = false;
					}

					int[] posCheck = {
							facing.getStepX() * (Mth.floor(entity.getX()) - pos.getX()),
							facing.getStepY() * (Mth.floor(entity.getY()) - pos.getY()),
							facing.getStepZ() * (Mth.floor(entity.getZ()) - pos.getZ())
					};

					for (int b = 1; b < Math.abs(IntStream.of(posCheck).sum()); b++) {
						if (!level.getBlockState(pos.relative(facing, b)).isAir()) {
							if (level.getBlockState(pos.relative(facing, b)).blocksMotion()) {
								entityCount--;
								if (entityCount <= 0) {
									hasEntity = false;
									break;
								}
							}
						}
					}
				}
			}

			if (state.getValue(ElderEyeBlock.POWERED) != hasEntity && state.getValue(ElderEyeBlock.ACTIVE)) {
				level.setBlockAndUpdate(pos, state.setValue(ElderEyeBlock.POWERED, hasEntity));
				((ElderEyeBlock) state.getBlock()).updateRedstoneNeighbors(state, level, pos);
			}
		}
	}

	public static int calcRange(Level level, BlockPos pos, Direction direction) {
		int i;
		for (i = 1; i < 13; i++) {
			if (level.getBlockState(pos.relative(direction, i)).isAir()) {
				if (level.getBlockState(pos.relative(direction, i)).is(Blocks.WATER)) {
					break;
				}
			}
		}
		return i;
	}

}
