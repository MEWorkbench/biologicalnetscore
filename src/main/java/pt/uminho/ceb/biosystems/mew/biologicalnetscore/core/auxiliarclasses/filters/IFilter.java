package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;

public interface IFilter {

	public INetwork filterNetwork(INetwork net);
	
}
