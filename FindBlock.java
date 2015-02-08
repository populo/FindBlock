package populo.mod.findblock;

import populo.mod.findblock.comand.CommandFindBlock;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid=FindBlock.modid, name="Find Block", version="1.0")
public class FindBlock {
	
	public static final String modid = "findblock";
	
	@Instance(value=modid)
	public static FindBlock instance;
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandFindBlock());
	}
}
