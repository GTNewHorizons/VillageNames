package astrotibs.villagenames;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import astrotibs.villagenames.block.ModBlocksVN;
import astrotibs.villagenames.block.color.BlockConcrete;
import astrotibs.villagenames.command.CommandName;
import astrotibs.villagenames.config.GeneralConfigHandler;
import astrotibs.villagenames.init.ModConfiguration;
import astrotibs.villagenames.init.Recipes;
import astrotibs.villagenames.integration.ModChecker;
import astrotibs.villagenames.item.ModItems;
import astrotibs.villagenames.name.NameGenerator;
import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.nbt.VNWorldDataVillage;
import astrotibs.villagenames.prismarine.monument.MonumentGeneratorIWG;
import astrotibs.villagenames.prismarine.monument.StructureOceanMonument;
import astrotibs.villagenames.prismarine.monument.StructureOceanMonumentPieces;
import astrotibs.villagenames.prismarine.register.ModBlocksPrismarine;
import astrotibs.villagenames.prismarine.register.ModItemsPrismarine;
import astrotibs.villagenames.proxy.CommonProxy;
import astrotibs.villagenames.reference.Reference;
import astrotibs.villagenames.utility.LogHelper;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public final class VillageNames {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy PROXY;
	
	Random random = new Random();
	public static final HashSet<Integer> globalDimensionBlacklist = new HashSet<Integer>();
	public static Item itemVillageManual;
	public static File configDirectory;
	
	@Instance(Reference.MOD_ID)
	public static VillageNames instance;
	
	public static ModChecker modChecker;
	
	public static boolean addOceanMonuments;
    
	// PRE-INIT
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// item and block initialization and registering
		
		modChecker = new ModChecker();
		ModChecker.printSuccessMessage();
		
		// load configurations
		configDirectory = new File(event.getModConfigurationDirectory(), "VillageNames");
		ModConfiguration.init(configDirectory);
		
		// Moved down here to make sure config fires first!?
		ModItems.init();
		ModBlocksVN.init();
		
		addOceanMonuments = GeneralConfigHandler.addOceanMonuments;
		if (addOceanMonuments) {
			// Register Prismarine stuff here
			ModBlocksPrismarine.init();
			ModItemsPrismarine.init();
			
			GameRegistry.registerWorldGenerator(new MonumentGeneratorIWG(), 0);
			
			//DEFINITELY use these
			MapGenStructureIO.registerStructure(StructureOceanMonument.StartMonument.class, "Monument");
			StructureOceanMonumentPieces.registerOceanMonumentPieces();
			
		}
		
		// set up network handling
		PROXY.preInit(event);
		
		// Worldgen stuff
		// set up key bindings
		
		LogHelper.info("Pre-initialization complete!");
	}
        
    
    /**
     * Add village to biome
     * Register event listeners, village handler, new torch component
     */
	// NEED THIS to do Well work
	@EventHandler
	public void load(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);//For the populating event
		// set up GUI handler
		// set up TileEntity and entity
		// register crafting recipes
		Recipes.init();
		
		// rendering
		// package registering
		// general event handlers
		//EventRegister.register();
		PROXY.init(event);
		PROXY.registerRender();	// So far this is only used for Glazed Terracotta
		PROXY.registerEvents();
		// key handling below:
		
        LogHelper.info("Registering replacer for village generation");
        
	}
	
	// POST-INIT
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		PROXY.postInit(event);
		// cover your ass here
		// e.g. get list of all blocks added into game from other mods
	}
	
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
	    // register server commands
		event.registerServerCommand(new CommandName());
	}
	
	
    /**
     * Listen to the populating event.
     * Decorates stuff in villages
     *
     * @param event The populating event
     */
	@SubscribeEvent
	public void onPopulating(PopulateChunkEvent.Post event) {
		
		// Here I break down the dimension blacklist
		final HashSet<Integer> globalDimensionBlacklist = new HashSet<Integer>();
	    String[] blackList = GeneralConfigHandler.blackList;
	    boolean wellSlabs = GeneralConfigHandler.wellSlabs;
	    boolean nameSign = GeneralConfigHandler.nameSign;
	    boolean addConcrete = GeneralConfigHandler.addConcrete;
	    boolean concreteWell = GeneralConfigHandler.concreteWell;
	    
	    
	    for (String text : blackList) {
	        if (text != null && !text.isEmpty()) {
	            boolean done = false;
	            if (text.contains("[") && text.contains("]")) {
	                String[] results = text.substring(text.indexOf("[") + 1, text.indexOf("]")).split(";");
	                if (results.length == 2) {
	                    try {
	                        int a = Integer.parseInt(results[0]);
	                        int b = Integer.parseInt(results[1]);
	                        boolean remove = text.startsWith("-");
	                        for (int x = a; x <= b; x++) {
	                            if (remove)
	                                globalDimensionBlacklist.remove(x);
	                            else
	                                globalDimensionBlacklist.add(x);
	                        }
	                        done = true;
	                    } catch (NumberFormatException ignored) {

	                    }
	                }
	            }
	            if (!done) {
	                try {
	                    globalDimensionBlacklist.add(Integer.parseInt(text.trim()));
	                } catch (NumberFormatException ignored) {
	                }
	            }
	        }
	    }
		
		int i = (event.chunkX << 4) + 8;//Villages are offset
		int k = (event.chunkZ << 4) + 8;
		int y;
        Block id;
        Block id2;
        Block id3;
		int[] field;
		int[] field2;
		int[] field3;
		List<int[]> listOpaque;
		List<int[]> listWater;
		List<int[]> listWater2;
		List<int[]> listWater3;
		
	    // Here I apply the dimension blacklist
		if (event.hasVillageGenerated && !globalDimensionBlacklist.contains(event.world.provider.dimensionId)) {
			
            int signCornerAndFacing = random.nextInt(8);
            int signOrientation = signCornerAndFacing/2*4; // Sets the facing orientation for the sign. // N=8, E=12, S=0, W=4
            int signLocation = ((signCornerAndFacing+1)/2)%4; // One of the four corners // 0=NW, 1=NE, 2=SE, 3=SW
            int signXOffset = (1-Math.abs((signLocation+1)/2-1)*2)*2;
            int signZOffset = ((signLocation/2)*2-1)*2;
            int signHeightOffGround = 2;
            Block signBase = Blocks.cobblestone;
            
            int isWellCorner = 0;
			for (int x = i; x < i + 16; x++) {
				for (int z = k; z < k + 16; z++) {//Search within chunk
					y = event.world.getHeightValue(x, z);//block on top of a "solid" block
					if (y > 1) {
						y--;
						id = event.world.getBlock(x, y, z);
						while (id.isAir(event.world, x, y, z) || id.isLeaves(event.world, x, y, z)) {
							y--;
							id = event.world.getBlock(x, y, z);
						}
						// This part decorates wells!
						if (id.isOpaqueCube()) {//found solid block in open air
                            id  = event.world.getBlock(x, y - 4, z);
                            id2 = event.world.getBlock(x, y - 5, z);
                            id3 = event.world.getBlock(x, y - 6, z);
                            if (isWaterId(id) && isWaterId(id2) && isWaterId(id3)) {//found water under solid block layer
                                y -= 4;
                                field = new int[]{x, y, z};
                                field2 = new int[]{x, y-1, z};
                                field3 = new int[]{x, y-2, z};
                                listWater = getBorder(event.world, id, field);
                                listWater2 = getBorder(event.world, id2, field2);
                                listWater3 = getBorder(event.world, id3, field3);
                                listOpaque = getOpaqueBorder(event.world, field);
                                if (listWater.size() == 3 && listWater2.size() == 3 && listWater3.size() == 3 && listOpaque.size() == 5) {//found 3 water blocks AND 5 opaque blocks surrounding one water block on THREE levels, assuming this is a village well
                                	
                                	// Now I need to get tricky with the sign generation.
                                	isWellCorner ++; //1=NW, 2=SW, 3=NE, 4=SE
                                	
                                	if (1-Math.abs(isWellCorner/2-1)+((isWellCorner-1)%2)*2 == signLocation) {
                                		
                                		// Call the name generator here
                                		String[] newVillageName = NameGenerator.newVillageName();
                                		String headerTags = newVillageName[0];
                                		String namePrefix = newVillageName[1];
                                		String nameRoot = newVillageName[2];
                                		String nameSuffix = newVillageName[3];
                                		
                                		if ( event.world.getBlock(x+signXOffset, y+2, z+signZOffset).isAir(event.world, x+signXOffset, y+2, z+signZOffset) ||
                                			 event.world.getBlock(x+signXOffset, y+2, z+signZOffset).isLeaves(event.world, x+signXOffset, y+2, z+signZOffset)	) {
                                			// Set the sign now!
                                    		
                                    		// Step 0: Set the color for the name sign, and for the clay base.
                                			int[] codeColor_a = NamePieces.codeColors_default;
                                    		int townColorMeta = codeColor_a[random.nextInt(codeColor_a.length)];
                                    		// Allow a chance for uncommon colors to be used
                                    		if (townColorMeta==-1) {
                                    			int[] codeColors_remaining_a = NamePieces.codeColors_remaining;
                                    			townColorMeta = codeColors_remaining_a[random.nextInt(codeColors_remaining_a.length)];
                                    		}
                                    		
                                    		// Step 0.5: assign the top line of the sign at random, because radius can't be determined.
                                    		// I did want these thresholds:
                                    		// radius > 52: City
                                    		// radius > 40: Village
                                    		// radius > 32: Town
                                    		// radius > 0: Hamlet
                                    		String[] towntype = new String[]{"Welcome to", "Hamlet of", "Village of", "Town of"};
                                    		String topLine = towntype[random.nextInt(towntype.length)];
                                    		topLine = topLine.replaceAll("\\^", " ");
                                    		
                                    		int signX = (x+signXOffset);
                                    		int signY = y+2;
                                    		int signZ = (z+signZOffset);
                                    		VNWorldDataVillage data = VNWorldDataVillage.forWorld(event.world);
                                    		
                                    		/*
                                    		 * Added in 1.1
                                    		 * This checks to see if the village has already been named
                                    		 */
            		    					
            		    					NBTTagCompound tagCompound = data.getData();
            		    					
            		    					Set tagmapKeyset = tagCompound.func_150296_c(); //Gets the town key list: "coordinates"
            		    			        
            		    					
            		    			        Iterator itr = tagmapKeyset.iterator();
            		    			        String townSignEntry;
            		    			        
            		    			        //Placeholders for villagenames.dat tags
            		    			        boolean signLocated = false; //Use this to record whether or not a sign was found
            		    			        
            		    			        
            		    			        while(itr.hasNext()) {
            		    			            Object element = itr.next();
            		    			            
            		    			            townSignEntry = element.toString(); //Text name of village header (e.g. "x535y80z39")
            		    			            //The only index that has data is 0:
            		    			            NBTTagCompound tagList = tagCompound.getTagList(townSignEntry, tagCompound.getId()).getCompoundTagAt(0);
            		    			            
            		    			            int townX = tagList.getInteger("signX");
            		    			            int townY = tagList.getInteger("signY");
            		    			            int townZ = tagList.getInteger("signZ");
            		    			            
            		    			            int radiussearch = 32;
            		    			            if ( (signX-townX)*(signX-townX) + (signY-townY)*(signY-townY) + (signZ-townZ)*(signZ-townZ) <= radiussearch*radiussearch ) {
            		    			            	// This village already has a name.
            		    			            	townColorMeta = tagList.getInteger("townColor");//Too annoying. Just generate a random one.
                                            		namePrefix = tagList.getString("namePrefix");
                                            		nameRoot = tagList.getString("nameRoot");
                                            		nameSuffix = tagList.getString("nameSuffix");
            		    			            	break;
            		    			            }
            		    			            
            		    			        }
                                    		
                                    		
            		    			        if (nameSign) {
                                        		// Cobblestone wall pole and sign proper
                                        		event.world.setBlock(x+signXOffset, y+1, z+signZOffset, Blocks.cobblestone_wall);
                                        		event.world.setBlock(x+signXOffset, y+2, z+signZOffset, Blocks.standing_sign, signOrientation, 2); //Last field 2 for some reason please
                                        		
                                        		// Clay base OR concrete
                                        		BlockConcrete concreteBlock = ModBlocksVN.blockConcrete;
                                        		Block roofGlazedBlock = ModBlocksVN.glazedTerracotta;
                                        		switch (townColorMeta/4) {
	                                        		case 0: roofGlazedBlock = ModBlocksVN.glazedTerracotta; break;
	                                        		case 1: roofGlazedBlock = ModBlocksVN.glazedTerracotta2; break;
	                                        		case 2: roofGlazedBlock = ModBlocksVN.glazedTerracotta3; break;
	                                        		case 3: roofGlazedBlock = ModBlocksVN.glazedTerracotta4; break;
                                        		}
                                    			if (addConcrete && concreteWell) {
                                    				// Generate new-style concrete
                                    				for (int pedY = y-3; pedY <= y; pedY++) {
                                    					for (int rimi = 2; rimi > -3; rimi--) {
                                        					// This builds the rim around the well
                                            				event.world.setBlock(x+(signXOffset/2*rimi), pedY,z+signZOffset, concreteBlock, townColorMeta, 2); // The one under the sign when rimi=2
                                            				event.world.setBlock(x+signXOffset, pedY,z+(-signZOffset/2*(1+rimi)), concreteBlock, townColorMeta, 2);
                                            				event.world.setBlock(x-(signXOffset*3/2), pedY,z+(signZOffset/2*rimi), concreteBlock, townColorMeta, 2);
                                            				event.world.setBlock(x+(-signXOffset/2*(1+rimi)), pedY,z-(signZOffset*3/2), concreteBlock, townColorMeta, 2);
                                        				}
                                    				}
                                    				
                                    				// Set glazed terracotta in well roof!
                                    				int metaSpin = random.nextInt(4)+4;
                                    				int metaChirality = random.nextBoolean() ? 1 : -1;
                                    				
                                    				event.world.setBlock(x, y+4, z, roofGlazedBlock, ((metaSpin)*4)%16+townColorMeta%4, 2);
                                    				event.world.setBlock(x, y+4, z-(signZOffset/2), roofGlazedBlock, ((metaSpin+(metaChirality*1))*4)%16+townColorMeta%4, 2);
                                    				event.world.setBlock(x-(signXOffset/2), y+4, z-(signZOffset/2), roofGlazedBlock, ((metaSpin+(metaChirality*2))*4)%16+townColorMeta%4, 2);
                                    				event.world.setBlock(x-(signXOffset/2), y+4, z, roofGlazedBlock, ((metaSpin+(metaChirality*3))*4)%16+townColorMeta%4, 2);
                                    				
                                    			}
                                    			else {
                                    				// Generate old-style clay base
                                    				for (int pedY = y-3; pedY <= y; pedY++) {
                                    					event.world.setBlock(x+signXOffset,pedY,z+signZOffset, Blocks.stained_hardened_clay, townColorMeta, 2);	
                                    				}
                                    			}                                        		
                                    		}
            		    			        
                                    		// Okay now that the three components have been generated
                                    		// I need to find a way to fit them onto a sign.
                                    		TileEntitySign signContents = new TileEntitySign();
                                    		
                                    		if ( (namePrefix.length() + 1 + nameRoot.length()) > 15 ) {
                                    			// Prefix+Root is too long, so move prefix to line 1
                                    			signContents.signText[0] = headerTags+ topLine.trim();
                                    			signContents.signText[1] = namePrefix.trim();
                                    			if ( (nameRoot.length() + 1 + nameSuffix.length()) > 15 ) {
                                    				// Root+Suffix is too long, so move suffix to line 3
                                    				signContents.signText[2] = nameRoot.trim();
                                    				signContents.signText[3] = nameSuffix.trim();
                                    			}
                                    			else {
                                    				// Fit Root+Suffix onto line 2
                                    				signContents.signText[2] = (nameRoot+" "+nameSuffix).trim();
                                    			}
                                    		}
                                    		else if ( (namePrefix.length() + 1 + nameRoot.length() + 1 + nameSuffix.length()) <= 15 ) {
                                    			// Whole name fits on one line! Put it all on line 2.
                                    			signContents.signText[1] = headerTags+ topLine;
                                    			signContents.signText[2] = (namePrefix+" "+nameRoot+" "+nameSuffix).trim();
                                    		}
                                    		else {
                                    			// Only Prefix and Root can fit together on line 2.
                                    			signContents.signText[1] = headerTags+ topLine.trim();
                                    			signContents.signText[2] = (namePrefix+" "+nameRoot).trim();
                                    			signContents.signText[3] = nameSuffix.trim();
                                    		}
                                    		
                                    		// The following was going to say "Village" or "Hamlet" etc based on village size
                                    		// But it's kind of a PITA calculating village radius during sign creation.
                                    		// Maybe another time.
                                    		
                                    		// Put the stuff on the sign.
                                    		event.world.setTileEntity(x+signXOffset, y+2, z+signZOffset, signContents);
                                    		
                                    		
                                    		// Make the data bundle to save to NBT
                                    		NBTTagList nbttaglist = new NBTTagList();
                                    		
                                    		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                                            nbttagcompound1.setInteger("signX", signX);
                                            nbttagcompound1.setInteger("signY", signY);
                                            nbttagcompound1.setInteger("signZ", signZ);
                                            nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
                                            nbttagcompound1.setString("namePrefix", namePrefix);
                                            nbttagcompound1.setString("nameRoot", nameRoot);
                                            nbttagcompound1.setString("nameSuffix", nameSuffix);
                                            nbttagcompound1.setString("sign0", signContents.signText[0]);
                                            nbttagcompound1.setString("sign1", signContents.signText[1]);
                                            nbttagcompound1.setString("sign2", signContents.signText[2]);
                                            nbttagcompound1.setString("sign3", signContents.signText[3]);
                                            nbttaglist.appendTag(nbttagcompound1);
                                            // Save the data under a "Villages" entry with unique name based on sign coords
                                            data.getData().setTag("x"+signX+"y"+signY+"z"+signZ, nbttaglist);
                                    		data.markDirty();
                                    		
                                    		
                                		}
                                		else {
                                			/*
                                			The stupid thing is generating a sign inside the well structure for some reason.
                                			So here I'm going to record that to the console, as well as saving NBT data for the attempt.
                                			 */
                                			NBTTagList nbttaglist = new NBTTagList();
                                			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                                			nbttaglist.appendTag(nbttagcompound1);
                                			int signX = (x+signXOffset);
                                    		int signY = y+2;
                                    		int signZ = (z+signZOffset);
                                    		VNWorldDataVillage data = VNWorldDataVillage.forWorld(event.world);
                                    		LogHelper.info("Tried to generate a sign inside a well's post at x="+signX+" y="+signY+" z="+signZ);
                                			
                                		}
                                		
                                	}
                                	
                                	if (wellSlabs) {
                                    	field = listOpaque.remove(1);
                                        event.world.setBlock(field[0], field[1] + 1, field[2], Blocks.stone_slab);
                                        field = listOpaque.remove(2);
                                        event.world.setBlock(field[0], field[1] + 1, field[2], Blocks.stone_slab);
                                	}
                                    
                                    while (event.world.getBlock(x, y, z) == id) {
                                        y--;
                                    }
                                    field = new int[]{x, y, z};
                                    listOpaque = getOpaqueBorder(event.world, field);
                                    
                                }
                            }
                            continue;
						}
					}
				}
			}
		}
		// Else would go here
		
	}
	
    /**
     *
     * @param world The world containing the blocks
     * @param id The block searched
     * @param x, y, z The coordinates of the center to search around
     * @return true if all faces of the center are attached to the searched block
     */
    private static boolean hasAround(World world, Block id, int x, int y, int z){
        return world.getBlock(x-1, y, z) == id && world.getBlock(x+1, y, z) == id && world.getBlock(x, y, z-1) == id && world.getBlock(x, y, z+1) == id;
    }

    /**
     *
     * @param world The world containing the blocks
     * @param id The block searched
     * @param field The coordinates of the center to search around
     * @return A list of coordinates that contain the same block, around the center, at the same height
     */
	private static List<int[]> getBorder(World world, Block id, int[] field) {
		List<int[]> list = new ArrayList<int[]>();
		for (int x = field[0] - 1; x < field[0] + 2; x++) {
			for (int z = field[2] - 1; z < field[2] + 2; z++) {
				if ((x != field[0] || z != field[2]) && world.getBlock(x, field[1], z) == id)
					list.add(new int[] { x, field[1], z });
			}
		}
		return list;
	}
	
    /**
    *
    * @param world The world containing the blocks
    * @param id The block searched
    * @param field The coordinates of the center to search around
    * @return A list of coordinates that contain opaque cubes, around the center, at the same height
    */
	private static List<int[]> getOpaqueBorder(World world, int[] field) {
		List<int[]> list = new ArrayList<int[]>();
		for (int x = field[0] - 1; x < field[0] + 2; x++) {
			for (int z = field[2] - 1; z < field[2] + 2; z++) {
				if ((x != field[0] || z != field[2]) && world.getBlock(x, field[1], z).isOpaqueCube())
					list.add(new int[] { x, field[1], z });
			}
		}
		return list;
	}
	

    /**
     * Roughly estimates if a center block is cornered by blocks of the given type.
     * Actually searching for two unaligned border blocks
     *
     * @param world The world containing the blocks
     * @param id The block searched
     * @param pos The coordinates of the center to search around
     * @return true if the center block is cornered by the block type
     */
	private static boolean isCorner(World world, Block id, int[] pos) {
		List<int[]> list = getBorder(world, id, pos);
		if (list.size() < 2)
			return false;
		int[] a = list.get(0);
		int[] b = list.get(1);
		return a[0] != b[0] && a[2] != b[2];
	}

    /**
     * Wrapper method to identify replaceable blocks.
     *
     * @return true if the position is replaceable by any block
     */
	private static boolean isReplaceable(World world, int x, int y, int z) {
		return world.getBlock(x, y, z).isReplaceable(world, x, y, z);
	}

    /**
     * Wrapper method to identify water type blocks.
     *
     * @param id The block to compare
     * @return true if the block material is water
     */
	private static boolean isWaterId(Block id) {
		return id.getMaterial() == Material.water;
	}
	
	public File getConfigDirectory()
    {
        return configDirectory;
    }
	
	public static String mapColorMetaToStringFormat(int colorMeta) {
		HashMap<Integer, String> signColorToFormat = new HashMap<Integer, String>();//new HashMap();
		// This hashmap translates the town's name color on the sign to a color meta value.
		// This meta should be universal through e.g. wool, clay, etc
		signColorToFormat.put(0, "\u00a7f"); //white
		signColorToFormat.put(1, "\u00a76"); //gold
		signColorToFormat.put(2, "\u00a7d"); //light_purple
		signColorToFormat.put(3, "\u00a79"); //blue
		signColorToFormat.put(4, "\u00a7e"); //yellow
		signColorToFormat.put(5, "\u00a7a"); //green
		signColorToFormat.put(6, "\u00a7c"); //red
		signColorToFormat.put(7, "\u00a78"); //dark_gray
		signColorToFormat.put(8, "\u00a77"); //gray
		//signColorToFormat.put(9, "\u00a7b"); //aqua
		signColorToFormat.put(9, "\u00a73"); //dark_aqua
		signColorToFormat.put(10, "\u00a75"); //dark_purple
		signColorToFormat.put(11, "\u00a71"); //dark_blue
		signColorToFormat.put(12, "\u00a70"); //black
		signColorToFormat.put(13, "\u00a72"); //dark_green
		signColorToFormat.put(14, "\u00a74"); //dark_red
		signColorToFormat.put(15, "\u00a70"); //black
		
		// Return a "town color" string
		return signColorToFormat.get(colorMeta);
	}
	
}
