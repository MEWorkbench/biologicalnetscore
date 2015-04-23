package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.bypassfilters;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;

public interface IBypassFilter {

	public INetwork createBypassNetwork(INetwork net, IBypassParameters parameters);
	
}
