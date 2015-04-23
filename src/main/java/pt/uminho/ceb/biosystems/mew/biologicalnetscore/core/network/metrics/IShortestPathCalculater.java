package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

/**
 * This is an interface that serves as a template for Objects that calculate the shortest 
 * path between two nodes.
 * 
 * @author Jose Pedro
 *
 */

public interface IShortestPathCalculater {

	/**
	 * Returns the shortest path between the two nodes.
	 * @param n1
	 * @param n2
	 * @return
	 */
	public Number getDistance(INode n1, INode n2);
	
	public Object[][] getPath(INode n1, INode n2);
	
	public void reset();
}
