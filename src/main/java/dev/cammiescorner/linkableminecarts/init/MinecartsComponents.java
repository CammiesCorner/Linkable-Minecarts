package dev.cammiescorner.linkableminecarts.init;

import dev.cammiescorner.linkableminecarts.LinkableMinecarts;
import dev.cammiescorner.linkableminecarts.component.entity.MinecartComponent;
import dev.cammiescorner.linkableminecarts.component.level.TrainsComponent;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v8.component.CardinalComponent;
import org.ladysnake.cca.api.v8.level.LevelComponentFactoryRegistry;
import org.ladysnake.cca.api.v8.level.LevelComponentInitializer;

public class MinecartsComponents implements EntityComponentInitializer, LevelComponentInitializer {
	public static final ComponentKey<MinecartComponent> MINECART_COMPONENT = createComponent("minecart", MinecartComponent.class);
	public static final ComponentKey<TrainsComponent> TRAINS_COMPONENT = createComponent("trains", TrainsComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(AbstractMinecart.class, MINECART_COMPONENT, MinecartComponent::new);
	}

	@Override
	public void registerLevelComponentFactories(LevelComponentFactoryRegistry registry) {
		registry.register(TRAINS_COMPONENT, TrainsComponent::new);
	}

	private static <T extends CardinalComponent> ComponentKey<T> createComponent(String name, Class<T> component) {
		return ComponentRegistry.getOrCreate(LinkableMinecarts.id(name), component);
	}
}
