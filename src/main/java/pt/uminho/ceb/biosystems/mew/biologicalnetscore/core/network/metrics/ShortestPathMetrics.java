package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

/**
 * This class stores several metrics related to the shortest path.
 * 
 * @author Jose Pedro
 *
 */

public class ShortestPathMetrics extends Observable implements Serializable{

	private static final long serialVersionUID = 1L;
//	protected INode[] nodes;
//	protected String[] avergangeDistsIndex;
//	protected Double[] avergangeDists;
//	protected INode[] avergangeToDistsIndex;
//	protected Double[] avergangeToDists;
//	protected INode[] connectedIndex;
//	protected Integer[] connected;
//	protected INode[] nodesThatPointToIndex;
//	protected Integer[] nodesThatPointTo;
	protected int diameter;
	protected double averangeDist;
	protected int numberOfInNodes;
	protected int numberOfOutNodes;
	protected int maxPath;
	protected int minPath;
	protected IShortestPathCalculater spc;
	protected INetwork net;
	
	public ShortestPathMetrics(INetwork network, IShortestPathCalculater spc)
	{
		INode[] nodes = network.getNodes();
		
		this.net = network;
		this.spc = spc;
		
		int num = 0;
		this.diameter = 0;
		this.averangeDist = 0;
		this.numberOfInNodes = 0;
		this.numberOfOutNodes = 0;
		this.maxPath = 0;
		this.minPath = 0;
		
		for(int i=0;i<nodes.length;i++)
		{
			if(network.getInEdges(nodes[i]).length>0) numberOfInNodes++;
			if(network.getOutEdges(nodes[i]).length>0) numberOfOutNodes++;
			
//			int nconnected = 0;
//			double totalDis = 0;
			
			for(int z=0;z<nodes.length;z++)
			{
				if(i!=z)
				{
					Number n = spc.getDistance(nodes[i], nodes[z]);
					if(n!=null)
					{
//						nconnected++;
						double dd = n.doubleValue();
//						totalDis += dd;
						num++;
						this.averangeDist+= dd;
						
						int y = (new Double(dd)).intValue();
						
						if(diameter!=-1 && diameter<dd)
						{
							diameter = y;
						}

						if(this.maxPath<y) this.maxPath = y;
						if(this.minPath==0 || (this.minPath>y && y>0)) this.minPath = y;
					}
					else diameter = -1;
				}
			}
			
			spc.reset();
		}
		
		this.averangeDist = this.averangeDist/num;
		
//		loadDataAverangeDist(nodes, nconnected_data, totalDis_data);
//		loadDataConnected(nodes, nconnected_data);
//		loadDataAvergangeToDists(nodes);
//		loadDataNodesThatPointTo(nodes);
	}
	
	public IShortestPathCalculater getSpc()
	{
		return this.spc;
	}
	
	public HashMap<Integer,Integer> getSpectrun(boolean directed)
	{
		HashMap<Integer,Integer> res = new HashMap<Integer,Integer>();
		
		INode[] nodes = this.net.getNodes();
		
		if(directed)
		{
			for(int a=0;a<nodes.length;a++)
			{
				for(int b=0;b<nodes.length;b++)
				{
					if(a!=b)
					{
						Number n = spc.getDistance(nodes[a], nodes[b]);
						if(n!=null)
						{
							Integer dis = new Integer(n.intValue());
							
							if(!res.containsKey(dis))
							{
								res.put(dis, new Integer(1));
							}
							else
							{
								Integer d = res.get(dis);
								Integer c = d.intValue()+1;
								res.put(dis, new Integer(c));
							}
						}
					}
				}
				spc.reset();
			}
		}
		else
		{
			for(int a=0;a<nodes.length;a++)
			{
				for(int b=(a+1);b<nodes.length;b++)
				{
					Number n = spc.getDistance(nodes[a], nodes[b]);
					if(n!=null)
					{
						
						Integer dis = new Integer(n.intValue());
						if(!res.containsKey(dis))
						{
							res.put(dis, new Integer(1));
						}
						else
						{
							Integer d = res.get(dis);
							Integer c = d.intValue()+1;
							res.put(dis, new Integer(c));
						}
					}
				}
				spc.reset();
			}
		}
		
		return res;
	}
	
	public int getDiameter() {
		return diameter;
	}

//	public double getAverangeSP(boolean directed)
//	{		
//		return this.averangeDist;
//	}
//	
//	private void loadDataAverangeDist(INode[] nodes, int[] nconnected_data, double[] totalDis_data)
//	{
//
//		HashMap<String,Double> temp_avergangeDists = new HashMap<String,Double>();
//		
//		for(int i=0;i<nodes.length;i++)
//		{
//			int nconnected = nconnected_data[i];
//			double totalDis = totalDis_data[i];
//			
//			if(nconnected>0)
//			{
//				double averageDis = totalDis/new Double(nconnected).doubleValue();
//			
//				temp_avergangeDists.put(nodes[i].getDb_id()+"@"+nodes[i].getType(), new Double(averageDis));
//			}
//		}
//		
//		Object[] keys = temp_avergangeDists.keySet().toArray();
//
//		this.avergangeDistsIndex = new String[keys.length];
//		this.avergangeDists = new Double[keys.length];
//		
//		for(int i=0;i<keys.length;i++)
//		{
//			this.avergangeDistsIndex[i] = (String)keys[i];
//			this.avergangeDists[i] = temp_avergangeDists.get(keys[i]);
//		}
//	}
	
//	private void loadDataConnected(INode[] nodes, int[] nconnected_data)
//	{
//		HashMap<INode,Integer> temp_connected = new HashMap<INode,Integer>();
//		
//		for(int i=0;i<nodes.length;i++)
//		{
//			int nconnected = nconnected_data[i];
//			
//			if(nconnected>0)
//			{
//				temp_connected.put(nodes[i], new Integer(nconnected));
//			}
//		}
//		
//		Object[] keys = temp_connected.keySet().toArray();
//		
//		this.connectedIndex = new INode[keys.length];
//		this.connected = new Integer[keys.length];
//		
//		for(int i=0;i<keys.length;i++)
//		{
//			this.connectedIndex[i] = (INode)keys[i];
//			this.connected[i] = temp_connected.get(keys[i]);
//		}
//	}
//	
//	private void loadDataAvergangeToDists(INode[] nodes)
//	{
//
//		HashMap<INode,Double> temp_avergangeToDists = new HashMap<INode,Double>();
//		HashMap<INode,Integer> temp_numberOfavergangeToDists = new HashMap<INode,Integer>();
//		
//		for(int i=0;i<nodes.length;i++)
//		{
//			int nconnected = 0;
//			double totalDis = 0;
//			ArrayList<String> notCon = new ArrayList<String>();
//			
//			for(int z=0;z<nodes.length;z++)
//			{
//				if(i!=z)
//				{
//					Number n = spc.getDistance(nodes[i], nodes[z]);
//					if(n!=null)
//					{
//						
//						nconnected++;
//						totalDis += n.doubleValue();
//						
//						if(temp_numberOfavergangeToDists.containsKey(nodes[z]))
//						{
//							int ixt = temp_numberOfavergangeToDists.get(nodes[z]).intValue();
//							double gank = temp_avergangeToDists.get(nodes[z]).doubleValue();
//						
//							ixt++;
//							gank+=n.doubleValue();
//							
//							temp_numberOfavergangeToDists.put(nodes[z], new Integer(ixt));
//							temp_avergangeToDists.put(nodes[z], new Double(gank));
//						}
//						else
//						{
//							temp_numberOfavergangeToDists.put(nodes[z], new Integer(1));
//							temp_avergangeToDists.put(nodes[z], new Double(n.doubleValue()));
//						}
//					}
//					else
//					{
//						notCon.add(nodes[z].toString());
//					}
//				}
//			}
//		}
//		
//		for(int i=0;i<nodes.length;i++)
//		{
//			if(temp_numberOfavergangeToDists.containsKey(nodes[i]))
//			{
//				int ixt = temp_numberOfavergangeToDists.get(nodes[i]).intValue();
//				double gank = temp_avergangeToDists.get(nodes[i]).doubleValue();
//			
//				gank = gank / (new Double(ixt)).doubleValue();
//				temp_avergangeToDists.put(nodes[i], new Double(gank));
//			}
//		}
//		
//		Object[] keys = temp_avergangeToDists.keySet().toArray();
//		
//		this.avergangeToDistsIndex = new INode[keys.length];
//		this.avergangeToDists = new Double[keys.length];
//		
//		for(int i=0;i<keys.length;i++)
//		{
//			this.avergangeToDistsIndex[i] = (INode)keys[i];
//			this.avergangeToDists[i] = temp_avergangeToDists.get(keys[i]);
//		}
//	}
//
//	private void loadDataNodesThatPointTo(INode[] nodes)
//	{
//		HashMap<INode,Integer> temp_nodesThatPointTo = new HashMap<INode,Integer>();
//		
//		for(int i=0;i<nodes.length;i++)
//		{
//			ArrayList<String> notCon = new ArrayList<String>();
//			
//			for(int z=0;z<nodes.length;z++)
//			{
//				if(i!=z)
//				{
//					Number n = spc.getDistance(nodes[i], nodes[z]);
//					if(n!=null)
//					{
//						if(!temp_nodesThatPointTo.containsKey(nodes[z]))
//						{
//							ArrayList<INode> nliz = new ArrayList<INode>();
//							nliz.add(nodes[i]);
//							temp_nodesThatPointTo.put(nodes[z], new Integer(1));
//						}
//						else
//						{
//							int newga = temp_nodesThatPointTo.get(nodes[z]).intValue()+1;
//							temp_nodesThatPointTo.put(nodes[z], new Integer(newga));
//						}
//					}
//					else
//					{
//						notCon.add(nodes[z].toString());
//					}
//				}
//			}
//		}
//		
//		Object[] keys = temp_nodesThatPointTo.keySet().toArray();
//		
//		this.nodesThatPointToIndex = new INode[keys.length];
//		this.nodesThatPointTo = new Integer[keys.length];
//		
//		for(int i=0;i<keys.length;i++)
//		{
//			this.nodesThatPointToIndex[i] = (INode)keys[i];
//			this.nodesThatPointTo[i] = temp_nodesThatPointTo.get(keys[i]);
//		}
//	}
	
	/**
	 * Returns table whit entries for all nodes, each entry contains the number 
	 * of nodes connected and not connected to the node and the average path 
	 * distance that has to be travelled to arrive at that node from any point of the network.
	 * @return
	 */
	
	public QRTable getNodeData(INode n)
	{
		ArrayList<String> columnsNames = new ArrayList<String>();

		columnsNames.add("Data Name");
		columnsNames.add("Data");
		
		QRTable qrt = new QRTable(columnsNames, "Regulations", new int[]{0,0});
		
		INode[] nodes = this.net.getNodes();
		
		int f=0, t=0, maxPathf=0, maxPatht=0;
		double ft=0, tt=0;
		
		for(int i=0;i<nodes.length;i++)
		{
			if(!nodes[i].equals(n))
			{
				Number nu = this.spc.getDistance(n, nodes[i]);
				
				if(nu!=null)
				{
					int c = nu.intValue();
					
					if(maxPathf<c) maxPathf = c;
					
					ft+=nu.doubleValue();
					f++;
				}
				nu = this.spc.getDistance(nodes[i], n);
				
				if(nu!=null)
				{
					
					int c = nu.intValue();
					
					if(maxPatht<c) maxPatht = c;
					
					tt+=nu.doubleValue();
					t++;
				}
			}
			spc.reset();
		}
		
		qrt.addLine(new String[]{"Type", n.getType()});
		qrt.addLine(new String[]{"Name", n.toString()});
		qrt.addLine(new String[]{"Id", n.getDb_id()});
		qrt.addLine(new String[]{"Longest shortest path from the node", ""+maxPathf});
		qrt.addLine(new String[]{"Longest shortest path to the node", ""+maxPatht});
		qrt.addLine(new String[]{"Number of out paths", ""+f});
		qrt.addLine(new String[]{"Number of in paths", ""+t});
		qrt.addLine(new String[]{"Averange out path", ""+(ft/f)});
		qrt.addLine(new String[]{"Averange in path", ""+(tt/t)});
		
		return qrt;
	}
	
	
	public QRTable getConnectedNodes(INode n)
	{
		ArrayList<String> columnsNames = new ArrayList<String>();

		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add("Distance");
		
		QRTable qrt = new QRTable(columnsNames, "Regulations", new int[]{0,0,0,2});
		
		INode[] nodes = this.net.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			if(!nodes[i].equals(n))
			{
				Number nu = this.spc.getDistance(n, nodes[i]);
				
				if(nu!=null)
				{
					Object[] ql = new Object[]{nodes[i].getType(), nodes[i].toString(), nodes[i].getDb_id(), new Integer(nu.intValue())};
					qrt.addLine(ql);
				}
				
			}
		}
		
		spc.reset();
		return qrt;
	}
	
	
	public int getDistance(INode s, INode e)
	{
		Number nu = this.spc.getDistance(s, e);
		
		int res;
		
		if(nu==null) res=-1;
		else res=nu.intValue();
		
		spc.reset();
		return res;
	}
	
	public QRTable getPathTable(INode s, INode e)
	{
		Object[] tres = this.spc.getPath(s, e)[0];
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		
		QRTable qrt = new QRTable(columnsNames, "Regulations", new int[]{0,0});
		
		for(int i=0;i<tres.length;i++)
		{
			INode n = (INode)tres[i];
			qrt.addLine(new String[]{n.getType(),n.toString(),n.getDb_id()});
		}
		
		spc.reset();
		return qrt;
	}
	
//	@SuppressWarnings("unchecked")
//	public QRTable getData2()
//	{
//		ArrayList<String> columnsNames = new ArrayList<String>();
//
//		columnsNames.add("Type");
//		columnsNames.add("Name");
//		columnsNames.add("Id");
//		columnsNames.add("Number of not connected nodes");
//		columnsNames.add("Number of connected nodes");
//		columnsNames.add("Average distances to get to node");
//		
//		QRTable qrt = new QRTable(columnsNames, "Regulations", new int[]{0,0,0,2,2,1});
//		
//		LinkedList<SortabelObject2> lsos = new LinkedList<SortabelObject2>();
//		
//		for(int i=0;i<nodes.length;i++)
//		{
//			INode n = nodes[i];
//			
//			int containsAvergangeToDists = -1;
//			
//			for(int y=0;y<avergangeToDistsIndex.length && containsAvergangeToDists==-1;y++)
//			{
//				if(
//					avergangeToDistsIndex[y].getType().equals(n.getType()) && 
//					avergangeToDistsIndex[y].toString().equals(n.toString())
//				)
//				{
//					containsAvergangeToDists=y;
//				}
//			}
//			
//			if(containsAvergangeToDists!=-1)
//			{
//				Integer numC = null;
//				
//				for(int y=0;numC==null;y++)
//				{
//					
//					if(
//							this.nodesThatPointToIndex[y].getType().equals(n.getType()) && 
//							this.nodesThatPointToIndex[y].toString().equals(n.toString())
//					)
//					{
//						numC = this.nodesThatPointTo[y];
//					}
//				}
//				
//				ArrayList<Object> ql = new ArrayList<Object>();
//				ql.add(n.getType());
//				ql.add(n.toString());
//				ql.add(n.getDb_id());
//				ql.add(new Integer(this.nodes.length-numC.intValue()));
//				ql.add(new Integer(numC.intValue()));
//				ql.add(this.avergangeToDists[containsAvergangeToDists]);
//				lsos.add(new SortabelObject2(this.avergangeToDists[containsAvergangeToDists].doubleValue(), ql));
//			}
//		}
//		
//		SortabelObject2[] sos = new SortabelObject2[lsos.size()];
//		
//		for(int i=0;i<lsos.size();i++)
//		{
//			sos[i] = lsos.get(i);
//		}
//		
//		Arrays.sort(sos);
//		
//		for(int i=0;i<sos.length;i++)
//		{
//			ArrayList<Object> ql = (ArrayList<Object>)(sos[i].getNode());
//    		qrt.addLine(ql);
//		}
//
//		this.numberOfInNodes = sos.length;
//		
//		return qrt;
//	}
//	
//	/**
//	 * Returns table whit entries for all nodes, each entry contains the number 
//	 * of nodes connected and not connected to the node and the average path 
//	 * distance form that node to all nodes of the network.
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public QRTable getData()
//	{
//		ArrayList<String> columnsNames = new ArrayList<String>();
//
//		columnsNames.add("Type");
//		columnsNames.add("Name");
//		columnsNames.add("Id");
//		columnsNames.add("Number of not connected nodes");
//		columnsNames.add("Number of connected nodes");
//		columnsNames.add("Average distances");
//		
//		QRTable qrt = new QRTable(columnsNames, "Regulations", new int[]{0,0,0,2,2,1});
//		
//		ArrayList<SortabelObject2> lsos = new ArrayList<SortabelObject2>();
//		
//		for(int i=0;i<nodes.length;i++)
//		{
//			INode n = nodes[i];
//			
//			if(this.net.outDegree(n)>0)
//			{
//				Integer numC = null;
//				
//				for(int y=0;numC==null && y<this.connectedIndex.length;y++)
//				{
//					
//					if(
//							this.connectedIndex[y].getType().equals(n.getType()) && 
//							this.connectedIndex[y].toString().equals(n.toString())
//					)
//					{
//						numC = this.connected[y];
//					}
//				}
//				
//				if(numC==null) numC = new Integer(0);
//				
//				ArrayList<Object> ql = new ArrayList<Object>();
//				ql.add(n.getType());
//				ql.add(n.toString());
//				ql.add(n.getDb_id());
//				ql.add(new Integer(this.nodes.length-numC.intValue()));
//				ql.add(new Integer(numC.intValue()));
//				
//				Double ad = null;
//
//				for(int y=0;ad==null && y<this.avergangeDistsIndex.length;y++)
//				{
//					if(this.avergangeDistsIndex[y].equals(n.getDb_id()+"@"+n.getType()))
//					{
//						ad = avergangeDists[y];
//					}
//				}
//				
//				if(ad==null) ad = new Double(0);
//				
//				ql.add(new Double(ad.doubleValue()));
//				lsos.add(new SortabelObject2(ad.doubleValue(), ql));
////        		qrt.addLine(ql);
//			}
//		}
//		
//		SortabelObject2[] sos = new SortabelObject2[lsos.size()];
//		
//		for(int i=0;i<lsos.size();i++)
//		{
//			sos[i] = lsos.get(i);
//		}
//		
//		Arrays.sort(sos);
//		
//		for(int i=0;i<sos.length;i++)
//		{
//			ArrayList<Object> ql = (ArrayList<Object>)(sos[i].getNode());
//    		qrt.addLine(ql);
//		}
//
//		this.numberOfOutNodes = sos.length;
//		
//		return qrt;
//	}
//	
//	/**
//	 * Returns a table with the shorts path  between a node and all nodes it connects to.
//	 * @param n
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public QRTable getData(INode n)
//	{
//		ArrayList<String> columnsNames = new ArrayList<String>();
//
//		columnsNames.add("Target Type");
//		columnsNames.add("Target Name");
//		columnsNames.add("Target Id");
//		columnsNames.add("Distances");
//		
//		QRTable qrt = new QRTable(columnsNames, "Regulations", new int[]{0,2});
//		
//		LinkedList<SortabelObject2> lsos = new LinkedList<SortabelObject2>();
//		
//		for(int i=0;i<nodes.length;i++)
//		{
//			Number distance = null;
//			
//			distance =  spc.getDistance(n, nodes[i]);
//			
//			
//			if(distance!=null)
//			{
//				ArrayList<Object> ql = new ArrayList<Object>();
//				ql.add(nodes[i].getType());
//				ql.add(nodes[i].toString());
//				ql.add(nodes[i].getDb_id());
//				ql.add(new Integer(distance.intValue()));
//				lsos.add(
//					new SortabelObject2(distance.doubleValue(), ql)
//				);
//			}
//		}
//		
//		SortabelObject2[] sos = new SortabelObject2[lsos.size()];
//		
//		for(int i=0;i<lsos.size();i++)
//		{
//			sos[i] = lsos.get(i);
//		}
//		
//		Arrays.sort(sos);
//		
//		for(int i=0;i<sos.length;i++)
//		{
//			ArrayList<Object> ql = (ArrayList<Object>)(sos[i].getNode());
//    		qrt.addLine(ql);
//		}
//		
//		return qrt;
//	}
//	
//	/**
//	 * Returns the shortest path between two nodes.
//	 * @param source
//	 * @param target
//	 * @return
//	 */
//
//	public INode[] getNodes() {
//		return nodes;
//	}
	
	public int getNumberOfInNodes() {
		return numberOfInNodes;
	}

	public int getNumberOfOutNodes() {
		return numberOfOutNodes;
	}
	
	public Object[][] getPath(INode source, INode dest)
	{
		Object[][] res = this.spc.getPath(source, dest);
		spc.reset();
		
		return res;
	}
	
	public INetwork getNetwork()
	{
		return this.net;
	}
	
	public double getAverangeDist()
	{
		return this.averangeDist;
	}

	public int getMaxPath() {
		return maxPath;
	}

	public int getMinPath() {
		return minPath;
	}
	
}
