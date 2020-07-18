package astrotibs.villagenames.village;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsAccessory1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsAnimalPen1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsAnimalPen2;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsAnimalPen3;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsArmorerHouse1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsBigHouse1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsButcherShop1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsButcherShop2;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class StructureCreationHandlers
{
	// Plains flower planter
	public static class PlainsAccessory1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsAccessory1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsAccessory1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsAccessory1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsAccessory1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains small animal pen
	public static class PlainsAnimalPen1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsAnimalPen1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsAnimalPen1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsAnimalPen1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsAnimalPen1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains large animal pen
	public static class PlainsAnimalPen2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsAnimalPen2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsAnimalPen2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsAnimalPen2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsAnimalPen2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains decorated animal pen
	public static class PlainsAnimalPen3_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsAnimalPen3_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsAnimalPen3.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsAnimalPen3.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsAnimalPen3.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains armorer
	public static class PlainsArmorerHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsArmorerHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsArmorerHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsArmorerHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsArmorerHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains big house
	public static class PlainsBigHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsBigHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsBigHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsBigHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsBigHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}

	// Plains Butcher 1
	public static class PlainsButcherShop1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsButcherShop1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsButcherShop1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsButcherShop1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsButcherShop1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Butcher 2
	public static class PlainsButcherShop2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsButcherShop2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsButcherShop2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsButcherShop2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsButcherShop2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
}


