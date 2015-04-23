package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.BFSPaths;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;

public class BFSPathsShortestPathCalculater implements Serializable, IShortestPathCalculater {

	private static final long serialVersionUID = 1L;
	protected JungNetwork net;
	transient protected BFSPaths bfs;
	
	public BFSPathsShortestPathCalculater(JungNetwork net)
	{
		this.net = net;
		this.bfs = new BFSPaths(this.net, this.net.getEdgeTypesArray());
	}
	
	@Override
	public Number getDistance(INode n1, INode n2) {
		if(this.bfs==null) this.bfs = new BFSPaths(this.net, this.net.getEdgeTypesArray());
		
		int dis = this.bfs.getDistance(n1, n2);
		Integer res= null; 
		if(dis>-1) res = new Integer(dis);
		return res;
	}

	@Override
	public Object[][] getPath(INode n1, INode n2) {
		if(this.bfs==null) this.bfs = new BFSPaths(this.net, this.net.getEdgeTypesArray());
		this.bfs.getDistance(n1, n2);
		return this.bfs.getShortestPaths(n1, n2);
	}

	@Override
	public void reset() {
		if(this.bfs==null) this.bfs = new BFSPaths(this.net, this.net.getEdgeTypesArray());
		else this.bfs.clean();
	}

	public void setBfs(BFSPaths bfs)
	{
		this.bfs = bfs;
	}
	
}
