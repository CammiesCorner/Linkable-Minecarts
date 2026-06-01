package dev.cammiescorner.linkableminecarts.component.level;

import dev.cammiescorner.linkableminecarts.util.Train;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.*;

public class TrainsComponent implements ServerTickingComponent {
	private final Map<UUID, Train> trains = new HashMap<>();
	private final Level level;

	public TrainsComponent(Level level) {
		this.level = level;
	}

	@Override
	public void serverTick() {
		for(Train train : trains.values()) {
			var velocity = Vec3.ZERO;
			var minecarts = train.viewMinecarts().stream().map(minecartEntityReference -> minecartEntityReference.getEntity(level, AbstractMinecart.class)).toList();

			if(minecarts.stream().anyMatch(Objects::isNull))
				continue;

			for(AbstractMinecart minecart : minecarts)
				velocity.add(minecart.getDeltaMovement());

			velocity.scale(1f / train.viewMinecarts().size());

			for(AbstractMinecart minecart : minecarts)
				minecart.setDeltaMovement(velocity);
		}
	}

	@Override
	public void readData(ValueInput readView) {
		this.trains.clear();

		for(Train train : readView.read("Trains", Train.CODEC.listOf()).orElse(List.of()))
			this.trains.put(train.uuid, train);
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.store("Trains", Train.CODEC.listOf(), trains.values().stream().toList());
	}

	@Nullable
	public Train getTrain(UUID uuid) {
		return trains.get(uuid);
	}

	public void addTrain(Train train) {
		trains.put(train.uuid, train);
	}

	public void removeTrain(Train train) {
		trains.remove(train.uuid);
	}
}
