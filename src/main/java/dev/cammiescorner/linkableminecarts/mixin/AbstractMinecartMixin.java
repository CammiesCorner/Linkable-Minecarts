package dev.cammiescorner.linkableminecarts.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.cammiescorner.linkableminecarts.init.MinecartsComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractMinecart.class)
public abstract class AbstractMinecartMixin extends VehicleEntity {
	public AbstractMinecartMixin(EntityType<?> type, Level level) { super(type, level); }

	@WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/minecart/AbstractMinecart;computeSpeed()V"))
	private boolean averageSpeed(AbstractMinecart instance) {
		var component = getComponent(MinecartsComponents.MINECART_COMPONENT);
		var train = component.getTrain();

		return train == null || train.viewMinecarts().size() == 1;
	}
}
