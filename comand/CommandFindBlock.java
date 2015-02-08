package populo.mod.findblock.comand;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class CommandFindBlock extends CommandBase implements ICommand {
	
	private final List aliases;
	
	private EntityPlayer player;
	
	protected Block searchedBlock;
	protected String searchedName;
	protected String blockName;
	protected String blockLocalName;
	
	
	public CommandFindBlock() {
		aliases = new ArrayList();
		aliases.add("find");
		
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "find";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "find <block> [radius]";
	}

	@Override
	public List getCommandAliases() {
		return this.aliases;
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] string) {
		World world = sender.getEntityWorld();
	
		int found = 0;
		
		searchedName = string[0];
		
		if(string.length == 0) {
			throw new WrongUsageException("find <block> [radius]", new Object[0]);
		} else {
			
			int radius = 25;
			     
			if (string.length >= 2)	radius = parseIntBounded(sender, string[1], 1, 200);
			if (getBlockByText(sender, string[0]) instanceof Block)	{
				searchedBlock = getBlockByText(sender, string[0]);	
				blockName = searchedBlock.getUnlocalizedName();
				blockLocalName = searchedBlock.getLocalizedName();
				if (searchedBlock == Blocks.dirt) blockLocalName = "Dirt";
				else if (searchedBlock == Blocks.sand) blockLocalName = "Sand";
				else if (searchedBlock == Blocks.unpowered_comparator) blockLocalName = "Redstone Comparator";
				else if (searchedBlock == Blocks.pumpkin_stem) blockLocalName = "Pumpkin Stem";
				else if (searchedBlock == Blocks.sapling) blockLocalName = "Sapling";
				
				if (!world.isRemote) {
					for (int x = -(radius); x <= radius; x ++) {
						for (int y = -(radius); y <= radius; y ++) {
							for (int z = -(radius); z <= radius; z ++) {
								if (world.getBlock(sender.getPlayerCoordinates().posX + x, sender.getPlayerCoordinates().posY + y, sender.getPlayerCoordinates().posZ + z)
										== searchedBlock) {
									sender.addChatMessage(new ChatComponentText("found " + blockLocalName + " at " + (sender.getPlayerCoordinates().posX + x) + ", " + 
											(sender.getPlayerCoordinates().posY + y) + ", " + (sender.getPlayerCoordinates().posZ + z)));
									found++;
								}
							}
						}
					}
				}
				if (found == 0) {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + blockLocalName + " was not found in a " + radius + " block radius"));
				} else {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "found " + found + " " + blockLocalName + " blocks in a " + radius + " block radius"));
				}
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return (sender instanceof EntityPlayer);
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] string) {
		
		return getListOfStringsFromIterableMatchingLastWord(string, Block.blockRegistry.getKeys());
		
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
