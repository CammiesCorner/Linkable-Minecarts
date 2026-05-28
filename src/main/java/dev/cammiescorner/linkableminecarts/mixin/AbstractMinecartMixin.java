package dev.cammiescorner.linkableminecarts.mixin;

import dev.cammiescorner.linkableminecarts.util.ConnectableMinecart;
import dev.cammiescorner.linkableminecarts.util.MinecartConnection;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractMinecart.class)
public abstract class AbstractMinecartMixin extends VehicleEntity implements ConnectableMinecart {
	@Unique private final MinecartConnection minecartConnection = new MinecartConnection();

	protected AbstractMinecartMixin(EntityType<?> type, Level level) { super(type, level); }

	@Override
	public MinecartConnection linkableminecarts$getMinecartConnection() {
		return minecartConnection;
	}

	@Override
	public void linkableminecarts$setConnectedCartA(EntityReference<AbstractMinecart> minecart) {
		minecartConnection.setMinecartA(minecart);
	}

	@Override
	public void linkableminecarts$setConnectedCartB(EntityReference<AbstractMinecart> minecart) {
		minecartConnection.setMinecartB(minecart);
	}
}
