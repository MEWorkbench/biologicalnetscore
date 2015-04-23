package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.bypassfilters;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public interface IBypassTransformer {
	
	public boolean transform(INetwork orginalNet, INetwork transformedNet, IEdge edge, INode start, INode end);
	
}
