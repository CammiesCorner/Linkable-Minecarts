package dev.cammiescorner.linkableminecarts;

import dev.cammiescorner.linkableminecarts.util.ConnectableMinecart;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkableMinecarts implements ModInitializer {
	public static final String MOD_ID = "linkableminecarts";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
			if(!level.isClientSide() && hitResult != null && hitResult.getEntity() instanceof AbstractMinecart absMinecart) {
				ItemStack stack = player.getItemInHand(hand);

				if(stack.is(ItemTags.CHAINS) && absMinecart instanceof ConnectableMinecart minecart) {

				}
			}

			return InteractionResult.PASS;
		});
	}
}