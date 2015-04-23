package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

import java.util.ArrayList;

public class ActiveNodeLocater {

	//General data
	protected INetwork net;
	protected String metaboliteNodeType;
	protected String reactionNodeType;
	protected ArrayList<INode> presentMetabolites;
	protected ArrayList<INode> activeReactions;
	
	//Reaction node data
	protected boolean directed;
	protected int reversibleTagPosition;
	
	//Edge data
	protected String consumptionType;
	protected String productionType;
	protected String multiType;
	protected int edgeReversibleTagPosition;
	protected String reverseSingTag;
	protected String reversibleTagSet1;
	protected String reversibleTagSet2;
	protected int stoichiometryPosition;
	
	public ActiveNodeLocater(INetwork net, String metaboliteNodeType, String reactionNodeType, ArrayList<INode> presentMetabolites, 
		boolean directed, int reversibleTagPosition, String consumptionType, String productionType, String multiType,
		int edgeReversibleTagPosition, String reverseSingTag, String reversibleTagSet1, String reversibleTagSet2, int stoichiometryPosition)
	{
		this.net = net;
		this.metaboliteNodeType = metaboliteNodeType;
		this.reactionNodeType = reactionNodeType;
		this.presentMetabolites = presentMetabolites;
		this.activeReactions = new ArrayList<INode>();
		this.directed = directed;
		this.reversibleTagPosition = reversibleTagPosition;
		this.consumptionType = consumptionType;
		this.productionType = productionType;
		this.multiType = multiType;
		this.edgeReversibleTagPosition = edgeReversibleTagPosition;
		this.reverseSingTag = reverseSingTag;
		this.reversibleTagSet1 = reversibleTagSet1;
		this.reversibleTagSet2 = reversibleTagSet2;
		this.stoichiometryPosition = stoichiometryPosition;
	}
	
	public ArrayList<INode> getPresentMetabolites()
	{
		return this.presentMetabolites;
	}
	
	public ArrayList<INode> getActiveReactions()
	{
		return this.activeReactions;
	}
	
	public void run()
	{
		INode[] areactions = this.net.getNodes(this.reactionNodeType);
		
		ArrayList<INode> reactions = new ArrayList<INode>();
		
		for(int i=0;i<areactions.length;i++) reactions.add(areactions[i]);
		
		int size1 = 0;
		int size2 = 0;
		
//		System.out.print("Metabolites: ");
//		
//		for(int i=0;i<this.presentMetabolites.size();i++)
//		{
//			System.out.print(this.presentMetabolites.get(i)+" ");
//		}
//
//		System.out.print("Reactions: ");
//		
//		for(int i=0;i<this.activeReactions.size();i++)
//		{
//			System.out.print(this.activeReactions.get(i)+" ");
//		}
		
		int k = 0;
		
		do {
			
			size1 = this.presentMetabolites.size();
			size2 = this.activeReactions.size();
			
			
			ArrayList<INode> remove = new ArrayList<INode>();
			
			for(int i=0;i<reactions.size();i++)
			{
				if(checkIfActive(reactions.get(i)))
				{
					remove.add(reactions.get(i));
				}
			}
			
			for(int i=0;i<remove.size();i++)
			{
				reactions.remove(remove.get(i));
				this.activeReactions.add(remove.get(i));
			}
			
			k++;
			
//			System.out.print("Metabolites: ");
//			
//			for(int i=0;i<this.presentMetabolites.size();i++)
//			{
//				System.out.print(this.presentMetabolites.get(i)+" ");
//			}
//
//			System.out.print("Reactions: ");
//			
//			for(int i=0;i<this.activeReactions.size();i++)
//			{
//				System.out.print(this.activeReactions.get(i)+" ");
//			}
			
		} while(size1<this.presentMetabolites.size() || size2<this.activeReactions.size());
	}
	
	protected boolean checkIfActive(INode reaction)
	{
		if(this.directed)
		{
			return checkIfActiveDirected(reaction);
		}
		else
		{
			return checkIfActiveUndirected(reaction);
		}
	}
	
	protected boolean checkIfActiveDirected(INode reaction)
	{
		String rev = reaction.getDatabase_data()[this.reversibleTagPosition];
		
		if(rev.equals("false"))
		{
			IEdge[] cedge = this.net.getInEdges(reaction);
			boolean ok = true;
			for(int z=0;z<cedge.length && ok;z++)
			{
				if(cedge[z].isType().equals(this.consumptionType))
				{
					INode met = this.net.getConectedNodes(cedge[z])[0];
					ok = isMetabolitePresent(met);
				}
			}
			
			if(ok)
			{
				
				IEdge[] oedge = this.net.getOutEdges(reaction);
				
				for(int z=0;z<oedge.length && ok;z++)
				{
					if(oedge[z].isType().equals(this.productionType))
					{
						INode met = this.net.getConectedNodes(oedge[z])[1];
						addMet(met);
					}
				}
			}
			return ok;
		}
		else
		{
			IEdge[] cedge = this.net.getInEdges(reaction);
			
			ArrayList<INode> set1 = new ArrayList<INode>();
			ArrayList<INode> set2 = new ArrayList<INode>();
			
			for(int z=0;z<cedge.length;z++)
			{
				if(cedge[z].isType().equals(this.consumptionType))
				{
					INode met = this.net.getConectedNodes(cedge[z])[0];
					if(cedge[z].getDatabase_data()[this.edgeReversibleTagPosition].equals(this.reversibleTagSet1))
					{
						set1.add(met);
					}
					else if(cedge[z].getDatabase_data()[this.edgeReversibleTagPosition].equals(this.reversibleTagSet2))
					{
						set2.add(met);
					}
				}
			}
			
			boolean ok = true;
			
			for(int z=0;z<set1.size() && ok;z++)
			{
				ok = isMetabolitePresent(set1.get(z));
			}
			
			if(ok)
			{
				for(int z=0;z<set2.size();z++)
				{
					addMet(set2.get(z));
				}
				return true;
			}
			else
			{
				ok = true;
				
				for(int z=0;z<set2.size() && ok;z++)
				{
					ok = isMetabolitePresent(set2.get(z));
				}
				
				if(ok)
				{
					for(int z=0;z<set1.size();z++)
					{
						addMet(set1.get(z));
					}
				}
				
				return ok;
			}
		}
		
	}
	
	protected boolean checkIfActiveUndirected(INode reaction)
	{
		String rev = reaction.getDatabase_data()[this.reversibleTagPosition];
		
		if(rev.equals("false"))
		{
			IEdge[] edge = this.net.getInEdges(reaction);
			boolean ok = true;
			for(int z=0;z<edge.length && ok;z++)
			{
				if(edge[z].isType().equals(this.consumptionType))
				{
					INode met;
					
					if(this.net.getConectedNodes(edge[z])[0].getType().equals(this.metaboliteNodeType))
						met = this.net.getConectedNodes(edge[z])[0];
					else met = this.net.getConectedNodes(edge[z])[1];
					
					ok = isMetabolitePresent(met);
				}
			}
			
			if(ok)
			{
				for(int z=0;z<edge.length && ok;z++)
				{
					if(edge[z].isType().equals(this.productionType))
					{
						INode met;
						
						if(!this.net.getConectedNodes(edge[z])[0].getType().equals(this.reactionNodeType))
							met = this.net.getConectedNodes(edge[z])[0];
						else met = this.net.getConectedNodes(edge[z])[1];
						
						addMet(met);
					}
				}
			}
			
			return ok;
		}
		else
		{
			IEdge[] edge = this.net.getInEdges(reaction);
			
			ArrayList<INode> set1 = new ArrayList<INode>();
			ArrayList<INode> set2 = new ArrayList<INode>();
			
			for(int z=0;z<edge.length;z++)
			{
				if(edge[z].isType().equals(this.multiType))
				{
					INode met;
					if(this.net.getConectedNodes(edge[z])[0].getType().equals(this.metaboliteNodeType)) met = this.net.getConectedNodes(edge[z])[0];
					else met = this.net.getConectedNodes(edge[z])[1];
					
					double sto = new Double(edge[z].getDatabase_data()[this.stoichiometryPosition]).doubleValue();
					
					if(edge[z].getDatabase_data()[this.edgeReversibleTagPosition].equals(this.reverseSingTag)) sto=0-sto;
					
					if(sto<0)
					{
						set1.add(met);
					}
					else
					{
						set2.add(met);
					}
				}
			}
			
			boolean ok = true;
			
			for(int z=0;z<set1.size() && ok;z++)
			{
				ok = isMetabolitePresent(set1.get(z));
			}
			
			if(ok)
			{
				for(int z=0;z<set2.size();z++)
				{
					addMet(set2.get(z));
				}
				
				return true;
			}
			else
			{
				ok = true;
				
				for(int z=0;z<set2.size() && ok;z++)
				{
					ok = isMetabolitePresent(set2.get(z));
				}
				
				if(ok)
				{
					for(int z=0;z<set1.size();z++)
					{
						addMet(set1.get(z));
					}
				}
				
				return ok;
			}
			
		}
		
	}
	
	protected boolean isMetabolitePresent(INode n)
	{
//		for(int i=0;i<presentMetabolites.size();i++)
//		{
//			System.out.print(presentMetabolites.get(i).toString()+" ");
//		}
		if(n.getType().equals(this.metaboliteNodeType))
		{
			for(int i=0;i<presentMetabolites.size();i++)
			{
				if(presentMetabolites.get(i).equals(n)) return true;
			}
			
			return false;
		}
		
		return true;
	}
	
	protected void addMet(INode n)
	{
		boolean ok = false;
		for(int i=0;i<presentMetabolites.size() && !ok;i++)
		{
			if(presentMetabolites.get(i).equals(n)) ok = true;
		}
		
		if(!ok) presentMetabolites.add(n);
		
	}
}
