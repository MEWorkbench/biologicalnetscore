package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.fileformate.exporter;

import java.io.File;
import java.io.IOException;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;

public interface IExporterInterface {

	public void exportData(INetwork net, File f) throws IOException;
	
}
