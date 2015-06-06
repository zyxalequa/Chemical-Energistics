package net.ce.machines.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.ce.ChemicalEnergistics;
import net.ce.Reference;
import net.ce.init.CETabs;
import net.ce.machines.tiles.TileEntityChemicalReactor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockChemicalReactor extends BlockContainer implements ITileEntityProvider
{
	protected final int maxEnergyGeneration;
	protected final int maxEnergyTransfer;
	protected final int capacity;
	public Random rand = new Random();
	private static final String NAME = "chemicalReactor";
	
	public BlockChemicalReactor(int maxEnergyGeneration, int maxEnergyTransfer, int capacity)
	{
		super(Material.iron);
		this.setHardness(3.5F);
		this.setStepSound(soundTypeMetal);
		this.maxEnergyGeneration = maxEnergyGeneration;
		this.maxEnergyTransfer = maxEnergyTransfer;
		this.capacity = capacity;
		this.setBlockName(Reference.MODID + ":" + NAME);
		this.setCreativeTab(CETabs.tabCE);
	}
	
	/*@Override
    public void registerBlockIcons(IIconRegister pIconRegister) 
	{
        for(int i = 0; i < maxMeta; ++i) {
            icons[i] = pIconRegister.registerIcon(Constants.MODID + ":" + name + (i+1) + "Side");
        }
    }*/
	
	/*@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int pSide, int pMeta) 
	{
        return icons[pMeta];
    }*/

    @Override
    public TileEntity createNewTileEntity(World world, int meta) 
    {
        return new TileEntityChemicalReactor();
    }
    
    @Override
    public boolean hasTileEntity(int metadata) 
    {
        return true;
    }
    
    /**
	 * Called upon block activation (right-click)
	 * 
	 * When right-clicked, open the specified GUI
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float lx, float ly, float lz)
	{
		if (world.isRemote)
		{
			return true;
		}
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null && tileEntity instanceof TileEntityChemicalReactor)
		{
			// number indicates which GUI to open
			player.openGui(ChemicalEnergistics.instance, 1, world, x, y, z);
			
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6)
	{
		if (world.isRemote)
		{
			return;
		}
		
		ArrayList tankContents = new ArrayList();
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null && tileEntity instanceof TileEntityChemicalReactor)
		{
			TileEntityChemicalReactor chemicalReactor = (TileEntityChemicalReactor) tileEntity;
			
			for (int i = 0; i < chemicalReactor.getSizeInventory(); i++)
			{
				ItemStack stack = chemicalReactor.getStackInSlot(i);
				
				if (stack != null)
				{
					tankContents.add(stack.copy());
				}
			}
			
			for (int i = 0; i < tankContents.size(); i++)
			{
				EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5);
				item.setVelocity((rand.nextDouble() - 0.5) * 0.25, rand.nextDouble() * 0.5 * 0.25, (rand.nextDouble() - 0.5) * 0.25);
				world.spawnEntityInWorld(item);
			}
		}
	}
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) 
    {
        // If the ItemBlock version has energy stored in it then give the newly
        // created tile entity that energy
        if(stack.stackTagCompound != null) 
        {
        	//TileEntityChemicalReactor tileEntity = (TileEntityChemicalReactor)world.getTileEntity(x, y, z);
            //tileEntity.modifyEnergyStored(stack.stackTagCompound.getInteger(EnergyStorage.NBT_ENERGY));
        }
        
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }
}
