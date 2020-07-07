package astrotibs.villagenames.village.biomestructures;

import java.util.ArrayList;

import net.minecraft.block.Block;

/**
 * Used to assign data to the decor blueprints
 * @author AstroTibs
 */
public class BlueprintData {
	
	// xyz coordinates in the frame of reference of the structure
	private int upos;
	private int vpos;
	private int wpos;
	
	// Block of interest
	private Block block;
	private int meta;
	
	// Flag
	private byte fillFlag;
	
	// Getters
	public int getUPos() {return upos;}
	public int getVPos() {return vpos;}
	public int getWPos() {return wpos;}
	public Block getBlock() {return block;}
	public int getMeta() {return meta;}
	public byte getfillFlag() {return fillFlag;}
	
	
	// Constructor so you can add values
	/**
	 * Fill flags:
	 * 1 = also fill below with this block
	 * 2 = also clear spaces above this block
	 */
	public BlueprintData(int upos, int vpos, int wpos, Block block, int meta, byte fillFlag)
	{
		this.upos = upos; this.vpos = vpos; this.wpos = wpos;
		this.block = block; this.meta = meta;
		this.fillFlag = fillFlag;
	}
	
	// Assuming fill flag is 0 - ordinary block replacement
	public BlueprintData(int upos, int vpos, int wpos, Block block, int meta)
	{
		this(upos, vpos, wpos, block, meta, (byte) 0);
	}
	
	// Assuming meta flag is 0
	public BlueprintData(int upos, int vpos, int wpos, Block block, byte fill)
	{
		this(upos, vpos, wpos, block, 0, fill);
	}
	
	// Assuming both are 0
	public BlueprintData(int upos, int vpos, int wpos, Block block)
	{
		this(upos, vpos, wpos, block, 0, (byte) 0);
	}
	
	
	
	// Methods for placing a block
	public static void addPlaceBlock(ArrayList<BlueprintData> blueprint, int u, int v, int w, Block block, int meta)
	{
		blueprint.add(new BlueprintData(u, v, w, block, meta, (byte) 0));
	}
	public static void addPlaceBlock(ArrayList<BlueprintData> blueprint, int u, int v, int w, Block block)
	{
		blueprint.add(new BlueprintData(u, v, w, block, 0, (byte) 0));
	}
	
	
	
	// Methods for filling space with blocks
	public static void addFillWithBlocks(ArrayList<BlueprintData> blueprint, int umin, int vmin, int wmin, int umax, int vmax, int wmax, Block block, int meta)
	{
		for (int u=umin; u<=umax; u++) {for (int v=vmin; v<=vmax; v++) {for (int w=wmin; w<=wmax; w++) {
			blueprint.add(new BlueprintData(u, v, w, block, meta));
		}}}
	}
	public static void addFillWithBlocks(ArrayList<BlueprintData> blueprint, int umin, int vmin, int wmin, int umax, int vmax, int wmax, Block block)
	{
		addFillWithBlocks(blueprint, umin, vmin, wmin, umax, vmax, wmax, block, 0);
	}
	
	
	
	// Methods for filling up to a space with a block
	public static void addFillBelowTo(ArrayList<BlueprintData> blueprint, int u, int v, int w, Block block, int meta)
	{
		blueprint.add(new BlueprintData(u, v, w, block, meta, (byte) 1));
	}
	public static void addFillBelowTo(ArrayList<BlueprintData> blueprint, int u, int v, int w, Block block)
	{
		blueprint.add(new BlueprintData(u, v, w, block, 0, (byte) 1));
	}
	
	

	// Methods for placing a block and clearing the space above
	public static void addPlaceBlockAndClearAbove(ArrayList<BlueprintData> blueprint, int u, int v, int w, Block block, int meta)
	{
		blueprint.add(new BlueprintData(u, v, w, block, meta, (byte) 2));
	}
	public static void addPlaceBlockAndClearAbove(ArrayList<BlueprintData> blueprint, int u, int v, int w, Block block)
	{
		blueprint.add(new BlueprintData(u, v, w, block, 0, (byte) 2));
	}
	
	
	
	// Methods for filling up to a space with a block and clearing the space above
	public static void addFillBelowToAndClearAbove(ArrayList<BlueprintData> blueprint, int u, int v, int w, Block block, int meta)
	{
		blueprint.add(new BlueprintData(u, v, w, block, meta, (byte) 3));
	}
	public static void addFillBelowToAndClearAbove(ArrayList<BlueprintData> blueprint, int u, int v, int w, Block block)
	{
		blueprint.add(new BlueprintData(u, v, w, block, 0, (byte) 3));
	}
	
	
}
