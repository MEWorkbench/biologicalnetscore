package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.util.Map;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;

/**
 * This interface is a template for the creation of objects that calculate the 
 * clustering coefficient of off all the nodes of a network.
 * 
 * @author Jose Pedro
 *
 */
public interface IClusteringCoefficientsCalculator {
	
	/**
	 * Returns a Map with the clustering coefficients values in the form of 
	 * Strings with the nodes (INodes objects) as keys.
	 * 
	 * @param net
	 * @return
	 */
	
	public Map<?,?> getClusteringCoefficients(INetwork net);
	
	public double[][] getCk(INetwork net);
	
	public double[][] getInCk(INetwork net);
	
	public double[][] getOutCk(INetwork net);
}
