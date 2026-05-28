package dev.cammiescorner.linkableminecarts.component.level;

import dev.cammiescorner.linkableminecarts.util.Train;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TrainsComponent implements CardinalComponent {
	private final Map<UUID, Train> trains = new HashMap<>();
	private final Level level;

	public TrainsComponent(Level level) {
		this.level = level;
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
		System.out.println("Get Train: " + uuid);
		return trains.get(uuid);
	}

	public void addTrain(Train train) {
		System.out.println("Add Train: " + train.uuid);
		trains.put(train.uuid, train);
	}

	public void removeTrain(Train train) {
		trains.remove(train.uuid);
	}
}
