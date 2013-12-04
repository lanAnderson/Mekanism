package mekanism.client.sound;

import mekanism.client.ClientPlayerTickHandler;
import mekanism.common.item.ItemJetpack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

public class JetpackSound extends Sound
{
	/** The TileEntity this sound is associated with. */
	public EntityPlayer player;
	
	public int ticksSincePlay = 0;
	
	public JetpackSound(String id, String sound, EntityPlayer entity)
	{
		super(id, sound, entity, new Vector3(entity));
		
		player = entity;
	}
	
	@Override
	public float getMultiplier()
	{
		return Math.min(1, ((float)ticksSincePlay/20F))*0.3F;
	}
	
	@Override
	public Vector3 getLocation()
	{
		return new Vector3(player);
	}
	
	@Override
	public void play()
	{
		super.play();
		
		ticksSincePlay = 0;
	}
    
	@Override
	public boolean update(World world)
	{
		if(player.isDead)
		{
			return false;
		}
		else if(!world.loadedEntityList.contains(player))
		{
			return false;
		}
		else if(!hasJetpack(player))
		{
			return false;
		}
		else {
			if(ClientPlayerTickHandler.isJetpackOn(player) != isPlaying)
			{
				if(ClientPlayerTickHandler.isJetpackOn(player))
				{
					play();
				}
				else {
					stopLoop();
				}
			}
		}
		
		if(isPlaying)
		{
			ticksSincePlay++;
		}
		
		return true;
	}
	
	private boolean hasJetpack(EntityPlayer player)
	{
		return player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() instanceof ItemJetpack;
	}
}
