package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public interface IMotif {
	
	public INode[] getNodes();
	
	public IEdge[] getEdges();
	
	public String getName();

	public ArrayList<Object>[] getMotifData();
}
