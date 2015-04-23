package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class BFSap {

	public static double getAverangeShortestPath(INetwork net)
	{
		double num = 0;
		double tam = 0;
		
		INode[] nodes = net.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			BFSLigth bfsl = new BFSLigth(net, nodes[i], new String[]{"Any"});
			int[] dis = bfsl.getDistances();
			
			for(int z=0;z<dis.length;z++)
			{
				if(dis[z]>0)
				{
					num += dis[z];
					tam ++;
				}
			}
			
		}
		
		return num/tam;
	}
	
}
