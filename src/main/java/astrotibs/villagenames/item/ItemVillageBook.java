package astrotibs.villagenames.item;

import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

// Example copied from Pam's Harvestcraft
public class ItemVillageBook extends ItemEditableBook {
	
	public ItemVillageBook(String unlocalizedName) { // As of version 3, you can just register all the book variations through this class.
		super();
		setUnlocalizedName(unlocalizedName);//"villagebook"
		setTextureName(Reference.MOD_ID.toLowerCase() + ":" + unlocalizedName);//"villagebook"
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setMaxStackSize(1);
	}
	
	@Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
	
    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
    
    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
	
	private NBTTagList putContents(NBTTagList bookTagList) {
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagList bookPages = new NBTTagList();
		bookPages.appendTag(new NBTTagString("Page 1"));
		return bookTagList;
	}
	
	public void onUpdate(ItemStack itemStack, World world, EntityPlayer entity, int unknownInt, boolean unknownBool) {
		NBTTagList bookTagList = new NBTTagList();
		NBTTagCompound tag = new NBTTagCompound();
		bookTagList = putContents(bookTagList);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par3EntityPlayer.worldObj.isRemote) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(par3EntityPlayer, par1ItemStack, false));
		}
		return par1ItemStack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_) {
        return false; // Returning "false" stops the book from glowing.
    }
	
	
}
