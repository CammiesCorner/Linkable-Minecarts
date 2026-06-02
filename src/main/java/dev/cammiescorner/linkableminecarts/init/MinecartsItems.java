package dev.cammiescorner.linkableminecarts.init;

import dev.cammiescorner.linkableminecarts.LinkableMinecarts;
import dev.cammiescorner.linkableminecarts.item.PolymerMinecartItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class MinecartsItems {
	public static final Item SPAWNER_MINECART = register("spawner_minecart", properties -> new PolymerMinecartItem(EntityType.SPAWNER_MINECART, properties));

	public static <T extends Item> T register(String name, Function<Item.Properties, T> factory) {
		return register(name, factory, new Item.Properties());
	}

	public static <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
		var resourceKey = ResourceKey.create(Registries.ITEM, LinkableMinecarts.id(name));

		return Registry.register(BuiltInRegistries.ITEM, resourceKey, factory.apply(properties.setId(resourceKey)));
	}

	public static void init() {
		LinkableMinecarts.LOGGER.info("Registering Items");
	}
}
