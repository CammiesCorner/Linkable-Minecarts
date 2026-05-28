package dev.cammiescorner.linkableminecarts.mixin;

import com.mojang.authlib.GameProfile;
import dev.cammiescorner.linkableminecarts.util.ServerPlayerExt;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements ServerPlayerExt {
	@Unique private EntityReference<AbstractMinecart> minecart;

	public ServerPlayerMixin(Level level, GameProfile gameProfile) {
		super(level, gameProfile);
	}

	@Override
	public @Nullable AbstractMinecart linkableminecarts$getMinecart() {
		return minecart != null ? minecart.getEntity(level(), AbstractMinecart.class) : null;
	}

	@Override
	public void linkableminecarts$setMinecart(AbstractMinecart minecart) {
		this.minecart = EntityReference.of(minecart);
	}
}
