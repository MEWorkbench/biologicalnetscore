package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.exportation;

import java.io.File;
import java.io.IOException;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;

public interface IExport {

	public void export(INetwork net, File f) throws IOException;
	
}
