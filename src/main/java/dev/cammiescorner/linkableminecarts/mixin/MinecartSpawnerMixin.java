package dev.cammiescorner.linkableminecarts.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.cammiescorner.linkableminecarts.init.MinecartsItems;
import net.minecraft.world.entity.vehicle.minecart.MinecartSpawner;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BaseSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecartSpawner.class)
public abstract class MinecartSpawnerMixin {
	@Shadow public abstract BaseSpawner getSpawner();

	@ModifyReturnValue(method = "getDropItem", at = @At("RETURN"))
	private Item changeDropItem(Item original) {
		return MinecartsItems.SPAWNER_MINECART;
	}

	@ModifyReturnValue(method = "getPickResult", at = @At("RETURN"))
	private ItemStack changePickItem(ItemStack original) {
		var stack = new ItemStack(MinecartsItems.SPAWNER_MINECART);

		// TODO save entity type data to item stack
//		stack.set(DataComponents.ENTITY_DATA, TypedEntityData.of(getSpawner().));

		return stack;
	}
}
