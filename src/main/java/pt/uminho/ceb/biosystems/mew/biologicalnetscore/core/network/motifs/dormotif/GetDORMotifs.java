package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.dormotif;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IGetMotifs;

public class GetDORMotifs implements IGetMotifs {

	protected String[] upperNodesTypes;
	protected String[] edgesTypes;
	protected String[] lowerNodesTypes;
	protected int min;
	
	public GetDORMotifs(String[] upperNodesTypes, String[] lowerNodesTypes,  String[] edgesTypes, int min) {
		this.upperNodesTypes = upperNodesTypes;
		this.edgesTypes = edgesTypes;
		this.lowerNodesTypes = lowerNodesTypes;
		this.min = min;
	}
	
	@Override
	public DORMotifs getMotifs(INetwork net) {
		
		ArrayList<INode> tempRegs = new ArrayList<INode>();
		ArrayList<INode[]> tempRegleted = new ArrayList<INode[]>();
		ArrayList<IEdge[]> tempRegletedLink = new ArrayList<IEdge[]>();
		
		ArrayList<DORMotif> temp_dors = new ArrayList<DORMotif>();
		
		INode[] nodes = net.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			int num = validFirst(net, nodes[i]);
			
			if(num>0)
			{
				INode[] tempRegletedL = new INode[num];
				IEdge[] tempRegletedLinkL = new IEdge[num];
				
				IEdge[] eds = net.getOutEdges(nodes[i]);
				
				int z = 0;
				
				for(int a=0;a<eds.length;a++)
				{
					if(isOfType(eds[a].isType(), this.edgesTypes))
					{
						INode ln = net.getConectedNodes(eds[a])[1];
						
						if(isOfType(ln.getType(), this.lowerNodesTypes))
						{
							tempRegletedL[z] = ln;
							tempRegletedLinkL[z] = eds[a];
							z++;
						}
					}
				}
				
				tempRegs.add(nodes[i]);
				tempRegleted.add(tempRegletedL);
				tempRegletedLink.add(tempRegletedLinkL);
			}
		}
		
		
		INode[] regulaters = new INode[tempRegs.size()];
		
		for(int i=0;i<regulaters.length;i++) regulaters[i] = tempRegs.get(i);
		
		int j = 0;
		
		if(tempRegs.size()>0)
		{
			int regsSize = 0;
			
			int newSize = 0;
			
			do {
				regsSize = tempRegs.size();
				
				if(tempRegs.size()>0)
				{
					ArrayList<INode> nownodes = new ArrayList<INode>();
				
					ArrayList<INode> dor = new ArrayList<INode>();

					nownodes.add(tempRegs.get(0));
					
					dor.add(tempRegs.get(0));
					
					getEntangledNodes(nownodes, dor, regulaters, net);
					
					for(int z=0;z<dor.size();z++)
					{
						tempRegs.remove(dor.get(z));
					}

					if(dor.size()>=this.min)
					{
						INode[] upperNodes = new INode[dor.size()];
						
						ArrayList<INode> tlower = new ArrayList<INode>();
						ArrayList<IEdge> tedge = new ArrayList<IEdge>();
						
						for(int x=0;x<dor.size();x++)
						{
							upperNodes[x] = dor.get(x);
							
							boolean stop = false;
							
							for(int v=0;v<regulaters.length && !stop;v++)
							{
								if(regulaters[v].equals(upperNodes[x]))
								{
									stop = true;
									
									INode[] ns = tempRegleted.get(v);
									IEdge[] es = tempRegletedLink.get(v);
									
									for(int t=0;t<ns.length;t++)
									{
										if(!tlower.contains(ns[t])) tlower.add(ns[t]);
										tedge.add(es[t]);
									}
								}
							}
						}
						
						INode[] lower = new INode[tlower.size()];
						IEdge[] edge = new IEdge[tedge.size()];
						
						for(int x=0;x<lower.length;x++) lower[x] = tlower.get(x);
						
						for(int x=0;x<edge.length;x++) edge[x] = tedge.get(x);
						
						j++;
						
						temp_dors.add(new DORMotif(j+"", upperNodes, edge, lower));
					}
				}
				
				newSize = tempRegs.size();
				
			} while(regsSize!=newSize && tempRegs.size()>0);
			
		}
		
		DORMotif[] motifs = new DORMotif[temp_dors.size()];
		
		for(int i=0;i<temp_dors.size();i++) motifs[i] = temp_dors.get(i);
		
		String name = "DOR "+min+" upper nodes {"+this.upperNodesTypes[0];
		
		for(int i=1;i<this.upperNodesTypes.length;i++)
		{
			name += ","+this.upperNodesTypes[i];
		}
		
		name += "} lower nodes {"+this.lowerNodesTypes[0];
		
		for(int i=1;i<this.lowerNodesTypes.length;i++)
		{
			name += ","+this.lowerNodesTypes[i];
		}
		
		name += "} edges {"+edgesTypes[0];
		
		for(int i=1;i<this.edgesTypes.length;i++)
		{
			name += ","+this.edgesTypes[i];
		}

		name += "}";
		DORMotifs res = new DORMotifs(motifs, net.getNodes(), name);
		
		return res;
	}

	protected int validFirst(INetwork net, INode n)
	{
		boolean res = isOfType(n.getType(), this.upperNodesTypes);

		int z = 0;
		
		if(res)
		{
			IEdge[] eds = net.getOutEdges(n);
			
			for(int i=0;i<eds.length;i++)
			{
				if(isOfType(eds[i].isType(), this.edgesTypes))
				{
					if(isOfType(net.getConectedNodes(eds[i])[1].getType(), this.lowerNodesTypes))
					{
						z++;
					}
				}
			}
			
//			res = z>=this.min;
		}
		
		return z;
	}
	
	protected boolean isOfType(String type, String[] ntypes)
	{
		boolean res = false;
		
		for(int i=0;!res && i<ntypes.length;i++)
		{
			res = ntypes[i].equals(type);
		}
		
		return res;
	}
	
	protected boolean entangaled(INetwork net, INode node1, INode node2)
	{
		IEdge[] edges1 = net.getOutEdges(node1);
		IEdge[] edges2 = net.getOutEdges(node2);

		boolean[] ok1 = new boolean[edges1.length];
		boolean[] ok2 = new boolean[edges2.length];
		
		for(int i=0;i<edges1.length;i++)
		{
			ok1[i] = validEdge(net, edges1[i]);
		}
		
		for(int i=0;i<edges2.length;i++)
		{
			ok2[i] = validEdge(net, edges2[i]);
		}
		
		for(int a=0;a<ok1.length;a++)
		{
			if(ok1[a])
			{
				INode n1 = net.getConectedNodes(edges1[a])[1];
				
				for(int b=0;b<ok2.length;b++)
				{
					if(ok2[b])
					{
						INode n2 = net.getConectedNodes(edges2[b])[1];
						
						if(n1.equals(n2)) return true;
					}
				}
			}
		}
		
		return false;
	}
	
	protected boolean validEdge(INetwork net, IEdge e)
	{
		boolean res = isOfType(e.isType(), this.edgesTypes) && isOfType(net.getConectedNodes(e)[1].getType(), this.lowerNodesTypes);

		return res;
	}

	protected void getEntangledNodes(ArrayList<INode> nownodes, ArrayList<INode> dor, INode[] regulaters, INetwork net)
	{
		ArrayList<INode> new_nownodes = new ArrayList<INode>();
		
		for(int i=0;i<regulaters.length;i++)
		{
			INode node = regulaters[i];
			if(!dor.contains(node) && !new_nownodes.contains(node) && !nownodes.contains(node))
			{
				boolean stop = false;
				
				for(int a=0;a<nownodes.size() && !stop;a++)
				{
					if(entangaled(net, node, nownodes.get(a)))
					{
						new_nownodes.add(node);
						dor.add(node);
						stop = true;
					}
				}
			}
		}
		
		
		if(new_nownodes.size()>0) getEntangledNodes(new_nownodes, dor, regulaters, net);
		
	}
}
