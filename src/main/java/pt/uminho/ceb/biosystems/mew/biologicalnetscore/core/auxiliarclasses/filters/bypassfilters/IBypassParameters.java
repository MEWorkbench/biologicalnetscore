package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.bypassfilters;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public interface IBypassParameters {
	
	//Adds the new edge to the new network
	public void getReplacementEdges(INetwork orginalNet, INetwork transformedNet, IEdge edge, INode start, INode end);
	
	public IBypassTransformer[] getBypassTransformers();
	
}
