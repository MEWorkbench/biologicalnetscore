package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;

/**
 * This interface is implemented by classes that store statistics about the network.
 * 
 * @author Jose Pedro
 *
 */

public interface StatData {

	/**
	 * Returns the name of the statistic.
	 * @return
	 */
	public abstract String getName();
	
	public abstract QRTable getData();
}
