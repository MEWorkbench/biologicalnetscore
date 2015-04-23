package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class BFSLigth {

	protected INode[] nodes;
	protected int[] distance;
	
	@SuppressWarnings("unchecked")
	public BFSLigth(INetwork net, INode start, String[] edgeTypes) {
		nodes = net.getNodes();
		distance = new int[nodes.length];
		ArrayList<INode>[] connectesBy = new ArrayList[nodes.length];
		ArrayList<String>[] connectesBySet = new ArrayList[nodes.length];
		
		for(int i=0;i<nodes.length;i++)
		{
			distance[i] = -1;
			connectesBy[i] = new ArrayList<INode>();
			connectesBySet[i] = new ArrayList<String>();
		}
	
		ArrayList<INode> current = new ArrayList<INode>();
		
		current.add(start);
		
		int dis = 0;
		
		boolean change = true;
		
		boolean stop = false;
		
		for(int z=0;z<nodes.length && !stop;z++)
		{
			if(nodes[z].equals(start))
			{
				stop = true;
				
				distance[z] = 0;
			}
		}
		
		while(change)
		{
			dis++;
			
			change = false;
			
			INode[] ns = new INode[current.size()];
			
			for(int i=0;i<current.size();i++)
			{
				ns[i] = current.get(i);
			}
			
			current = new ArrayList<INode>();
			
			for(int i=0;i<ns.length;i++)
			{
				IEdge[] edges = net.getOutEdges(ns[i]);
				
				for(int a=0;a<edges.length;a++)
				{
					boolean ok = false;
					
					int found = -1;
					boolean set1 = false;
					boolean set2 = false;
					
					for(int m=0;m<this.nodes.length && found==-1;m++)
					{
						if(ns[i].equals(this.nodes[m]))
						{
							found = m;
						}
					}

					for(int m=0;m<connectesBy[found].size();m++)
					{
						if(connectesBySet[found].get(m).equals("set 1")) set1=true;
						else if(connectesBySet[found].get(m).equals("set 2")) set2=true;
						else if(connectesBySet[found].get(m).equals("nill")) 
						{
							set1=true;
							set2=true;
						}
					}
					
					if(!set1 && !set2)
					{
						set1=true;
						set2=true;
					}
					
					for(int f=0;f<edgeTypes.length && !ok;f++)
					{
						if(edgeTypes[0].equals("Any") || edges[a].isType().equals(edgeTypes[f]))
						{
							if(edges[a].getExtradatanames()!=null && edges[a].getExtradatanames().length>0)
							{
								if(edges[a].getExtradatanames()[0].equals("set 1") && set1)
								{
									ok = true;
								}
								else if(edges[a].getExtradatanames()[0].equals("set 2") && set2)
								{
									ok = true;
								}
							}
							else ok = true;
						}
					}
					
					if(ok)
					{
						INode c = net.getConectedNodes(edges[a])[1];
					
						int pos = -1;
					
						for(int z=0;z<nodes.length && pos==-1;z++)
						{
							if(nodes[z].equals(c))
							{
								pos = z;
							}
						}
					
						String set = "nill";
						
						if(edges[a].getExtradatanames()!=null && edges[a].getExtradatanames().length>0)
						{
							if(edges[a].getExtradatanames()[0].equals("set 1"))
							{
								set = "set 1";
							}
							else if(edges[a].getExtradatanames()[0].equals("set 2"))
							{
								set = "set 2";
							}
						}
						
						if(distance[pos]==-1)
						{
							distance[pos]=dis;
							connectesBy[pos].add(ns[i]);
							connectesBySet[pos].add(set);
							current.add(nodes[pos]);
							change = true;
						}
						else if(distance[pos]==dis)
						{
							if(!connectesBy[pos].contains(ns[i]))
							{
								connectesBy[pos].add(ns[i]);
								connectesBySet[pos].add(set);
							}
						}
					}
					
				}
			}
		}
	}

	public int getDistance(INode end)
	{
		int res = -1;
		
		boolean stop = false;
		
		for(int i=0;i<this.nodes.length && !stop;i++)
		{
			if(nodes[i].equals(end))
			{
				stop = true;
				res = this.distance[i];
			}
		}
		
		return res;
	}

	public int[] getDistances()
	{		
		return this.distance;
	}
	
	public INode[] getNodes()
	{
		return nodes;
	}
}
