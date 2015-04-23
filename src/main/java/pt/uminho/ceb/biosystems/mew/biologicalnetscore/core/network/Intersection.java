package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

public class Intersection {
	
	public static INetwork intersection(INetwork net1, INetwork net2, String name)
	{
		INetwork res = net1.clone();
		
		res.setName(name);
		
		INode[] nodes = res.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			if(net2.getNode(nodes[i].getDb_id(), nodes[i].getType())==null)
				res.removeNode(nodes[i]);
		}
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n2 = net2.getNode(nodes[i].getDb_id(), nodes[i].getType());
			
			if(n2!=null)
			{
				IEdge[] eds1 = res.getOutEdges(nodes[i]);
				IEdge[] eds2 = net2.getOutEdges(n2);
				
				for(int a=0;a<eds1.length;a++)
				{
					boolean found = false;
					
					INode sec1 = res.getConectedNodes(eds1[a])[1];
					
					for(int b=0;!found && b<eds2.length;b++)
					{
						INode sec2 = net2.getConectedNodes(eds2[b])[1];
						if(sec1.equals(sec2) && eds1[a].equals(eds2[b])) found = true;
					}
					
					if(!found)
					{
						res.removeEdge(eds1[a]);
					}
				}
				
			}
		}
		
		return res;
	}
}
