package dev.cammiescorner.linkableminecarts.component.entity;

import dev.cammiescorner.linkableminecarts.init.LinkableMinecartsComponents;
import dev.cammiescorner.linkableminecarts.util.Train;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

import java.util.List;
import java.util.UUID;

public class MinecartComponent implements CardinalComponent {
	private final AbstractMinecart entity;
	private UUID trainId;

	public MinecartComponent(AbstractMinecart entity) {
		this.entity = entity;
	}

	@Override
	public void readData(ValueInput readView) {
		trainId = readView.read("TrainId", UUIDUtil.STRING_CODEC).orElse(null);
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.storeNullable("TrainId", UUIDUtil.STRING_CODEC, trainId);
	}

	public Train getOrCreateTrain() {
		if(trainId == null) {
			trainId = UUID.randomUUID();
			var train = new Train(trainId, List.of(EntityReference.of(entity)), List.of());

			entity.level().getComponent(LinkableMinecartsComponents.TRAINS_COMPONENT).addTrain(train);

			return train;
		}

		return getTrain();
	}

	@Nullable
	public Train getTrain() {
		if(trainId == null)
			return null;

		return entity.level().getComponent(LinkableMinecartsComponents.TRAINS_COMPONENT).getTrain(trainId);
	}

	public void setTrain(Train train) {
		trainId = train.uuid;
	}
}
