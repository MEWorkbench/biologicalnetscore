package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

/**
 * This is an interface that serves as a template for node ranking Objects.
 * 
 * @author Jose Pedro
 *
 */

public interface IRanker {

	/**
	 * Returns the ranks of the node.
	 * @param n
	 * @return
	 */
	public double getRankScore(INode n);
}
