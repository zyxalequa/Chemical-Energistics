package net.ce.handlers;

import net.ce.gui.GuiChemicalReactor;
import net.ce.gui.GuiPressurizedTank;
import net.ce.gui.containers.ContainerChemicalReactor;
import net.ce.gui.containers.ContainerPressurizedTank;
import net.ce.machines.tiles.TileEntityChemicalReactor;
import net.ce.machines.tiles.TileEntityPressurizedTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == 0)
		{
			TileEntityPressurizedTank pressurizedTank = (TileEntityPressurizedTank) world.getTileEntity(x, y, z);
			return new ContainerPressurizedTank(player, pressurizedTank);
		}
		
		if (ID == 1)
		{
			TileEntityChemicalReactor chemicalReactor = (TileEntityChemicalReactor) world.getTileEntity(x, y, z);
			return new ContainerChemicalReactor(player, chemicalReactor);
		}
		
		return null;
	}
	
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == 0)
		{
			TileEntityPressurizedTank pressurizedTank = (TileEntityPressurizedTank) world.getTileEntity(x, y, z);
			return new GuiPressurizedTank(player, pressurizedTank);
		}
		
		if (ID == 1)
		{
			TileEntityChemicalReactor chemicalReactor = (TileEntityChemicalReactor) world.getTileEntity(x, y, z);
			return new GuiChemicalReactor(player, chemicalReactor);
		}
		
		return null;
	}
}
