package dev.cammiescorner.linkableminecarts.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.linkableminecarts.init.LinkableMinecartsComponents;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Train {
	public static final Codec<Train> CODEC = RecordCodecBuilder.create(trainInstance -> trainInstance.group(
			UUIDUtil.STRING_CODEC.fieldOf("uuid").forGetter(Train::getUuid),
			EntityReference.<AbstractMinecart>codec().listOf().fieldOf("minecarts").forGetter(Train::viewMinecarts),
			ItemStack.CODEC.listOf().fieldOf("connections").forGetter(Train::viewConnections)
	).apply(trainInstance, Train::new));
	private final List<EntityReference<AbstractMinecart>> minecarts = new ArrayList<>();
	private final List<ItemStack> connections = new ArrayList<>();
	public final UUID uuid;

	public Train(UUID uuid, List<EntityReference<AbstractMinecart>> minecarts, List<ItemStack> connections) {
		this.uuid = uuid;
		this.minecarts.addAll(minecarts);
		this.connections.addAll(connections);
	}

	public List<EntityReference<AbstractMinecart>> viewMinecarts() {
		return Collections.unmodifiableList(minecarts);
	}

	public List<ItemStack> viewConnections() {
		return Collections.unmodifiableList(connections);
	}

	public void addMinecarts(int index, EntityReference<AbstractMinecart>... minecarts) {
		for(int i = 0; i < minecarts.length; i++) {
			var minecart = minecarts[i];

			if(!this.minecarts.contains(minecart))
				this.minecarts.add(index + i, minecart);
		}
	}

	public void removeMinecarts(List<EntityReference<AbstractMinecart>> minecarts) {
		this.minecarts.removeAll(minecarts);
	}


	public boolean merge(Train trainB, AbstractMinecart minecartA, AbstractMinecart minecartB, ItemStack stack) {
		var trainAStart = minecarts.getFirst().matches(minecartA);
		var trainBStart = trainB.minecarts.getFirst().matches(minecartB);

		if((!trainAStart && !minecarts.getLast().matches(minecartA)) || (!trainBStart && !trainB.minecarts.getLast().matches(minecartB)))
			return false;

		for(EntityReference<AbstractMinecart> reference : trainB.minecarts) {
			var minecart = reference.getEntity(minecartA.level(), AbstractMinecart.class);

			minecart.getComponent(LinkableMinecartsComponents.MINECART_COMPONENT).setTrain(this);
		}

		minecarts.addAll(trainAStart ? 0 : minecarts.size(), trainBStart ? trainB.minecarts : trainB.minecarts.reversed());
		connections.add(trainAStart ? 0 : connections.size(), stack);
		connections.addAll(trainAStart ? 0 : connections.size(), trainBStart ? trainB.connections : trainB.connections.reversed());
		minecartB.level().getComponent(LinkableMinecartsComponents.TRAINS_COMPONENT).removeTrain(trainB);

		return true;
	}

	public final Train split(int index, Level level, @Nullable Entity thrower) {
		var newMinecarts = this.minecarts.subList(index, this.minecarts.size());
		var newConnections = this.connections.subList(index, this.connections.size());

		var train = new Train(UUID.randomUUID(), newMinecarts, newConnections);

		this.minecarts.removeAll(newMinecarts);
		this.connections.removeAll(newConnections);

		ItemStack stack = this.connections.removeLast();
		AbstractMinecart minecartA = this.minecarts.getLast().getEntity(level, AbstractMinecart.class);
		AbstractMinecart minecartB = newMinecarts.getFirst().getEntity(level, AbstractMinecart.class);
		Vec3 pos = minecartA.position().add(minecartB.position().subtract(minecartA.position()).scale(0.5));
		ItemEntity item = new ItemEntity(level, pos.x, pos.y, pos.z, stack);

		item.setThrower(thrower);
		item.setDefaultPickUpDelay();
		level.addFreshEntity(item);

		return train;
	}

	public UUID getUuid() {
		return uuid;
	}

	public boolean isComplete() {
		// TODO check if any minecarts are unloaded
		return false;
	}
}
