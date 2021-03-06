package com.thexfactor117.ce;

import com.thexfactor117.ce.crafting.CERecipes;
import com.thexfactor117.ce.handlers.*;
import com.thexfactor117.ce.helpers.*;
import com.thexfactor117.ce.init.*;
import com.thexfactor117.ce.proxies.CommonProxy;
import com.thexfactor117.ce.world.CEWorldGeneration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;

/**
 * To-do List:
 * Alpha 1.0.0
 * - Catalytic Reactor
 * - Basic Catalysts
 * - Crafting Recipes
 * - Textures (25%)
 * <p>
 * Alpha 2.0.0
 * - Element Freezer
 * - Element Oven
 * - Battery Box
 * - Advanced Catalysts
 * <p>
 * Alpha 3.0.0
 * - Chemical Deconstructor
 * - Chemical Bonder
 * - Compounds
 */

/**
 *
 * @author TheXFactor117 & Hlaaftana
 *
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ChemicalEnergistics {
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(Reference.MODID)
	public static ChemicalEnergistics instance;

	public CEWorldGeneration worldGen = new CEWorldGeneration();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Initializing Chemical Energistics - creating items, warming up machines, readying the explosives...");
		ConfigHandler.registerConfig(event.getModConfigurationDirectory());

		CEFluids.registerFluids();
		CEBlocks.registerBlocks();
		CEItems.registerItems();
		CERecipes.registerItemsToOreDict();

		GameRegistry.registerWorldGenerator(worldGen, 0);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		CERecipes.registerRecipes();
		proxy.registerTileEntities();

		BucketHandler.registerBuckets();

		GameRegistry.registerFuelHandler(new FuelHandler());

		NetworkRegistry.INSTANCE.registerGuiHandler(ChemicalEnergistics.instance, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(new CraftingHandler());
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Initialization of Chemical Energistics complete!");
		LogHelper.info("Taking over the world.....");
	}
}
