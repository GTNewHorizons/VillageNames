package astrotibs.villagenames.structure;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import astrotibs.villagenames.mixins.early.AccessorChunkProviderFlat;
import astrotibs.villagenames.mixins.early.AccessorChunkProviderGenerate;
import astrotibs.villagenames.mixins.early.AccessorChunkProviderHell;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.structure.MapGenStructure;

public class StructureRegistry {

	private static void addMapGen(Collection<MapGenStructure> output, MapGenStructure structure) {
		if (structure != null) {
			output.add(structure);
		}
	}

	private StructureRegistry() {
		ImmutableList.Builder<IStructureGenProvider> builder = ImmutableList.builder();

		builder.add(new IStructureGenProvider() {
			@Override
			public boolean canUseOnProvider(IChunkProvider provider) {
				return provider instanceof ChunkProviderGenerate;
			}

			@Override
			public Collection<MapGenStructure> listProviders(IChunkProvider provider) {
				var accessor = ((AccessorChunkProviderGenerate) provider);
				List<MapGenStructure> result = Lists.newArrayList();
				addMapGen(result, accessor.getStrongholdGenerator());
				addMapGen(result, accessor.getVillageGenerator());
				addMapGen(result, accessor.getMineshaftGenerator());
				addMapGen(result, accessor.getScatteredFeatureGenerator());
				return result;
			}
		});

		builder.add(new IStructureGenProvider() {
			@Override
			public boolean canUseOnProvider(IChunkProvider provider) {
				return provider instanceof ChunkProviderFlat;
			}

			@Override
			public Collection<MapGenStructure> listProviders(IChunkProvider provider) {
				var accessor = (AccessorChunkProviderFlat) provider;
				List<MapGenStructure> result = Lists.newArrayList();
				result.addAll(accessor.getStructureGenerators());
				return result;
			}
		});

		builder.add(new IStructureGenProvider() {
			@Override
			public boolean canUseOnProvider(IChunkProvider provider) {
				return provider instanceof ChunkProviderHell;
			}

			@Override
			public Collection<MapGenStructure> listProviders(IChunkProvider provider) {
				var accessor = (AccessorChunkProviderHell) provider;
				List<MapGenStructure> result = Lists.newArrayList();
				addMapGen(result, accessor.getGenNetherBridge());
				return result;
			}
		});
		providers = builder.build();
	}

	public final static StructureRegistry instance = new StructureRegistry();

	private List<IStructureNamer> names = Lists.newArrayList();

	private List<IStructureGenProvider> providers;

	private interface IStructureVisitor {
		public void visit(MapGenStructure structure);
	}

	private void visitStructures(WorldServer world, IStructureVisitor visitor) {
		ChunkProviderServer provider = world.theChunkProviderServer;
		IChunkProvider inner = provider.currentChunkProvider;

		if (inner != null) {
			for (IStructureGenProvider p : providers)
				if (p.canUseOnProvider(inner)) {
					for (MapGenStructure struct : p.listProviders(inner))
						visitor.visit(struct);
				}
		}
	}

	private String identifyStructure(MapGenStructure structure) {
		for (IStructureNamer n : names) {
			String name = n.identify(structure);
			if (!Strings.isNullOrEmpty(name)) return name;
		}
		return structure.func_143025_a();
	}

	public Map<String, ChunkPosition> getNearestStructures(final WorldServer world, final int x, final int y, final int z) {
		final ImmutableMap.Builder<String, ChunkPosition> result = ImmutableMap.builder();
		visitStructures(world, new IStructureVisitor() {
			@Override
			public void visit(MapGenStructure structure) {
				try {
					ChunkPosition structPos = structure.func_151545_a(world, x, y, z);

					if (structPos != null) {
						String structType = identifyStructure(structure);
						if (!Strings.isNullOrEmpty(structType)) result.put(structType, structPos);
					}
				} catch (IndexOutOfBoundsException e) {
					// bug in MC, just ignore
					// hopefully fixed by magic of ASM
				}
			}
		});

		return result.build();
	}

	public Set<ChunkPosition> getNearestInstance(final String name, final WorldServer world, final int x, final int y, final int z) {
		final ImmutableSet.Builder<ChunkPosition> result = ImmutableSet.builder();
		visitStructures(world, new IStructureVisitor() {
			@Override
			public void visit(MapGenStructure structure) {
				String structType = identifyStructure(structure);
				if (name.equals(structType)) {
					try {
						ChunkPosition structPos = structure.func_151545_a(world, x, y, z);
						if (structPos != null) result.add(structPos);
					} catch (IndexOutOfBoundsException e) {
						// bug in MC, just ignore
						// hopefully fixed by magic of ASM
					}
				}
			}
		});

		return result.build();
	}
}