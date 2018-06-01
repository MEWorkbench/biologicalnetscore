package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.filters;

/**
 * This abstract class serves as a template for the creation of filter 
 * classes, this kind of classes use a series of comparisons (extensions of 
 * the abstract AbstractComparison class) to generate masks for a network.
 * @author Jose Pedro
 *
 */

public abstract class AbstractFilter {

	protected AbstractComparison<?>[] comparisons;
	protected int maskSize;
	/**
	 * Returns a boolean mask to defines the nodes to be shown.
	 * @return
	 */
	
	public boolean[] getMask()
	{
		boolean[] mask = new boolean[maskSize];
		
		for(int a=0;a<maskSize;a++)
			mask[a] = evaluateRow(a);
		
		return mask;
	}
	
	protected boolean evaluateRow(int row)
	{
		boolean res = true;
		
		giveData(row);
		for(int a=0;a<this.comparisons.length;a++) 
			if(this.comparisons[a].checkReady())
				res = res && this.comparisons[a].evaluate();
			else res = false;
		
		return res;
	}
	
	abstract protected void giveData(int row);
}
