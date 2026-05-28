package dev.cammiescorner.linkableminecarts;

import dev.cammiescorner.linkableminecarts.init.LinkableMinecartsComponents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
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
			if(!level.isClientSide() && player instanceof ServerPlayer serverPlayer && player.isCrouching() && entity instanceof AbstractMinecart minecartA) {
				ItemStack stack = player.getItemInHand(hand);

				if(stack.is(ItemTags.CHAINS)) {
					var minecartB = serverPlayer.linkableminecarts$getMinecart();

					if(minecartB != null) {
						var trainA = minecartA.getComponent(LinkableMinecartsComponents.MINECART_COMPONENT).getOrCreateTrain();
						var trainB = minecartB.getComponent(LinkableMinecartsComponents.MINECART_COMPONENT).getOrCreateTrain();
						var stackCopy = stack.copy();

						if(trainA != trainB) {
							if(trainA.merge(trainB, minecartA, minecartB, stackCopy.split(1))) {
								if(!player.isCreative()) player.setItemInHand(hand, stackCopy);
							}
							else {
								serverPlayer.sendOverlayMessage(Component.literal("Cannot link the middle of a train to a new train!").withStyle(ChatFormatting.RED));
							}
						}

						serverPlayer.linkableminecarts$setMinecart(null);
					}
					else {
						serverPlayer.linkableminecarts$setMinecart(minecartA);
					}
				}
			}

			return InteractionResult.PASS;
		});
	}

	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}
}