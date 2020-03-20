package astrotibs.villagenames.config.gui;

import java.util.Set;

import cpw.mods.fml.client.IModGuiFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class VNGuiFactory implements IModGuiFactory 
{
    @Override
    public void initialize(Minecraft minecraftInstance) { //Called when instantiated to initialize with the active Minecraft instance
    }
 
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() { //Allows the main menu mod "Config" button to work
        return VNGuiConfig.class; 
    }
 
    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() { // Not sure if this is implemented yet
        return null;
    }
 
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) { // Allows for custom buttons or widgets
        return null;
    }
    
}