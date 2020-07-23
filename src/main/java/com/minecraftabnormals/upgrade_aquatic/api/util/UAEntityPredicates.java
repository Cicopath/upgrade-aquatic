package com.minecraftabnormals.upgrade_aquatic.api.util;

import java.util.function.Predicate;

import com.minecraftabnormals.upgrade_aquatic.common.entities.PikeEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;

public class UAEntityPredicates {

	public static final Predicate<Entity> IS_SPECTRAL = (p_200818_0_) -> {
		return ((PikeEntity) p_200818_0_).getPikeType() == 7 && !((PikeEntity) p_200818_0_).isHidingInPickerelweed();
	};
	
	public static final Predicate<Entity> IS_HIDING_IN_PICKERELWEED = (p_200818_0_) -> {
		return !((PikeEntity) p_200818_0_).isHidingInPickerelweed();
	};
	
	public static final Predicate<Entity> IS_CHILD = (entity) -> {
		return ((AnimalEntity) entity).isChild();
	};
	
}