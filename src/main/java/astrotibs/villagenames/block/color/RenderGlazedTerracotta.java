package astrotibs.villagenames.block.color;

import org.lwjgl.opengl.GL11;

import astrotibs.villagenames.proxy.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

// Adapted from KillerMapper's Road Stuff renderer
public class RenderGlazedTerracotta implements ISimpleBlockRenderingHandler
{
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.uvRotateTop = 1;
        renderer.uvRotateNorth = 2;
        //renderer.uvRotateEast = 3;
        renderer.uvRotateSouth = 1;
        renderer.uvRotateWest = 3;
        renderer.uvRotateBottom = 1;
        this.renderInInventory(tessellator, renderer, block, metadata);
        renderer.uvRotateTop = 0;
        renderer.uvRotateNorth = 0;
        //renderer.uvRotateEast = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateBottom = 0;
    }
    
    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        int orient = world.getBlockMetadata(x, y, z)/4; // Direction block is facing: 0=N, 1=E, 2=S, 3=W
        
        renderer.uvRotateTop = orient > 1 ? 5-orient : orient; // Rotate top side (NO NEED FOR MIRROR)
        renderer.uvRotateNorth = orient > 0 ? (orient%3)+1 : 0; // Rotate west-facing side (NO NEED FOR MIRROR)
        renderer.uvRotateEast = orient < 2 ? 1-orient : orient; // Rotate north-facing side (N&S orientations are flipped)
        renderer.uvRotateSouth = orient > 1 ? 2*orient-4 : 3-2*orient; // Rotate east-facing side (E&W orientations are flipped)
        renderer.uvRotateWest = orient > 1 ? 3-orient : orient+2; // Rotate south-facing side (NO NEED FOR MIRROR)
        renderer.uvRotateBottom = orient > 1 ? 2*orient-4 : 3-2*orient; // (ALL orientations are wrong)
        renderer.renderStandardBlock(block, x, y, z);
        
        // Must reset the rotation or it will mess up all rotating blocks around
        renderer.uvRotateTop = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateEast = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateBottom = 0;
        
        return true;
        
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return ClientProxy.renderGlazedTerracotta;
    }

    // To render a ISBRH part in the inventory - Credits to MinecraftForgeFrance
    private void renderInInventory(Tessellator tessellator, RenderBlocks renderer, Block block, int metadata)
    {
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        //renderer.flipTexture=true;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata)); //DO NOT SWITCH
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

}