package astrotibs.villagenames.integration;

import astrotibs.villagenames.utility.LogHelper;
import cpw.mods.fml.common.Loader;

public class ModChecker {
	
	public static boolean isWitcheryLoaded;
	public static boolean isGalacticraftLoaded;
	public static boolean isMorePlanetsLoaded;
	public static boolean isHardcoreEnderExpansionLoaded;
	//public static boolean isATGLoaded;
	//public static boolean isGanysSurfaceLoaded;
	//public static boolean isRoguelikeDungeonsLoaded;
	//public static boolean isFossilsAndArchaeologyLoaded; 
	//public static boolean isTwilightForestLoaded;
	//public static boolean isArtifactsLoaded;
	//public static boolean isOptifineLoaded;
	
	public ModChecker() {
		ModChecker.isWitcheryLoaded = Loader.isModLoaded("witchery");
		ModChecker.isGalacticraftLoaded = Loader.isModLoaded("GalacticraftCore");
		ModChecker.isMorePlanetsLoaded = Loader.isModLoaded("MorePlanet");
		ModChecker.isHardcoreEnderExpansionLoaded = Loader.isModLoaded("HardcoreEnderExpansion");
		//this.isATGLoaded = Loader.isModLoaded("ATG");
		//this.isGanysSurfaceLoaded = Loader.isModLoaded("ganyssurface");
		//this.isRoguelikeDungeonsLoaded = Loader.isModLoaded("Roguelike");
		//this.isFossilsAndArchaeologyLoaded = Loader.isModLoaded("fossil");
		//this.isTwilightForestLoaded = Loader.isModLoaded("TwilightForest");
		//this.isArtifactsLoaded = Loader.isModLoaded("Artifacts");
		//this.isOptifineLoaded = Loader.isModLoaded("OptiFine");
	}
	
	public static void printSuccessMessage() {
		if(isWitcheryLoaded) LogHelper.info("Detected mod: Witchery");
		if(isGalacticraftLoaded) LogHelper.info("Detected mod: Galacticraft");
		if(isMorePlanetsLoaded) LogHelper.info("Detected mod: More Planets");
		if(isHardcoreEnderExpansionLoaded) LogHelper.info("Detected mod: Hardcore Ender Expansion");
		//if(isATGLoaded) LogHelper.info("Detected mod: Alternate Terrain Generation");
		//if(isGanysSurfaceLoaded) LogHelper.info("Detected mod: Gany's Surface");
		//if(isRoguelikeDungeonsLoaded) LogHelper.info("Detected mod: Roguelike Dungeons");
		//if(isFossilsAndArchaeologyLoaded) LogHelper.info("Detected mod: Fossils and Archeology Revival");
		//if(isTwilightForestLoaded) LogHelper.info("Detected mod: The Twilight Forest");
		//if(isArtifactsLoaded) LogHelper.info("Detected mod: Artifacts");
		/*
		if(isOptifineLoaded) {
			LogHelper.info("Detected mod: Optifine");
			LogHelper.warn("WARNING: a bug in OptiFine causes errors for local games. Sometimes, Codex-generated names don't save.");
		}
		*/
	}
}
