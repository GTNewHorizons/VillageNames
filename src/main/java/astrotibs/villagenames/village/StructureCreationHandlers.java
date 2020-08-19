package astrotibs.villagenames.village;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertAnimalPen1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertAnimalPen2;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertArmorer1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertButcherShop1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertCartographerHouse1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertFarm1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertFarm2;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertFisher1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertFletcherHouse1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertLargeFarm1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertLibrary1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertMason1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertMediumHouse1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertMediumHouse2;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertShepherdHouse1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertSmallHouse1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertSmallHouse2;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertSmallHouse3;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertSmallHouse4;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertSmallHouse5;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertSmallHouse6;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertSmallHouse7;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertSmallHouse8;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertTannery1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertTemple1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertTemple2;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertToolSmith1;
import astrotibs.villagenames.village.biomestructures.DesertStructures.DesertWeaponsmith1;
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
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsSmallHouse7;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsSmallHouse8;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsStable1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsStable2;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsTannery1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsTemple3;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsTemple4;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsToolSmith1;
import astrotibs.villagenames.village.biomestructures.PlainsStructures.PlainsWeaponsmith1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaAnimalPen1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaArmorer2;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaArmorerHouse1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaButcherShop1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaCartographerHouse1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaFisherCottage1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaFletcherHouse1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaLargeFarm1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaLibrary1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaMasonsHouse1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaMediumFarm1;
import astrotibs.villagenames.village.biomestructures.TaigaStructures.TaigaMediumHouse1;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class StructureCreationHandlers
{
	// Plains flower planter
	public static class PlainsAccessory1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsAccessory1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsAnimalPen1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsAnimalPen2_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsAnimalPen3_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsArmorerHouse1_vals;
		
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
	
	// Plains large house
	public static class PlainsBigHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsBigHouse1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsButcherShop1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsButcherShop2_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsCartographer1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsFisherCottage1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsFletcherHouse1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsLargeFarm1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsLibrary1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsLibrary2_vals;
		
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
	
	// Plains Mason House 1
	public static class PlainsMasonsHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsMasonsHouse1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsMediumHouse1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsMediumHouse2_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsMeetingPoint4_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsMeetingPoint5_vals;
		
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
	
	// Plains Shepherd House
	public static class PlainsShepherdsHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsShepherdsHouse1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsSmallFarm1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsSmallHouse1_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsSmallHouse2_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsSmallHouse3_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsSmallHouse4_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsSmallHouse5_vals;
		
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
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsSmallHouse6_vals;
		
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
	
	// Plains Small House 7
	public static class PlainsSmallHouse7_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsSmallHouse7_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsSmallHouse7.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsSmallHouse7.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsSmallHouse7.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Small House 8
	public static class PlainsSmallHouse8_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsSmallHouse8_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsSmallHouse8.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsSmallHouse8.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsSmallHouse8.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Cobblestone Stable
	public static class PlainsStable1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsStable1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsStable1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsStable1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsStable1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Terracotta Stable
	public static class PlainsStable2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsStable2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsStable2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsStable2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsStable2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Tannery
	public static class PlainsTannery1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsTannery1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsTannery1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsTannery1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsTannery1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Terracotta Temple
	public static class PlainsTemple3_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsTemple3_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsTemple3.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsTemple3.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsTemple3.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Cobblestone Temple
	public static class PlainsTemple4_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsTemple4_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsTemple4.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsTemple4.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsTemple4.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Tool Smithy
	public static class PlainsToolSmith1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsToolSmith1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsToolSmith1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsToolSmith1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsToolSmith1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Plains Weapon Smithy
	public static class PlainsWeaponsmith1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernPlainsWeaponsmith1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(PlainsWeaponsmith1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return PlainsWeaponsmith1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return PlainsWeaponsmith1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Small Animal Pen
	public static class DesertAnimalPen1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertAnimalPen1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertAnimalPen1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertAnimalPen1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertAnimalPen1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Covered Animal Pen
	public static class DesertAnimalPen2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertAnimalPen2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertAnimalPen2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertAnimalPen2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertAnimalPen2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Armorer House
	public static class DesertArmorer1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertArmorer1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertArmorer1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertArmorer1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertArmorer1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Butcher Shop
	public static class DesertButcherShop1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertButcherShop1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertButcherShop1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertButcherShop1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertButcherShop1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Cartographer House
	public static class DesertCartographerHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertCartographerHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertCartographerHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertCartographerHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertCartographerHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Small Farm
	public static class DesertFarm1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertFarm1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertFarm1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertFarm1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertFarm1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Large Farm
	public static class DesertFarm2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertFarm2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertFarm2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertFarm2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertFarm2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Fisher Cottage
	public static class DesertFisher1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertFisher1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertFisher1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertFisher1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertFisher1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Fletcher House
	public static class DesertFletcherHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertFletcherHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertFletcherHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertFletcherHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertFletcherHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Large Farm
	public static class DesertLargeFarm1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertLargeFarm1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertLargeFarm1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertLargeFarm1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertLargeFarm1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Library
	public static class DesertLibrary1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertLibrary1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertLibrary1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertLibrary1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertLibrary1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Mason House
	public static class DesertMason1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertMason1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertMason1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertMason1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertMason1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Medium House
	public static class DesertMediumHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertMediumHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertMediumHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertMediumHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertMediumHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Big House
	public static class DesertMediumHouse2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertMediumHouse2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertMediumHouse2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertMediumHouse2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertMediumHouse2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Shepherd House
	public static class DesertShepherdHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertShepherdHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertShepherdHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertShepherdHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertShepherdHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Small House 1
	public static class DesertSmallHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertSmallHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertSmallHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertSmallHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertSmallHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Small House 2
	public static class DesertSmallHouse2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertSmallHouse2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertSmallHouse2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertSmallHouse2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertSmallHouse2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Small House 3
	public static class DesertSmallHouse3_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertSmallHouse3_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertSmallHouse3.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertSmallHouse3.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertSmallHouse3.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Small House 4
	public static class DesertSmallHouse4_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertSmallHouse4_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertSmallHouse4.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertSmallHouse4.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertSmallHouse4.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Small House 5
	public static class DesertSmallHouse5_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertSmallHouse5_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertSmallHouse5.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertSmallHouse5.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertSmallHouse5.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Small House 6
	public static class DesertSmallHouse6_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertSmallHouse6_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertSmallHouse6.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertSmallHouse6.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertSmallHouse6.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Small House 7
	public static class DesertSmallHouse7_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertSmallHouse7_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertSmallHouse7.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertSmallHouse7.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertSmallHouse7.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Small House 8
	public static class DesertSmallHouse8_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertSmallHouse8_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertSmallHouse8.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertSmallHouse8.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertSmallHouse8.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Tannery 1
	public static class DesertTannery1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertTannery1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertTannery1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertTannery1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertTannery1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Temple 1
	public static class DesertTemple1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertTemple1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertTemple1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertTemple1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertTemple1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Temple 2
	public static class DesertTemple2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertTemple2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertTemple2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertTemple2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertTemple2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Tool Smithy
	public static class DesertToolSmith1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertToolSmith1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertToolSmith1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertToolSmith1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertToolSmith1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Desert Weapon Smithy
	public static class DesertWeaponsmith1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernDesertWeaponsmith1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(DesertWeaponsmith1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return DesertWeaponsmith1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return DesertWeaponsmith1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Animal Pen
	public static class TaigaAnimalPen1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaAnimalPen1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaAnimalPen1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaAnimalPen1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaAnimalPen1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Armorer Hut
	public static class TaigaArmorer2_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaArmorer2_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaArmorer2.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaArmorer2.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaArmorer2.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Armorer House
	public static class TaigaArmorerHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaArmorerHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaArmorerHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaArmorerHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaArmorerHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Butcher Shop
	public static class TaigaButcherShop1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaButcherShop1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaButcherShop1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaButcherShop1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaButcherShop1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Cartographer House
	public static class TaigaCartographerHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaCartographerHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaCartographerHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaCartographerHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaCartographerHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Fisher Cottage
	public static class TaigaFisherCottage1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaFisherCottage1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaFisherCottage1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaFisherCottage1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaFisherCottage1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Fletcher House
	public static class TaigaFletcherHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaFletcherHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaFletcherHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaFletcherHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaFletcherHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Large Farm
	public static class TaigaLargeFarm1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaLargeFarm1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaLargeFarm1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaLargeFarm1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaLargeFarm1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Medium Farm
	public static class TaigaMediumFarm1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaMediumFarm1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaMediumFarm1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaMediumFarm1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaMediumFarm1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Library
	public static class TaigaLibrary1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaLibrary1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaLibrary1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaLibrary1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaLibrary1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Mason House
	public static class TaigaMasonsHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaMasonsHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaMasonsHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaMasonsHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaMasonsHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
	
	// Taiga Medium House 1
	public static class TaigaMediumHouse1_Handler implements IVillageCreationHandler
	{
		ArrayList<Integer> ali = GeneralConfig.componentModernTaigaMediumHouse1_vals;
		
	    @Override
	    public PieceWeight getVillagePieceWeight(Random random, int villageSize)
	    {
	    	return new PieceWeight(TaigaMediumHouse1.class, ali.get(0), MathHelper.getRandomIntegerInRange(random, ali.get(0), MathHelper.getRandomIntegerInRange(random, villageSize * ali.get(1) + ali.get(2), villageSize * ali.get(3) + ali.get(4))));
	    }
	    
	    @Override
	    public Class<?> getComponentClass() {return TaigaMediumHouse1.class;}
	    
	    @Override
	    public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	    {
	    	if (startPiece instanceof StartVN) {return TaigaMediumHouse1.buildComponent((StartVN)startPiece, pieces, random, p1, p2, p3, p4, p5);} return null;
	    }
	}
}


