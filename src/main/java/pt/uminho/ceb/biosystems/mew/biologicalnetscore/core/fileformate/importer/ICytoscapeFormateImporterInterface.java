package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.fileformate.importer;

import java.io.File;
import java.io.IOException;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;

public interface ICytoscapeFormateImporterInterface {

	public INetwork importData(File f, File[] nodeMetaData, File[] edgeMetaData) throws IOException;
}
