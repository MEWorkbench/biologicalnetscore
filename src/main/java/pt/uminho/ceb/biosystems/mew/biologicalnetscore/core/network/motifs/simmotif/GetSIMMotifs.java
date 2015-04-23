package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.simmotif;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IGetMotifs;

public class GetSIMMotifs implements IGetMotifs {

	protected String coreType;
	protected String seconderyType;
	protected String edgesType;
	protected int numSecs;
	protected int edgesDirection;	//0 - undirected 1-x->y 2-x<-y
	
	public GetSIMMotifs(String coreType, String seconderyType, String edgesType, int numSecs, int edgesDirection) {
		this.coreType = coreType;
		this.seconderyType = seconderyType;
		this.edgesType = edgesType;
		this.numSecs = numSecs;
		this.edgesDirection = edgesDirection;
	}
	
	@Override
	public SIMMotifs getMotifs(INetwork net) {

		INode[] nodes = net.getNodes();
		
		ArrayList<SIMMotif> motifs = new ArrayList<SIMMotif>();
		for(int i=0;i<nodes.length;i++)
		{
			SIMMotif mo = getMotif(nodes[i], net);
			if(mo!=null) motifs.add(mo);
			
		}
		SIMMotif[] simms = new SIMMotif[motifs.size()];
		for(int i=0;i<simms.length;i++) simms[i] = motifs.get(i);
		
		String ct, st;
		
		if(coreType==null) ct = "Any";
		else ct = coreType;

		if(seconderyType==null) st = "Any";
		else st = seconderyType;
		
		String name = "SIM "+numSecs+" core="+ct+" Secondery="+st;
		
		return new SIMMotifs(simms, nodes, name);
	}
	
	protected SIMMotif getMotif(INode core, INetwork net)
	{
		SIMMotif res = null;
		
		boolean ok = true;
		
		if(this.coreType!=null) ok = core.getType().equals(this.coreType);
		
		if(ok)
		{
			if(this.edgesDirection==0 && net.degree(core)>=numSecs)
			{
				ArrayList<INode> tnodes = new ArrayList<INode>();
				ArrayList<IEdge> tedges = new ArrayList<IEdge>();
				
				IEdge[] out = net.getOutEdges(core);
				IEdge[] in = net.getInEdges(core);
				
				for(int i=0;i<out.length;i++)
					if(this.edgesType==null || out[i].isType().equals(this.edgesType))
					{
						INode sec = net.getConectedNodes(out[i])[1];
						if(this.seconderyType==null || sec.getType().equals(this.seconderyType))
						{
							tnodes.add(sec);
							tedges.add(out[i]);
						}
					}
				for(int i=0;i<in.length;i++) 
					if(this.edgesType==null || in[i].isType().equals(this.edgesType))
					{
						INode sec = net.getConectedNodes(in[i])[0];
						if(this.seconderyType==null || sec.getType().equals(this.seconderyType))
						{
							tnodes.add(sec);
							tedges.add(in[i]);
						}
					}
					
				if(this.numSecs<=tedges.size())
				{
					INode[] nodes = new INode[tnodes.size()+1];
					IEdge[] edges = new IEdge[tedges.size()];
					
					nodes[0] = core;
					
					for(int i=0;i<tedges.size();i++) 
					{
						nodes[i+1] = tnodes.get(i);
						edges[i] = tedges.get(i);
					}
					res=new SIMMotif(nodes,edges);
				}
			}
			else if(this.edgesDirection==1 && net.outDegree(core)>=numSecs)
			{
				ArrayList<INode> tnodes = new ArrayList<INode>();
				ArrayList<IEdge> tedges = new ArrayList<IEdge>();
				
				IEdge[] out = net.getOutEdges(core);
				
				for(int i=0;i<out.length;i++)
					if(this.edgesType==null || out[i].isType().equals(this.edgesType))
					{
						INode sec = net.getConectedNodes(out[i])[1];
						if(this.seconderyType==null || sec.getType().equals(this.seconderyType))
						{
							tnodes.add(sec);
							tedges.add(out[i]);
						}
					}
				
				if(this.numSecs<=tedges.size())
				{
					INode[] nodes = new INode[tnodes.size()+1];
					IEdge[] edges = new IEdge[tedges.size()];
					
					nodes[0] = core;
					
					for(int i=0;i<tedges.size();i++) 
					{
						nodes[i+1] = tnodes.get(i);
						edges[i] = tedges.get(i);
					}
					res=new SIMMotif(nodes,edges);
				}
			}
			else if(this.edgesDirection==2 && net.inDegree(core)>=numSecs)
			{
				ArrayList<INode> tnodes = new ArrayList<INode>();
				ArrayList<IEdge> tedges = new ArrayList<IEdge>();
				
				IEdge[] in = net.getInEdges(core);
				
				for(int i=0;i<in.length;i++) 
					if(this.edgesType==null || in[i].isType().equals(this.edgesType))
					{
						INode sec = net.getConectedNodes(in[i])[0];
						if(this.seconderyType==null || sec.getType().equals(this.seconderyType))
						{
							tnodes.add(sec);
							tedges.add(in[i]);
						}
					}
					
				if(this.numSecs<=tedges.size())
				{
					INode[] nodes = new INode[tnodes.size()+1];
					IEdge[] edges = new IEdge[tedges.size()];
					
					nodes[0] = core;
					
					for(int i=0;i<tedges.size();i++) 
					{
						nodes[i+1] = tnodes.get(i);
						edges[i] = tedges.get(i);
					}
					res=new SIMMotif(nodes,edges);
				}
			}
		}
		
		return res;
	}
	
}
