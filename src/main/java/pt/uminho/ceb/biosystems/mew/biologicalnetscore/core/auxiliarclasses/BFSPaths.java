package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;

import java.util.HashMap;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class BFSPaths {

	protected INetwork net;
	protected HashMap<INode,BFS> s;
	protected String[] edgeTypes;
	
	public BFSPaths(INetwork net, String[] edgeTypes)
	{
		this.net = net;
		this.s = new HashMap<INode,BFS>();
		this.edgeTypes = edgeTypes;
	}
	
	public int getDistance(INode start, INode end)
	{
		BFS bfs;
		if(!this.s.containsKey(start))
		{
			bfs = new BFS(net, start, this.edgeTypes);
			s.put(start, bfs);
		}
		else
		{
			bfs = this.s.get(start);
		}
		int res = bfs.getDistance(end);
//		if(this.s.size()>50) this.s = new HashMap<INode,BFS>();
		return res;
	}

	
	public INode[][] getShortestPaths(INode start, INode end)
	{
		BFS bfs = this.s.get(start);
		if(bfs==null)
		{
			this.getDistance(start, end);
		}
		return bfs.getPaths(end);
	}
	
	public int getSize()
	{
		return this.s.size();
	}
	
	public void clean()
	{
		this.s = new HashMap<INode,BFS>();
	}
}
