package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.filters;

/**
 * This abstract class is extended by all compression classes that are 
 * used by the filter classes do determine if a node should be included on the mask or no.
 * @author Jose Pedro
 *
 * @param <T>
 */

public abstract class AbstractComparison<T> {
	
	/**
	 * Receives the data to be used in the analysis.
	 * @param data the data received
	 */
	abstract protected void inputData(T data);
	
	/**
	 * Checks if the analysis is ready to be made.
	 * @return
	 */
	abstract protected boolean checkReady();
	
	/**
	 * Evaluates the data and return the result of the analysis.
	 * @return
	 */
	abstract public boolean evaluate();
}
