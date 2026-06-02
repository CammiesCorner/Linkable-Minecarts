package dev.cammiescorner.linkableminecarts.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MinecartsTags {
	public static TagKey<Item> MINECARTS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "minecarts"));
}
