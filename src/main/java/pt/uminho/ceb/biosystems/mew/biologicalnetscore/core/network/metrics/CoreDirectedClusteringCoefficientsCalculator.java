package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class CoreDirectedClusteringCoefficientsCalculator implements IClusteringCoefficientsCalculator{

	protected HashMap<INode,Double> res = null;

	@Override
	public Map<?, ?> getClusteringCoefficients(INetwork net) {
		if(res==null)
		{
			res = new HashMap<INode,Double>();
			
			INode[] ns = net.getNodes();
			
			for(int i=0;i<ns.length;i++)
			{
				ArrayList<INode> neighborhood = getNeighborhood(net, ns[i]);
				double k = neighborhood.size();
				double d = numberOfEdgesInNeighborhood(net, neighborhood);
				d = d/(k*(k-1));
			}
		}
		
		return res;
	}
	
	protected ArrayList<INode> getNeighborhood(INetwork net, INode n)
	{
		ArrayList<INode> res = new ArrayList<INode>();
		
		IEdge[] es = net.getOutEdges(n);
		
		for(int i=0;i<es.length;i++)
		{
			INode t = net.getConectedNodes(es[i])[1];
			if(!res.contains(t)) res.add(t);
		}
		
		es = net.getInEdges(n);
		
		for(int i=0;i<es.length;i++)
		{
			INode t = net.getConectedNodes(es[i])[0];
			if(!res.contains(t)) res.add(t);
		}
		
		return res;
	}
	
	protected double numberOfEdgesInNeighborhood(INetwork net, ArrayList<INode> neighborhood)
	{
		double res = 0;
		
		for(int i=0;i<neighborhood.size();i++)
		{
			ArrayList<INode> found = new ArrayList<INode> ();
			IEdge[] es = net.getOutEdges(neighborhood.get(i));
			for(int a=0;a<es.length;a++)
			{
				INode t = net.getConectedNodes(es[i])[1];
				if(!found.contains(t))
				{
					if(neighborhood.contains(t))
					{
						found.add(t);
						res++;
					}
				}
			}
		}
		
		return res;
	}
	
	@Override
	public double[][] getCk(INetwork net) {
		
		HashMap<Integer,ArrayList<INode>> temp = new HashMap<Integer,ArrayList<INode>>();
		
		INode[] nodes = net.getNodes();
		
		ArrayList<Integer> aindex = new ArrayList<Integer>();
		
		for(int i=0;i<nodes.length;i++)
		{
			Integer degree = new Integer(net.degree(nodes[i]));
			
			if(!temp.containsKey(degree))
			{
				temp.put(degree, new ArrayList<INode>());
				aindex.add(degree);
			}
			temp.get(degree).add(nodes[i]);
		}
		
		Integer[] index = new Integer[aindex.size()];
		
		for(int i=0;i<aindex.size();i++)
		{
			index[i] = aindex.get(i);
		}
		
		Arrays.sort(index);
		
		if(res==null) getClusteringCoefficients(net);
		
		double[][] res = new double[index.length][2];
		
		for(int i=0;i<index.length;i++)
		{
			res[i][0] = index[i].doubleValue();
			res[i][1] = 0;
			ArrayList<INode> alis = temp.get(index[i]);
			
			for(int z=0;z<alis.size();z++)
			{
				res[i][1] += this.res.get(alis.get(z));
			}
			
			res[i][1] = res[i][1]/alis.size();
		}
		
		return res;
	}

	@Override
	public double[][] getInCk(INetwork net) {
		HashMap<Integer,ArrayList<INode>> temp = new HashMap<Integer,ArrayList<INode>>();
		
		INode[] nodes = net.getNodes();
		
		ArrayList<Integer> aindex = new ArrayList<Integer>();
		
		for(int i=0;i<nodes.length;i++)
		{
			Integer degree = new Integer(net.inDegree(nodes[i]));
			
			if(!temp.containsKey(degree))
			{
				temp.put(degree, new ArrayList<INode>());
				aindex.add(degree);
			}
			temp.get(degree).add(nodes[i]);
		}
		
		Integer[] index = new Integer[aindex.size()];
		
		for(int i=0;i<aindex.size();i++)
		{
			index[i] = aindex.get(i);
		}
		
		Arrays.sort(index);
		
		if(res==null) getClusteringCoefficients(net);
		
		double[][] res = new double[index.length][2];
		
		for(int i=0;i<index.length;i++)
		{
			res[i][0] = index[i].doubleValue();
			res[i][1] = 0;
			ArrayList<INode> alis = temp.get(index[i]);
			
			for(int z=0;z<alis.size();z++)
			{
				res[i][1] += this.res.get(alis.get(z));
			}
			
			res[i][1] = res[i][1]/alis.size();
		}
		
		return res;
	}

	@Override
	public double[][] getOutCk(INetwork net) {
		HashMap<Integer,ArrayList<INode>> temp = new HashMap<Integer,ArrayList<INode>>();
		
		INode[] nodes = net.getNodes();
		
		ArrayList<Integer> aindex = new ArrayList<Integer>();
		
		for(int i=0;i<nodes.length;i++)
		{
			Integer degree = new Integer(net.outDegree(nodes[i]));
			
			if(!temp.containsKey(degree))
			{
				temp.put(degree, new ArrayList<INode>());
				aindex.add(degree);
			}
			temp.get(degree).add(nodes[i]);
		}
		
		Integer[] index = new Integer[aindex.size()];
		
		for(int i=0;i<aindex.size();i++)
		{
			index[i] = aindex.get(i);
		}
		
		Arrays.sort(index);
		
		if(res==null) getClusteringCoefficients(net);
		
		double[][] res = new double[index.length][2];
		
		for(int i=0;i<index.length;i++)
		{
			res[i][0] = index[i].doubleValue();
			res[i][1] = 0;
			ArrayList<INode> alis = temp.get(index[i]);
			
			for(int z=0;z<alis.size();z++)
			{
				res[i][1] += this.res.get(alis.get(z));
			}
			
			res[i][1] = res[i][1]/alis.size();
		}
		
		return res;
	}

}
