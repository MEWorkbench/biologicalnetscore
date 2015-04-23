package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public interface IMotifs {

	public QRTable[] getData();
	
	public IMotif[] getMotifs();
	
	public INode[] getNodes();
	
	public int[] getOccurrences();

	public String[] getStatsNames();

	public int[][] getStats();
	
	public QRTable getStatsData();
	
	public String getName();
}
