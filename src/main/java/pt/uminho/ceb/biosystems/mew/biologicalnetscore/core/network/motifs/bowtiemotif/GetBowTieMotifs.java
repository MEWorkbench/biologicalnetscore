package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.bowtiemotif;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IGetMotifs;

public class GetBowTieMotifs implements IGetMotifs {

	protected String[] upperNodesTypes;
	protected String[] upperEdgesTypes;
	protected String centralNodeType;
	protected String[] lowerEdgesTypes;
	protected String[] lowerNodesTypes;
	protected int numberOfLowerNodesTypes;
	protected int numberOfUpperNodesTypes;
	
	public GetBowTieMotifs(String centralNodeType, String[] upperNodesTypes, String[] upperEdgesTypes, String[] lowerNodesTypes, String[] lowerEdgesTypes,
			int numberOfLowerNodesTypes, int numberOfUpperNodesTypes) {
		this.upperNodesTypes = upperNodesTypes;
		this.upperEdgesTypes = upperEdgesTypes;
		this.centralNodeType = centralNodeType;
		this.lowerEdgesTypes = lowerEdgesTypes;
		this.lowerNodesTypes = lowerNodesTypes;
		this.numberOfLowerNodesTypes = numberOfLowerNodesTypes;
		this.numberOfUpperNodesTypes = numberOfUpperNodesTypes;
	}
	
	@Override
	public BowTieMotifs getMotifs(INetwork net) {
		ArrayList<BowTieMotif> tmotifs = new ArrayList<BowTieMotif>();
		INode[] pcentrals = net.getNodes(this.centralNodeType);
		
		for(int i=0;i<pcentrals.length;i++)
		{
			INode pcentral = pcentrals[i];

			IEdge[] pue = net.getInEdges(pcentral);
			IEdge[] ple = net.getOutEdges(pcentral);
			
			boolean[] validu = new boolean[pue.length];
			boolean[] validl = new boolean[ple.length];
			
			int ok1 = 0, ok2 = 0;
			
			for(int a=0;a<validu.length;a++)
			{
				IEdge e = pue[a];
				INode n = net.getConectedNodes(e)[0];
				
				if(isOfType(e.isType(), this.upperEdgesTypes) && isOfType(n.getType(), this.upperNodesTypes))
				{
					validu[a] = true;
					ok1++;
				}
				else validu[a] = false;
			}
			
			for(int a=0;ok1>=this.numberOfUpperNodesTypes && a<validl.length;a++)
			{
				IEdge e = ple[a];
				INode n = net.getConectedNodes(e)[1];
				
				if(isOfType(e.isType(), this.lowerEdgesTypes) && isOfType(n.getType(), this.lowerNodesTypes))
				{
					validl[a] = true;
					ok2++;
				}
				else validl[a] = false;
			}
			
			if(ok2>=this.numberOfLowerNodesTypes)
			{
				INode[] upperNodes = new INode[ok1];
				IEdge[] upperEdges = new IEdge[ok1];
				
				int z = 0;
				
				for(int a=0;a<validu.length;a++)
				{
					if(validu[a])
					{
						IEdge e = pue[a];
						INode n = net.getConectedNodes(e)[0];
						upperNodes[z] = n;
						upperEdges[z] = e;
						z++;
					}
				}

				INode[] lowerNodes = new INode[ok2];
				IEdge[] lowerEdges = new IEdge[ok2];
				
				z = 0;
				
				for(int a=0;a<validl.length;a++)
				{
					if(validl[a])
					{
						IEdge e = ple[a];
						INode n = net.getConectedNodes(e)[1];
						lowerNodes[z] = n;
						lowerEdges[z] = e;
						z++;
					}
				}
				
				BowTieMotif ms = new BowTieMotif(pcentral, upperNodes, upperEdges, lowerNodes, lowerEdges);
				
				tmotifs.add(ms);
			}
		}
		
		BowTieMotif[] motifs = new BowTieMotif[tmotifs.size()];
		
		for(int i=0;i<motifs.length;i++) motifs[i] = tmotifs.get(i);
		
		String name = "Bowtie {"+numberOfUpperNodesTypes+","+numberOfLowerNodesTypes+"} upper nodes {"+this.upperNodesTypes[0];
		
		for(int i=1;i<this.upperNodesTypes.length;i++)
		{
			name += ","+this.upperNodesTypes[i];
		}
		
		name += "} lower nodes {"+this.lowerNodesTypes[0];
		
		for(int i=1;i<this.lowerNodesTypes.length;i++)
		{
			name += ","+this.lowerNodesTypes[i];
		}
		
		name += "} upper edges {"+this.upperEdgesTypes[0];
		
		for(int i=1;i<this.upperEdgesTypes.length;i++)
		{
			name += ","+this.upperEdgesTypes[i];
		}
		
		name += "} lower edges {"+this.lowerEdgesTypes[0];
		
		for(int i=1;i<this.lowerEdgesTypes.length;i++)
		{
			name += ","+this.lowerEdgesTypes[i];
		}
		
		name += "}";
		
		
		BowTieMotifs res = new BowTieMotifs(motifs, net.getNodes(), name);
		
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
