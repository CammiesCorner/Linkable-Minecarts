package dev.cammiescorner.linkableminecarts;

import dev.cammiescorner.linkableminecarts.init.MinecartsComponents;
import dev.cammiescorner.linkableminecarts.init.MinecartsItems;
import dev.cammiescorner.linkableminecarts.init.MinecartsTags;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.MinecartSpawner;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkableMinecarts implements ModInitializer {
	public static final String MOD_ID = "linkableminecarts";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MinecartsItems.init();

		PolymerResourcePackUtils.addModAssets(MOD_ID);
		PolymerResourcePackUtils.markAsRequired();

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(output -> {
			output.insertAfter(Items.TNT_MINECART, MinecartsItems.SPAWNER_MINECART);
		});

		ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
			if(entity instanceof ItemEntity itemEntity) {
				if(itemEntity.getItem().is(MinecartsTags.MINECARTS)) {
					var stack = itemEntity.getItem();

					if(stack.getOrDefault(DataComponents.MAX_STACK_SIZE, 0) < 16)
						stack.set(DataComponents.MAX_STACK_SIZE, 16);

					itemEntity.setItem(stack);
				}
			}
		});

		UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
			if(!level.isClientSide() && player instanceof ServerPlayer serverPlayer && entity instanceof AbstractMinecart minecartA) {
				ItemStack stack = player.getItemInHand(hand);
				var type = SpawnEggItem.getType(stack);

				// Modify spawner minecart
				if(minecartA instanceof MinecartSpawner spawnerMinecart && type != null) {
					spawnerMinecart.getSpawner().setEntityId(type, level, level.getRandom(), spawnerMinecart.blockPosition());
					stack.shrink(1);

					return InteractionResult.SUCCESS;
				}

				// Link minecarts with chains
				if(player.isCrouching() && stack.is(ItemTags.CHAINS)) {
					var minecartB = serverPlayer.linkableminecarts$getMinecart();

					if(minecartB != null) {
						var trainA = minecartA.getComponent(MinecartsComponents.MINECART_COMPONENT).getOrCreateTrain();
						var trainB = minecartB.getComponent(MinecartsComponents.MINECART_COMPONENT).getOrCreateTrain();
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

					return InteractionResult.SUCCESS;
				}
			}

			return InteractionResult.PASS;
		});
	}

	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}
}