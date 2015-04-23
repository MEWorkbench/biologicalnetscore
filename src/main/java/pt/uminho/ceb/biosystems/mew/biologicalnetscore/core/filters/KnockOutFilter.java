package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.filters;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class KnockOutFilter extends AbstractFilter{

	protected INode[] nodes;
	protected double[] filter;
	
	public KnockOutFilter(INetwork net, String type, String[] knockOuts)
	{
		this.nodes = net.getNodes();
		super.maskSize = this.nodes.length;
		
		super.comparisons = 
			new AbstractComparison[]{new KnockOutComparison(type, knockOuts)};
	}
	
	@Override
	protected void giveData(int row) {
		String id = nodes[row].getDb_id();
		String type = nodes[row].getType();
		((KnockOutComparison)super.comparisons[0]).inputData(new String[]{type,id});
	}

}
