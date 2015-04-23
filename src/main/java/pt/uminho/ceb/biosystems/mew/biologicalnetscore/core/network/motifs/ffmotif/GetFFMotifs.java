package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.ffmotif;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IGetMotifs;

public class GetFFMotifs implements IGetMotifs {

	protected String xType;
	protected String yType;
	protected String zType;
	protected String xyType;
	protected String yzType;
	protected String xzType;
	
	public GetFFMotifs(String xType, String yType, String zType, 
		String xyType, String yzType, String xzType) {
		this.xType = xType;
		this.yType = yType;
		this.zType = zType;
		this.xyType = xyType;
		this.yzType = yzType;
		this.xzType = xzType;
	}
	
	@Override
	public FFMotifs getMotifs(INetwork net) {
		
		INode[] nodes = net.getNodes();
		
		IEdge[][] edgeIsDaSource = new IEdge[nodes.length][];
//		int edgeType[][] = new int[nodes.length][]; //0-origen,1-destiny
		
		for(int i=0;i<nodes.length;i++)
		{
			INode no = nodes[i];
			
			IEdge[] out = net.getOutEdges(no);
			
			int v=0;
			
			for(int a=0;a<out.length;a++)
			{
				if(
					xyType==null ||
					yzType==null ||
					xzType==null ||
					out[a].isType().equals(xyType) ||
					out[a].isType().equals(yzType) ||
					out[a].isType().equals(xzType)
				)
				{
					v++;
				}
			}
			
			edgeIsDaSource[i] = new IEdge[v];
//			edgeType[i] = new int[v];
			
			v = 0;
			
			for(int a=0;a<out.length;a++)
			{
				if(
					xyType==null ||
					yzType==null ||
					xzType==null ||
					out[a].isType().equals(xyType) ||
					out[a].isType().equals(yzType) ||
					out[a].isType().equals(xzType)
				)
				{
					edgeIsDaSource[i][v] = out[a];
//					edgeType[i][v] = 0;
					v++;
				}
			}
			
		}
		
		

		ArrayList<FFMotif> motifs = new ArrayList<FFMotif>();
		for(int i=0;i<nodes.length;i++)
		{
			INode x = nodes[i];
			
			if(this.validX(x, net))
			{
				
				for(int t=0;t<edgeIsDaSource[i].length;t++)
				{
					INode y = null;
					IEdge xy = null;
					if(this.xyType==null || edgeIsDaSource[i][t].isType().equals(this.xyType))
					{
						y = net.getConectedNodes(edgeIsDaSource[i][t])[1];
						xy = edgeIsDaSource[i][t];
					}
					
					if(y!=null && !y.equals(x) && this.validY(y, net))
					{	
						int ypos=0;
						boolean stop = false;
						for(;ypos<nodes.length && !stop;)
						{
							if(nodes[ypos].equals(y))
							{
								stop = true;
							}
							else ypos++;
						}
						for(int j=0;j<edgeIsDaSource[ypos].length;j++)
						{
							INode z = null;
							IEdge yz = null;
							if(this.yzType==null || edgeIsDaSource[ypos][j].isType().equals(this.yzType))
							{
								z = net.getConectedNodes(edgeIsDaSource[ypos][j])[1];
								yz = edgeIsDaSource[ypos][j];
							}
							
							if(z!=null && !z.equals(x) && !z.equals(y) && this.validZ(z, net))
							{
								for(int h=0;h<edgeIsDaSource[i].length;h++)
								{
									IEdge xz = null;
									if(this.xzType==null || edgeIsDaSource[i][h].isType().equals(this.xzType))
									{
										if(net.getConectedNodes(edgeIsDaSource[i][h])[1].equals(z))
											xz = edgeIsDaSource[i][h];
									}
									
									if(xz!=null)
									{
										FFMotif ffm = new FFMotif(x, y, z, xy, yz, xz);
										motifs.add(ffm);
									}
								}
							}
						}
					}
				}
			}
		}
		
		FFMotif[] ffms = new FFMotif[motifs.size()];
		for(int i=0;i<motifs.size();i++) ffms[i] = motifs.get(i);
		
		String tx, ty, tz, txy, tyz, txz;
		
		if(xType==null) tx = "Any";
		else tx = xType;
		if(yType==null) ty = "Any";
		else ty = yType;
		if(zType==null) tz = "Any";
		else tz = zType;
		if(xyType==null) txy = "Any";
		else txy = xyType;
		if(yzType==null) tyz = "Any";
		else tyz = yzType;
		if(xzType==null) txz = "Any";
		else txz = xzType;
		
		String name = "FFM x="+tx+" y="+ty+" z="+tz+" xy="+txy+" yz="+tyz+" xz="+txz;
		
		return new FFMotifs(ffms, name);
	}
	
	protected boolean validX(INode x, INetwork net)
	{
		boolean res = true;
		
		if(this.xType!=null) res = x.getType().equals(this.xType);
		
		if(res) res = net.outDegree(x)>1;
	
		return res;
	}

	protected boolean validY(INode y, INetwork net)
	{
		boolean res = true;
		
		if(this.yType!=null) res = y.getType().equals(this.yType);
		
		if(res) res = net.outDegree(y)>0;
		
		return res;
	}

	protected boolean validZ(INode z, INetwork net)
	{
		boolean res = true;
		
		if(this.zType!=null) res = z.getType().equals(this.zType);
		
		if(res) res = net.inDegree(z)>1;
		
		return res;
	}
	
}
