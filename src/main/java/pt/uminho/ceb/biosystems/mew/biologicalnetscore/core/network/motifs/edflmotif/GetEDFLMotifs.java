package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.edflmotif;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.BFSPaths;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IGetMotifs;

public class GetEDFLMotifs implements IGetMotifs {

	protected String startNodeType;
	protected String endNodeType;
	protected String[] feedbackEdgeTypes;
	protected String[] pathEdgesTypes;

	
	public GetEDFLMotifs(String startNodeType, String endNodeType, String[] feedbackEdgeTypes, String[] pathEdgesTypes) {
		this.startNodeType = startNodeType;
		this.endNodeType = endNodeType;
		this.feedbackEdgeTypes = feedbackEdgeTypes;
		this.pathEdgesTypes = pathEdgesTypes;
		
	}


	@Override
	public EDFLMotifs getMotifs(INetwork net) {
		
		ArrayList<EDFLMotif> tmotifs = new ArrayList<EDFLMotif>();
		
		INode[] starts = net.getNodes(this.startNodeType);
		INode[] ends = net.getNodes(this.endNodeType);
		
		boolean[][] possiblePair = new boolean[starts.length][ends.length];
		INode[][][][] possiblePaths = new INode[starts.length][ends.length][][];
		
		for(int a=0;a<starts.length;a++)
		{
			INode start = starts[a];
			BFSPaths bfspath = new BFSPaths(net, this.pathEdgesTypes);
			
			for(int b=0;b<ends.length;b++)
			{
				INode end = ends[b];
				
				int dis = bfspath.getDistance(start, end);
				
				if(dis>0)
				{
					possiblePair[a][b] = true;
					possiblePaths[a][b] = bfspath.getShortestPaths(start, end);
				}
				else possiblePair[a][b] = false;
			}
		}
		
		for(int b=0;b<ends.length;b++)
		{
			INode end = ends[b];
			
			BFSPaths feedbackbfspath = new BFSPaths(net, this.feedbackEdgeTypes);
			
			for(int a=0;a<starts.length;a++)
			{
				if(possiblePair[a][b])
				{
					INode start = starts[a];
				
					int dis = feedbackbfspath.getDistance(end, start);
					
					if(dis>0)
					{
						EDFLMotif em = new EDFLMotif(start, end, feedbackbfspath.getShortestPaths(end, start), possiblePaths[a][b]);
						
						tmotifs.add(em);
					}
				}
			}
		}
		
		EDFLMotif[] motifs = new EDFLMotif[tmotifs.size()];
		
		for(int i=0;i<motifs.length;i++) motifs[i] = tmotifs.get(i);
		
		String name = "EDFL {"+startNodeType+","+endNodeType+"} path edges {"+this.pathEdgesTypes[0];
		
		for(int i=1;i<this.pathEdgesTypes.length;i++)
		{
			name += ","+this.pathEdgesTypes[i];
		}
		
		name += "} feedback edges {"+this.feedbackEdgeTypes[0];
		
		for(int i=1;i<this.feedbackEdgeTypes.length;i++)
		{
			name += ","+this.feedbackEdgeTypes[i];
		}
		
		name += "}";
		
		return new EDFLMotifs(motifs, net.getNodes(), name);
	}

	protected boolean validStart(INode s, INetwork net)
	{
		boolean res = false;
		
		if(net.inDegree(s)>0 && net.outDegree(s)>0)
		{
			IEdge[] e = net.getInEdges(s);
			
			boolean ok = false;
			
			for(int i=0;!ok && i<e.length;i++)
			{
				for(int z=0;!ok && z<this.feedbackEdgeTypes.length;z++)
				{
					ok = e[i].isType().equals(this.feedbackEdgeTypes[z]);
				}
			}
			
			if(ok)
			{
				ok = false;
				
				e = net.getOutEdges(s);
				
				for(int i=0;!ok && i<e.length;i++)
				{
					for(int z=0;!ok && z<this.pathEdgesTypes.length;z++)
					{
						ok = e[i].isType().equals(this.pathEdgesTypes[z]);
					}
				}
			}
			
			res = ok;
		}
		
		return res;
	}

	protected boolean validEnd(INode s, INetwork net)
	{
		boolean res = false;
		
		if(net.inDegree(s)>0 && net.outDegree(s)>0)
		{
			IEdge[] e = net.getInEdges(s);
			
			boolean ok = false;
			
			for(int i=0;!ok && i<e.length;i++)
			{
				for(int z=0;!ok && z<this.pathEdgesTypes.length;z++)
				{
					ok = e[i].isType().equals(this.pathEdgesTypes[z]);
				}
			}
			
			if(ok)
			{
				ok = false;
				
				e = net.getOutEdges(s);
				
				for(int i=0;!ok && i<e.length;i++)
				{
					for(int z=0;!ok && z<this.feedbackEdgeTypes.length;z++)
					{
						ok = e[i].isType().equals(this.feedbackEdgeTypes[z]);
					}
				}
			}
			
			res = ok;
		}
		
		return res;
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
}
