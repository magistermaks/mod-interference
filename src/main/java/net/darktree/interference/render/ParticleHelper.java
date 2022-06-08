package net.darktree.interference.render;

import net.darktree.interference.Interference;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParticleHelper {

	private final static HashMap<Identifier, List<Identifier>> particleSprites = Interference.getClient(new HashMap<>());

	/**
	 * Register particle without messing with JSON
	 * This should be called from the common initializer or the static block
	 */
	public static DefaultParticleType register(Identifier particle, boolean always, Identifier... sprites) {
		if (particleSprites != null) {
			ArrayList<Identifier> paths = new ArrayList<>();

			for (Identifier sprite : sprites) {
				paths.add(new Identifier(sprite.getNamespace(), "particle/" + sprite.getPath()));
			}

			particleSprites.put(particle, paths);
		}

		return Registry.register(Registry.PARTICLE_TYPE, particle, FabricParticleTypes.simple(always));
	}

	/**
	 * Register a factory using the {@link ParticleHelper#factoryOf} to create the factory
	 */
	@ApiStatus.Experimental
	public static void registerFactory(DefaultParticleType type, ParticleConstructor<DefaultParticleType> constructor) {
		ParticleFactoryRegistry.getInstance().register(type, factoryOf(constructor));
	}

	/**
	 * Create a factory for a particle given its constructor
	 */
	@ApiStatus.Experimental
	public static <T extends ParticleEffect> ParticleFactoryRegistry.PendingParticleFactory<T> factoryOf(ParticleConstructor<T> constructor) {
		return (sprites) -> (T type, ClientWorld world, double d, double e, double f, double g, double h, double i) -> constructor.create(world, d, e, f, g, h, i);
	}

	@ApiStatus.Experimental
	public interface ParticleConstructor<T> {
		Particle create(ClientWorld world, double x, double y, double z, double vx, double vy, double vz);
	}

	@ApiStatus.Internal
	public static List<Identifier> getSpritesAndForget(Identifier identifier) {
		assert particleSprites != null;
		return particleSprites.remove(identifier);
	}
}
