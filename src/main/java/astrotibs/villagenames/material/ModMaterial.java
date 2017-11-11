package astrotibs.villagenames.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

// I need to do this so the goddamn lunarin gold shows up as gold
public class ModMaterial extends Material {
	// Need a constructor, m80
    public ModMaterial(MapColor mapcolor) {
    	super(mapcolor);
    }
	
    public static final Material gold = (new ModMaterial(MapColor.goldColor)).setRequiresTool();
    
    /* Determines if the material can be harvested without a tool (or with the wrong tool) */
    private boolean requiresNoTool = true;
    
    /*
     * Makes blocks with this material require the correct tool to be harvested.
     */
    @Override
	protected Material setRequiresTool()
    {
        this.requiresNoTool = false;
        return this;
    }
    
    /*
     * Returns true if the material can be harvested without a tool (or with the wrong tool)
     */
    @Override
	public boolean isToolNotRequired()
    {
        return this.requiresNoTool;
    }
    
}
