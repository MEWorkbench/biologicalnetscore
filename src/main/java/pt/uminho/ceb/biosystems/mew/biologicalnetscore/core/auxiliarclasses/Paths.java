package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class Paths {

	public static void bfs(INetwork net, INode start, INode end)
	{
		
	}
	
	public static ArrayList<INode[]> getAllPaths(INetwork net, INode start, INode end)
	{
		ArrayList<INode[]> res = new ArrayList<INode[]>();
		ArrayList<INode[]> inc = new ArrayList<INode[]>();
		
		inc.add(new INode[]{start});
		
		while(inc.size()>0)
		{
			INode[] path = new INode[0];
			
			int p = -1;
			
			for(int i=0;i<inc.size();i++)
			{
				if(inc.get(i).length>path.length)
				{
					path = inc.get(i);
					p=i;
				}
			}
			
			inc.remove(p);
			
			INode current = path[path.length-1];
			
			if(net.outDegree(current)>0)
			{
				IEdge[] edges = net.getOutEdges(current);
				
				ArrayList<INode> connected = new ArrayList<INode>();
				
				for(int i=0;i<edges.length;i++)
				{
					INode n = net.getConectedNodes(edges[i])[1];
					
					if(!connected.contains(n))
					{
						connected.add(n);
					}
				}
				
				for(int i=0;i<connected.size();i++)
				{
					INode n = connected.get(i);
					
					if(n.equals(end))
					{
						INode[] nres = new INode[path.length+1];
						
						for(int f=0;f<path.length;f++) nres[f] = path[f];
						
						nres[path.length] = n;
						
						res.add(nres);
					}
					else
					{
						boolean found = false;
						
						for(int a=0;a<path.length && !found;a++)
						{
							found = path[a].equals(n);						
						}
						
						if(!found)
						{
							INode[] npath = new INode[path.length+1];
							
							for(int f=0;f<path.length;f++) npath[f] = path[f];
							
							npath[path.length] = n;
							
							inc.add(npath);
						}
					}
				}
			}
			
		}
		
		return res;
	}

	
	protected static INode[][] getAllPathsAux(INetwork net, INode[] path, INode end)
	{
		INode current = path[path.length-1];
		
		if(current.equals(end)) return new INode[][]{path};
		
		if(net.outDegree(current)==0) return null;
		
		ArrayList<INode> connected = new ArrayList<INode>();
		
		IEdge[] edges = net.getOutEdges(current);
		
		for(int i=0;i<edges.length;i++)
		{
			INode n = net.getConectedNodes(edges[i])[1];
			
			if(!connected.contains(n))
			{
				connected.add(n);
			}
		}
		
		ArrayList<INode[][]> rl = new ArrayList<INode[][]>();
		
		for(int i=0;i<connected.size();i++)
		{
			INode[] npath = new INode[path.length+1];
			
			for(int f=0;f<path.length;f++) npath[f] = path[f];
			
			npath[path.length] = connected.get(i);
			
			INode[][] gaa = getAllPathsAux(net, npath, end);
			
			
			if(gaa!=null) rl.add(gaa);
			
		}
		
		if(rl.size()==0) return null;
		else
		{
			int size = 0;
			
			for(int i=0;i<rl.size();i++)
			{
				size += rl.get(i).length;
			}
			
			int pos = 0;
			
			INode[][] res = new INode[size][];
			
			for(int i=0;i<rl.size();i++)
			{
				INode[][] vos = rl.get(i);
				
				for(int a=0;a<vos.length;a++)
				{
					res[pos] = vos[a];
					pos++;
				}
			}
			return res;
		}
	}
	
}
