package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

import java.util.ArrayList;

public class Union {
	
	public static INetwork union(INetwork net1, INetwork net2, String name)
	{
		INetwork res = net1.clone();
		
		res.setName(name);
		
		INode[] nodes = net2.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			if(res.getNode(nodes[i].getDb_id(), nodes[i].getType())==null)
				res.addNode(nodes[i].clone());
		}
		
		nodes = res.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n2 = net2.getNode(nodes[i].getDb_id(), nodes[i].getType());
			
			if(n2!=null)
			{
				IEdge[] eds1 = res.getOutEdges(nodes[i]);
				IEdge[] eds2 = net2.getOutEdges(n2);
				
				for(int a=0;a<eds2.length;a++)
				{
					boolean found = false;
					
					INode sec2 = net2.getConectedNodes(eds2[a])[1];
					INode sec1 = null;
					
					for(int b=0;!found && b<eds1.length;b++)
					{
						sec1 = res.getConectedNodes(eds1[b])[1];
						if(sec1.equals(sec2) && eds1[b].equals(eds2[a])) found = true;
					}
					
					if(!found)
					{
						sec1 = res.getNode(sec2.getDb_id(), sec2.getType());
						res.addEdge(eds2[a].clone(), nodes[i], sec1);
					}
					
				}
			}
		}
		
		String[] et1 = net1.getEdgeTypesArray();
		String[] et2 = net2.getEdgeTypesArray();
		
		ArrayList<String> temp = new ArrayList<String>();
		
		for(int i=0;i<et1.length;i++)
		{
			temp.add(et1[i]);
		}
		
		for(int i=0;i<et2.length;i++)
		{
			if(!temp.contains(et2[i])) temp.add(et2[i]);
		}
		
		res.setEdgeTypesArray(temp.toArray(new String[]{}));
		
		String[] nt1 = net1.getNodeTypesArray();
		String[] nt2 = net2.getNodeTypesArray();
		
		temp = new ArrayList<String>();
		
		for(int i=0;i<nt1.length;i++)
		{
			temp.add(nt1[i]);
		}
		
		for(int i=0;i<nt2.length;i++)
		{
			if(!temp.contains(nt2[i])) temp.add(nt2[i]);
		}
		
		res.setNodeTypesArray(temp.toArray(new String[]{}));
		
		return res;
	}
}
