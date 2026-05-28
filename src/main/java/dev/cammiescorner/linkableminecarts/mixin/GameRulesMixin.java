package dev.cammiescorner.linkableminecarts.mixin;

import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(GameRules.class)
public abstract class GameRulesMixin {
	@ModifyConstant(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=max_minecart_speed")), constant = @Constant(intValue = 8, ordinal = 0))
	private static int setMinecartSpeed(int constant) {
		return 64;
	}
}
