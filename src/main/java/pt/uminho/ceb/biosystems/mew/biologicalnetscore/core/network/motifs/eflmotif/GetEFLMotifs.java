package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.eflmotif;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.BFSPaths;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IGetMotifs;

public class GetEFLMotifs implements IGetMotifs {

	protected String startNodeType;
	protected String endNodeType;
	protected String[] feedbackEdgeTypes;
	protected String[] pathEdgesTypes;

	
	public GetEFLMotifs(String startNodeType, String endNodeType, String[] feedbackEdgeTypes, String[] pathEdgesTypes) {
		this.startNodeType = startNodeType;
		this.endNodeType = endNodeType;
		this.feedbackEdgeTypes = feedbackEdgeTypes;
		this.pathEdgesTypes = pathEdgesTypes;
		
//		System.out.print("[");
//		for(int i=0;i<this.feedbackEdgeTypes.length;i++)
//		{
//			System.out.print(" "+this.feedbackEdgeTypes[i]);
//		}
//		System.out.print("[");
//		for(int i=0;i<this.pathEdgesTypes.length;i++)
//		{
//			System.out.print(" "+this.pathEdgesTypes[i]);
//		}
		
	}


	@Override
	public EFLMotifs getMotifs(INetwork net) {
		BFSPaths bfspath = new BFSPaths(net, pathEdgesTypes);
		ArrayList<EFLMotif> tmotifs = new ArrayList<EFLMotif>();
		
//		INode[] starts = net.getNodes(this.startNodeType);
		INode[] ends = net.getNodes(this.endNodeType);
		
			
		ArrayList<INode[]> tempL = new ArrayList<INode[]>();
		ArrayList<IEdge> tempE = new ArrayList<IEdge>();
		

		for(int b=0;b<ends.length;b++)
		{
			
			if(validEnd(ends[b], net))
			{
				IEdge[] fbe = net.getOutEdges(ends[b]);
				
				for(int z=0;z<fbe.length;z++)
				{
					if(isOfType(fbe[z].isType(), this.feedbackEdgeTypes))
					{
						INode start = net.getConectedNodes(fbe[z])[1];
						
						if(validStart(start, net))
						{
							tempL.add(new INode[]{start, ends[b]});
							tempE.add(fbe[z]);
						}
					}
				}
			}
		}

		
		
		for(int i=0;i<tempL.size();i++)
		{
			INode start = tempL.get(i)[0];
			INode end = tempL.get(i)[1];
			
			int dis = bfspath.getDistance(start, end);
			
			if(dis>0)
			{
				INode[][] paths = bfspath.getShortestPaths(start, end);
				tmotifs.add(new EFLMotif(start, end, tempE.get(i), paths));
			}
		}
		
		EFLMotif[] motifs = new EFLMotif[tmotifs.size()];
		
		for(int i=0;i<motifs.length;i++) motifs[i] = tmotifs.get(i);

		String name = "EFL {"+startNodeType+","+endNodeType+"} path edges {"+this.pathEdgesTypes[0];
		
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
		
		return new EFLMotifs(motifs, net.getNodes(), name);
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
