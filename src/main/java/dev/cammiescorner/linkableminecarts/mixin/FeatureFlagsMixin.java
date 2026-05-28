package dev.cammiescorner.linkableminecarts.mixin;

import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FeatureFlags.class)
public class FeatureFlagsMixin {
	@Mutable @Shadow @Final public static FeatureFlagSet DEFAULT_FLAGS;
	@Shadow @Final public static FeatureFlag MINECART_IMPROVEMENTS;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void modifyDefaultFeatures(CallbackInfo ci) {
		DEFAULT_FLAGS = DEFAULT_FLAGS.join(FeatureFlagSet.of(MINECART_IMPROVEMENTS));
	}
}
