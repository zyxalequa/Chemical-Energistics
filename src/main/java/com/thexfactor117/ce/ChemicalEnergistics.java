package com.thexfactor117.ce;

import com.thexfactor117.ce.handlers.GuiHandler;
import com.thexfactor117.ce.helpers.LogHelper;
import com.thexfactor117.ce.init.CEBlocks;
import com.thexfactor117.ce.init.CEItems;
import com.thexfactor117.ce.proxies.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.NAME)
public class ChemicalEnergistics 
{
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Instance(Reference.MODID)
	public static ChemicalEnergistics instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		LogHelper.info("Initializing Chemical Energistics - creating items, warming up machines, readying the explosives...");
		
		CEBlocks.registerBlocks();
		CEItems.registerItems();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(ChemicalEnergistics.instance, new GuiHandler());
		
		proxy.registerTileEntities();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LogHelper.info("Initialization of Chemical Energistics complete!");
		LogHelper.info("Taking over the world.....");
	}
}