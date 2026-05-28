package dev.cammiescorner.linkableminecarts.util;

import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import org.jspecify.annotations.Nullable;

public interface ServerPlayerExt {
	@Nullable
	default AbstractMinecart linkableminecarts$getMinecart() {
		throw new AssertionError();
	}

	default void linkableminecarts$setMinecart(AbstractMinecart minecart) {
		throw new AssertionError();
	}
}
