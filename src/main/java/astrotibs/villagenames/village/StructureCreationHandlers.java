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
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsCartographer1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsFisherCottage1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsFletcherHouse1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsLargeFarm1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsLibrary1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsLibrary2;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsMasonsHouse1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsMediumHouse1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsMediumHouse2;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsMeetingPoint4;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsMeetingPoint5;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsShepherdsHouse1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsSmallFarm1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsSmallHouse1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsSmallHouse2;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsSmallHouse3;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsSmallHouse4;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsSmallHouse5;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsSmallHouse6;
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
	
	// Plains Cartographer 1
	public static class PlainsCartographer1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsCartographer1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsCartographer1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsCartographer1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsCartographer1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Fisher Cottage 1
	public static class PlainsFisherCottage1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsFisherCottage1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsFisherCottage1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsFisherCottage1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsFisherCottage1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Fletcher House 1
	public static class PlainsFletcherHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsFletcherHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsFletcherHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsFletcherHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsFletcherHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Large Farm 1
	public static class PlainsLargeFarm1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsLargeFarm1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsLargeFarm1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsLargeFarm1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsLargeFarm1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Library 1
	public static class PlainsLibrary1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsLibrary1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsLibrary1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsLibrary1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsLibrary1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Library 2
	public static class PlainsLibrary2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsLibrary2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsLibrary2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsLibrary2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsLibrary2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Mason's House 1
	public static class PlainsMasonsHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsMasonsHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsMasonsHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsMasonsHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsMasonsHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Medium House 1
	public static class PlainsMediumHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsMediumHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsMediumHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsMediumHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsMediumHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Medium House 2
	public static class PlainsMediumHouse2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsMediumHouse2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsMediumHouse2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsMediumHouse2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsMediumHouse2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Market 1
	public static class PlainsMeetingPoint4_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsMeetingPoint4_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsMeetingPoint4.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsMeetingPoint4.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsMeetingPoint4.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Market 2
	public static class PlainsMeetingPoint5_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsMeetingPoint5_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsMeetingPoint5.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsMeetingPoint5.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsMeetingPoint5.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Shepherd's House
	public static class PlainsShepherdsHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsShepherdsHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsShepherdsHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsShepherdsHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsShepherdsHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Small Farm
	public static class PlainsSmallFarm1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsSmallFarm1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsSmallFarm1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsSmallFarm1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsSmallFarm1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Small House 1
	public static class PlainsSmallHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsSmallHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsSmallHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsSmallHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsSmallHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Small House 2
	public static class PlainsSmallHouse2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsSmallHouse2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsSmallHouse2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsSmallHouse2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsSmallHouse2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Small House 3
	public static class PlainsSmallHouse3_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsSmallHouse3_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsSmallHouse3.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsSmallHouse3.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsSmallHouse3.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Small House 4
	public static class PlainsSmallHouse4_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsSmallHouse4_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsSmallHouse4.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsSmallHouse4.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsSmallHouse4.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Small House 5
	public static class PlainsSmallHouse5_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsSmallHouse5_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsSmallHouse5.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsSmallHouse5.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsSmallHouse5.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Small House 6
	public static class PlainsSmallHouse6_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.structureModernPlainsSmallHouse6_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsSmallHouse6.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsSmallHouse6.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsSmallHouse6.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
}


