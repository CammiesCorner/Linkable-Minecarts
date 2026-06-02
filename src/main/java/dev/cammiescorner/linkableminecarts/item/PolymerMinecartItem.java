package dev.cammiescorner.linkableminecarts.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.component.TypedEntityData;

public class PolymerMinecartItem extends MinecartItem implements PolymerItem {
	public PolymerMinecartItem(EntityType<? extends AbstractMinecart> type, Properties properties) {
		super(type, properties.stacksTo(16).component(DataComponents.ENTITY_DATA, TypedEntityData.of(EntityType.PIG, new CompoundTag())));
	}

	@Override
	public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
		return Items.COMMAND_BLOCK_MINECART;
	}
}
