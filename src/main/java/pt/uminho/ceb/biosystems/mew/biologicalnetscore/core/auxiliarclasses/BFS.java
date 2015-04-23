package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class BFS {

	protected INode[] nodes;
	protected int[] distance;
	protected ArrayList<INode>[] connectesBy;
	protected ArrayList<String>[] connectesBySet;
	
	@SuppressWarnings("unchecked")
	public BFS(INetwork net, INode start, String[] edgeTypes) {
		nodes = net.getNodes();
		distance = new int[nodes.length];
		connectesBy = new ArrayList[nodes.length];
		connectesBySet = new ArrayList[nodes.length];
		
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
					boolean keyComplete = false;
					
					int found = -1;
					
//					ArrayList<String> sets = new ArrayList<String>();
					
					for(int m=0;m<this.nodes.length && found==-1;m++)
					{
						if(ns[i].equals(this.nodes[m]))
						{
							found = m;
						}
					}

					ArrayList<String> enteredBySet = connectesBySet[found];
					
					for(int f=0;f<edgeTypes.length && !ok;f++)
					{
						if(edges[a].isType().equals(edgeTypes[f]))
						{
							String set = edges[a].getData("Set");
							
							if(set==null || enteredBySet.size()==0) ok = true;
							else if(set.equals("nill"))
							{
								ok = true;
								keyComplete = true;
							}
							else
							{
								if(enteredBySet.contains(set))
								{
									ok = true;
									keyComplete = true;
								}
							}
							
						}
					}
					
					if(ok)
					{
						INode[] pc = net.getConectedNodes(edges[a]);
						
						INode c = pc[1];
					
						if(c.equals(ns[i])) c = pc[0];
						
						int pos = -1;
					
						for(int z=0;z<nodes.length && pos==-1;z++)
						{
							if(nodes[z].equals(c))
							{
								pos = z;
							}
						}
					
						String set = null;
						
						if(!keyComplete)
						{
							set = edges[a].getData("Set");
							if(set==null || set.equals("nill")) set = null;
						}
						
						if(distance[pos]==-1)
						{
							distance[pos]=dis;
							connectesBy[pos].add(ns[i]);
							if(set!=null) connectesBySet[pos].add(set);
							current.add(nodes[pos]);
							change = true;
						}
						else if(distance[pos]==dis)
						{
							if(!connectesBy[pos].contains(ns[i]))
							{
								connectesBy[pos].add(ns[i]);
								if(set!=null) connectesBySet[pos].add(set);
							}
						}
					}
					
				}
			}
		}
	}
	
	public INode[][] getPaths(INode end)
	{
		ArrayList<ArrayList<INode>> inco = new ArrayList<ArrayList<INode>>();
		ArrayList<ArrayList<INode>> tres = new ArrayList<ArrayList<INode>>();
		
		boolean stop = false;
		
		for(int i=0;i<this.nodes.length && !stop;i++)
		{
			if(nodes[i].equals(end))
			{
				stop = true;
				
				for(int z=0;z<this.connectesBy[i].size();z++)
				{
					INode nz = this.connectesBy[i].get(z);
					ArrayList<INode> nn = new ArrayList<INode>();
					nn.add(nz);
					inco.add(nn);
				}
			}
		}
		
		while(inco.size()!=0)
		{
			ArrayList<ArrayList<INode>> temp = inco;
			inco = new ArrayList<ArrayList<INode>>();
			
			for(int z=0;z<temp.size();z++)
			{
				ArrayList<INode> t = temp.get(z);
				
				stop = false;
				
				for(int i=0;i<this.nodes.length && !stop;i++)
				{
					if(nodes[i].equals(t.get(t.size()-1)))
					{
						
						if(this.connectesBy[i].size()==0)
						{
							tres.add(t);
						}
						else
						{
							for(int x=0;x<this.connectesBy[i].size();x++)
							{
								INode nz = this.connectesBy[i].get(x);
							
								ArrayList<INode> nt = new ArrayList<INode>();
							
								for(int u=0;u<t.size();u++)
								{
									nt.add(t.get(u));
								}
							
								nt.add(nz);
								
								inco.add(nt);
							}
						}
					}
				}
			}
		}
		
		INode[][] res = new INode[tres.size()][];
		
		for(int x=0;x<tres.size();x++)
		{
			res[x] = new INode[tres.get(x).size()+1];
			for(int y=0;y<tres.get(x).size();y++)
			{
				res[x][y] = tres.get(x).get(tres.get(x).size()-1-y);
			}
			res[x][tres.get(x).size()] = end;
		}
		
		return res;
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
	
	public void printDistances()
	{
		for(int i=0;i<this.distance.length;i++)
		{
			System.out.println(this.nodes[i].toString()+":"+this.distance[i]);
		}
	}
	
	public void printDistancesPaths()
	{
		for(int i=0;i<this.distance.length;i++)
		{
			if(this.distance[i]!=-1)
			{
				System.out.println(this.nodes[i].toString()+":"+this.distance[i]);
				
				for(int z=0;z<this.connectesBy[i].size();z++)
				{
					System.out.print(connectesBy[i].get(z).toString()+" ");
				}
				System.out.println();
			}
		}
	}
}
