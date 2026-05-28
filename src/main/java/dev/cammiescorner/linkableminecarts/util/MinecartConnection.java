package dev.cammiescorner.linkableminecarts.util;

import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import org.jetbrains.annotations.Nullable;

public class MinecartConnection {
	private EntityReference<AbstractMinecart> minecartA = null;
	private EntityReference<AbstractMinecart> minecartB = null;

	@Nullable
	public EntityReference<AbstractMinecart> getMinecartA() {
		return minecartA;
	}

	@Nullable
	public EntityReference<AbstractMinecart> getMinecartB() {
		return minecartB;
	}

	public void setMinecartA(@Nullable EntityReference<AbstractMinecart> minecart) {
		this.minecartA = minecart;
	}

	public void setMinecartB(@Nullable EntityReference<AbstractMinecart> minecartB) {
		this.minecartB = minecartB;
	}
}
