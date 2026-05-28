package dev.cammiescorner.linkableminecarts.util;

import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;

public interface ConnectableMinecart {
	MinecartConnection linkableminecarts$getMinecartConnection();

	void linkableminecarts$setConnectedCartA(EntityReference<AbstractMinecart> minecart);

	void linkableminecarts$setConnectedCartB(EntityReference<AbstractMinecart> minecart);
}
