package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics.JungHITSRankingData;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics.ClusteringMetrics;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics.RankingData;

public class NetworkComparison {

	public static INode[] decisionPoints(INetwork net1, INetwork net2, String type)
	{
		INode[] n1 = net1.getNodes(type);
		INode[] n2 = net2.getNodes(type);
		
		ArrayList<INode> temp = new ArrayList<INode>();
		
		for(int a=0;a<n1.length;a++)
		{
			INode node1 = n1[a];
			INode node2 = null;
			for(int b=0;node2==null && b<n2.length;b++)
			{
				if(n2[b].equals(node1)) node2 = n2[b];
			}
			
			if(node2!=null)
			{
//				IEdge[] ie1 = net1.getInEdges(node1);
				IEdge[] oe1 = net1.getOutEdges(node1);
//				IEdge[] ie2 = net2.getInEdges(node2);
				IEdge[] oe2 = net2.getOutEdges(node2);
				
				ArrayList<INode> con1 = new ArrayList<INode>();
				ArrayList<INode> con2 = new ArrayList<INode>();
				
//				for(int i=0;i<ie1.length;i++)
//				{
//					con1.add(net1.getConectedNodes(ie1[i])[0]);
//				}
				
				for(int i=0;i<oe1.length;i++)
				{
					con1.add(net1.getConectedNodes(oe1[i])[1]);
				}

//				for(int i=0;i<ie2.length;i++)
//				{
//					con2.add(net2.getConectedNodes(ie2[i])[0]);
//				}
				
				for(int i=0;i<oe2.length;i++)
				{
					con2.add(net2.getConectedNodes(oe2[i])[1]);
				}
				
				boolean divergency = false;
				
				if(con1.size()!=con2.size()) divergency = true;
				else
				{
					for(int i=0;!divergency && i<con1.size();i++)
					{
						boolean stop = false;
						
						for(int z=0;!stop && z<con2.size();z++)
						{
							if(con1.get(i).equals(con2.get(z)))
							{
								stop = true;
							}
						}
						
						if(!stop)
						{
							divergency = true;
						}
					}
				}
				
				if(divergency)
				{
					temp.add(node1);
				}
				
			}
		}
		
		INode[] res = new INode[temp.size()];
		
		for(int i=0;i<res.length;i++) res[i] = temp.get(i);
		
		return res;
	}
	
	public static double[] networkJacard(String[] types, INetwork net1, INode[] net1exclusive, INode[] net2exclusive)
	{
		double[] res = new double[types.length];
		
		for(int i=0;i<types.length;i++)
		{
			res[i] = 0;
			if(types[i].equals("All"))
			{
				double intersection = net1.getNodes().length - net1exclusive.length;
				double union = net1.getNodes().length + net2exclusive.length;
				res[i] = intersection/union;
			}
			else
			{
				double numt1 = net1.getNodes(types[i]).length;
				double nume1 = 0;
				double nume2 = 0;
				
				for(int u=0;u<net1exclusive.length;u++)
					if(net1exclusive[u].getType().equals(types[i]))
						nume1++;
				
				for(int u=0;u<net2exclusive.length;u++)
					if(net2exclusive[u].getType().equals(types[i]))
						nume2++;
				
				double intersection = numt1 - nume1;
				double union = numt1 + nume2;
				
				res[i] = intersection/union;
			}
		}
		
		return res;
	}
	
	public static double[] networkJacard(String[] types, INetwork net1, INetwork net2)
	{
		INode[][] temp = exclusiveNodes(net1, net2);
		
		INode[] net1exclusive = new INode[temp[0].length];
		INode[] net2exclusive = new INode[temp[1].length];
		
		for(int i=0;i<temp[0].length;i++)
		{
			net1exclusive[i] = temp[0][i];
		}
		
		for(int i=0;i<temp[1].length;i++)
		{
			net2exclusive[i] = temp[1][i];
		}
		
		return networkJacard(types, net1, net1exclusive, net2exclusive);
	}
	
	public static INode[][] exclusiveNodes(INetwork net1, INetwork net2)
	{
		ArrayList<INode> lis1 = new ArrayList<INode>();
		ArrayList<INode> lis2 = new ArrayList<INode>();
		
		INode[] n1 = net1.getNodes();
		INode[] n2 = net2.getNodes();
		
		for(int a=0;a<n1.length;a++)
		{
			boolean stop = false;
			
			for(int b=0;b<n2.length && !stop;b++)
			{
				stop = n1[a].equals(n2[b]);
			}
			if(!stop)
			{
				lis1.add(n1[a]);
			}
		}
		
		for(int a=0;a<n2.length;a++)
		{
			boolean stop = false;
			
			for(int b=0;b<n1.length && !stop;b++)
			{
				stop = n2[a].equals(n1[b]);
			}
			if(!stop)
			{
				lis2.add(n2[a]);
			}
		}
		
		INode[][] res = new INode[][]{new INode[lis1.size()], new INode[lis2.size()],};
		
		for(int i=0;i<lis1.size();i++)
		{
			res[0][i] = lis1.get(i);
		}

		for(int i=0;i<lis2.size();i++)
		{
			res[1][i] = lis2.get(i);
		}
		
		return res;
	}
	
	public static HashMap<String[],Double[]> compareRankers(RankingData r1, RankingData r2)
	{
		INode[] n1 = r1.getNodes();
		INode[] n2 = r2.getNodes();
		
		ArrayList<String[]> nodes = new ArrayList<String[]>();
		ArrayList<Integer[]> pos = new ArrayList<Integer[]>();
		
		for(int a=0;a<n1.length;a++)
		{
			INode node1 = n1[a];
			int p = -1;
			
			for(int b=0;b<n2.length && p==-1;b++)
			{
				if(n2[b].equals(node1)) p = b;
			}
			
			nodes.add(new String[]{node1.toString(), node1.getDb_id(), node1.getType()});
			Integer[] res = new Integer[2];
			res[0] = new Integer(a);
			res[1] = new Integer(p);
			pos.add(res);
		}
		
		for(int a=0;a<n2.length;a++)
		{
			boolean stop = false;
			
			for(int b=0;b<n1.length && !stop;b++)
			{
				stop = n2[a].equals(n1[b]);
			}
			if(!stop)
			{
				nodes.add(new String[]{n2[a].toString(), n2[a].getDb_id(), n2[a].getType()});
				Integer[] res = new Integer[2];
				res[0] = new Integer(-1);
				res[1] = new Integer(a);
				pos.add(res);
			}
		}
		
		HashMap<String[],Double[]> res = new HashMap<String[],Double[]>();
		
		if((r1 instanceof JungHITSRankingData) && (r2 instanceof JungHITSRankingData))
		{
			double[] hub1 = ((JungHITSRankingData)r1).getHubnessValues();
			double[] hub2 = ((JungHITSRankingData)r2).getHubnessValues();
			double[] aut1 = ((JungHITSRankingData)r1).getAuthorityValues();
			double[] aut2 = ((JungHITSRankingData)r2).getAuthorityValues();
			
			for(int i=0;i<nodes.size();i++)
			{
				String[] node = nodes.get(i);
			
				Integer[] poss = pos.get(i);
			
				Double[] ranks = new Double[4];
			
				if(poss[0].intValue()!=-1)
				{
					poss[0].intValue();
//					double d = hub1[poss[0].intValue()];
					
					ranks[0] = new Double(hub1[poss[0].intValue()]);
					ranks[1] = new Double(aut1[poss[0].intValue()]);
				}
				else
				{
					ranks[0] = null;
					ranks[1] = null;
				}
				if(poss[1].intValue()!=-1)
				{
					ranks[2] = new Double(hub2[poss[1].intValue()]);
					ranks[3] = new Double(aut2[poss[1].intValue()]);
				}
				else
				{
					ranks[2] = null;
					ranks[3] = null;
				}
			
				res.put(node, ranks);
			}
		}
		else
		{
			double[] ranks1 = r1.getRanker();
			double[] ranks2 = r2.getRanker();
			
			for(int i=0;i<nodes.size();i++)
			{
				String[] node = nodes.get(i);
			
				Integer[] poss = pos.get(i);
			
				Double[] ranks = new Double[2];
			
				if(poss[0].intValue()!=-1) ranks[0] = new Double(ranks1[poss[0].intValue()]);
				else ranks[0] = null;
				if(poss[1].intValue()!=-1) ranks[1] = new Double(ranks2[poss[1].intValue()]);
				else ranks[1] = null;
			
				res.put(node, ranks);
			}
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String[],Double[]> compareClusteringCoefficients(ClusteringMetrics c1, ClusteringMetrics c2)
	{
		INode[] n1 = c1.getNodes();
		INode[] n2 = c2.getNodes();
		
		Map cc1 = c1.getCc();
		Map cc2 = c2.getCc();
		
		HashMap<String[],Double[]> res = new HashMap<String[],Double[]>();
		
		for(int a=0;a<n1.length;a++)
		{
			INode node1 = n1[a];
			INode node2 = null;
			
			for(int b=0;b<n2.length && node2==null;b++)
			{
				if(n2[b].equals(node1)) node2 = n2[b];
			}
			
			Double rcc1 = new Double(cc1.get(node1).toString());
			Double rcc2 = null;
			
			if(node2!=null)
			{
				rcc2 = new Double(cc2.get(node2).toString());
			}
			
			res.put(new String[]{node1.getDb_id(), node1.getType(), node1.toString()}, new Double[]{rcc1, rcc2});
			
		}
		
		for(int a=0;a<n2.length;a++)
		{
			boolean stop = false;
			
			for(int b=0;b<n1.length && !stop;b++)
			{
				stop = n2[a].equals(n1[b]);
			}
			if(!stop)
			{
				Double rcc2 = new Double(cc2.get(n2[a]).toString());
				
				res.put(new String[]{n2[a].getDb_id(), n2[a].getType(), n2[a].toString()}, new Double[]{null, rcc2});
			}
		}
		
		return res;
	}
	
	public static Double[][] compareCk(ClusteringMetrics c1, ClusteringMetrics c2)
	{

		double[][] ck1 = c1.getCk();
		double[][] ock1 = c1.getOck();
		double[][] ick1 = c1.getIck();
		
		double[][] ck2 = c2.getCk();
		double[][] ock2 = c2.getOck();
		double[][] ick2 = c2.getIck();
		
		ArrayList<Double> tindex = new ArrayList<Double>();
		
		for(int i=0;i<ck1.length;i++)
		{
			tindex.add(new Double(ck1[i][0]));
		}
		
		for(int i=0;i<ock1.length;i++)
		{
			Double c = new Double(ock1[i][0]);
			
			if(!tindex.contains(c)) tindex.add(new Double(ock1[i][0]));
		}
		
		for(int i=0;i<ock1.length;i++)
		{
			Double c = new Double(ock1[i][0]);
			
			if(!tindex.contains(c)) tindex.add(c);
		}
		
		for(int i=0;i<ick1.length;i++)
		{
			Double c = new Double(ick1[i][0]);
			
			if(!tindex.contains(c)) tindex.add(c);
		}
		
		for(int i=0;i<ck2.length;i++)
		{
			Double c = new Double(ck2[i][0]);
			
			if(!tindex.contains(c)) tindex.add(c);
		}
		
		for(int i=0;i<ock2.length;i++)
		{
			Double c = new Double(ock2[i][0]);
			
			if(!tindex.contains(c)) tindex.add(c);
		}
		
		for(int i=0;i<ick2.length;i++)
		{
			Double c = new Double(ick2[i][0]);
			
			if(!tindex.contains(c)) tindex.add(c);
		}
	
		double[] index = new double[tindex.size()];
		
		for(int i=0;i<tindex.size();i++)
		{
			index[i] = tindex.get(i);
		}
		
		Arrays.sort(index);
		
		Double[][] res = new Double[index.length][7];
		
		for(int i=0;i<index.length;i++)
		{
			Double tck1 = null;
			Double tock1 = null;
			Double tick1 = null;
			Double tck2 = null;
			Double tock2 = null;
			Double tick2 = null;
			
			double id = index[i];
			
			for(int a=0;a<ck1.length && tck1==null;a++)
			{
				if(ck1[a][0]==id) tck1 = new Double(ck1[a][1]);
			}
			for(int a=0;a<ock1.length && tock1==null;a++)
			{
				if(ock1[a][0]==id) tock1 = new Double(ock1[a][1]);
			}
			for(int a=0;a<ick1.length && tick1==null;a++)
			{
				if(ick1[a][0]==id) tick1 = new Double(ick1[a][1]);
			}
			for(int a=0;a<ck2.length && tck2==null;a++)
			{
				if(ck2[a][0]==id) tck2 = new Double(ck2[a][1]);
			}
			for(int a=0;a<ock2.length && tock2==null;a++)
			{
				if(ock2[a][0]==id) tock2 = new Double(ock2[a][1]);
			}
			for(int a=0;a<ick2.length && tick2==null;a++)
			{
				if(ick2[a][0]==id) tick2 = new Double(ick2[a][1]);
			}
			
			if(tck1==null) tck1 = new Double(0);
			if(tock1==null) tock1 = new Double(0);
			if(tick1==null) tick1 = new Double(0);
			if(tck2==null) tck2 = new Double(0);
			if(tock2==null) tock2 = new Double(0);
			if(tick2==null) tick2 = new Double(0);
			
			res[i] = new Double[]{new Double(id), tck1, tock1, tick1, tck2, tock2, tick2};
		}
		
		return res;
		
	}
	
	public static HashMap<String[],Integer[]> compareDegree(INetwork net1, INetwork net2)
	{
		INode[] n1 = net1.getNodes();
		INode[] n2 = net2.getNodes();
		
		ArrayList<String[]> nodes = new ArrayList<String[]>();
		ArrayList<INode[]> pos = new ArrayList<INode[]>();
		
		for(int a=0;a<n1.length;a++)
		{
			INode node1 = n1[a];
			INode node2 = null;
			
			for(int b=0;b<n2.length && node2==null;b++)
			{
				if(n2[b].equals(node1)) node2 = n2[b];
			}
			
			nodes.add(new String[]{node1.getDb_id(), node1.getType(), node1.toString()});
			INode[] res = new INode[2];
			res[0] = node1;
			res[1] = node2;
			pos.add(res);
		}
		
		for(int a=0;a<n2.length;a++)
		{
			boolean stop = false;
			
			for(int b=0;b<n1.length && !stop;b++)
			{
				stop = n2[a].equals(n1[b]);
				
			}
			if(!stop)
			{
				INode node2 = n2[a];
				nodes.add(new String[]{n2[a].getDb_id(), n2[a].getType(), n2[a].toString()});
				INode[] res = new INode[2];
				res[0] = null;
				res[1] = node2;
				pos.add(res);
			}
		}
		
		HashMap<String[],Integer[]> res = new HashMap<String[],Integer[]>();
		
		for(int i=0;i<nodes.size();i++)
		{
			String[] node = nodes.get(i);
		
			INode[] poss = pos.get(i);
		
			Integer[] degree = new Integer[6];
		
			if(poss[0]!=null)
			{
				degree[0] = new Integer(net1.degree(poss[0]));
				degree[1] = new Integer(net1.outDegree(poss[0]));
				degree[2] = new Integer(net1.inDegree(poss[0]));
			}
			else
			{
				degree[0] = null;
				degree[1] = null;
				degree[2] = null;
			}
			if(poss[1]!=null)
			{
				degree[3] = new Integer(net2.degree(poss[1]));
				degree[4] = new Integer(net2.outDegree(poss[1]));
				degree[5] = new Integer(net2.inDegree(poss[1]));
			}
			else
			{
				degree[3] = null;
				degree[4] = null;
				degree[5] = null;
			}
		
			res.put(node, degree);
		}
		
		return res;
	}

	public static INode[] compareDirection(INetwork net1, INetwork net2, String[] invertableEdges, String invertableNodeType, String tagName, String invalidTag) 
		//0 - nill, 1-original, 2-reversed
	{
		INode[] ns = net1.getNodes(invertableNodeType);
		
		boolean[] inv = new boolean[ns.length];
		
		int z = 0;
		
//		System.out.println("ns.length "+ns.length);
		
		for(int i=0;i<ns.length;i++)
		{
			inv[i] = false;
			INode ns1 = ns[0];
			INode ns2 = net2.getNode(ns1.getDb_id(), ns1.getType());
			
			if(ns2!=null)
			{
				IEdge[] es1 = pruneNodes(net1.getOutEdges(ns1), invertableEdges, tagName, invalidTag);
				IEdge[] es2 = pruneNodes(net2.getOutEdges(ns2), invertableEdges, tagName, invalidTag);
				IEdge[] ee1 = pruneNodes(net1.getInEdges(ns1), invertableEdges, tagName, invalidTag);
				IEdge[] ee2 = pruneNodes(net2.getInEdges(ns2), invertableEdges, tagName, invalidTag);
//				System.out.println("Is GO "+es1.length+" "+es2.length+" "+ee1.length+" "+ee2.length);
				
				inv[i] = compareDirectionAux(net1, net2, es1, es2, ee1, ee2, invertableEdges);
				
				if(inv[i]) z++;
			}
		}
		
		INode[] res = new INode[z];
		
		z = 0;
		
		for(int i=0;i<ns.length;i++)
		{
			if(inv[i])
			{
				res[z] = ns[i];
				z++;
			}
		}
		
		return res;
	}
	
	protected static boolean compareDirectionAux(INetwork n1, INetwork n2, IEdge[] es1, IEdge[] es2, IEdge[] ee1, IEdge[] ee2, String[] invertableEdges)
	{
		boolean res = false;
		
		for(int a=0;a<es1.length && !res;a++)
		{
			INode no1 = n1.getConectedNodes(es1[a])[1];
			
			boolean ok = true;
				
			for(int b=0;b<ee1.length && ok;b++)
			{
				INode ne1 = n1.getConectedNodes(ee1[b])[0];
				
				if(no1.equals(ne1)) ok = false;
			}
			
			if(ok)
			{
				for(int b=0;b<ee2.length && !res;b++)
				{
					INode no2 = n2.getConectedNodes(ee2[b])[0];
					
					if(no1.equals(no2))
					{
						boolean secok = true;
						
						for(int c=0;c<es2.length && !res;c++)
						{
							INode ne2 = n2.getConectedNodes(es2[c])[1];
							
							if(ne2.equals(no1)) secok = false;
						}
						
						if(secok) res = true;
					}
				}
			}
		}
		
		return res;
	}
	
	protected static IEdge[] pruneNodes(IEdge[] es, String[] invertableEdges, String tagName, String invalidTag)
	{
		boolean[] aux = new boolean[es.length];
		
		int z = 0;
		
		for(int i=0;i<es.length;i++)
		{
			if(isType(es[i].isType(), invertableEdges))
			{
				String tag = es[i].getData(tagName);
				
				if(tag!=null && !tag.equals(invalidTag))
				{
					aux[i] = true;
					z++;
				}
				else aux[i] = false;
			}
			else aux[i] = false;
		}
		
		IEdge[] res = new IEdge[z];
		
		z=0;
		
		for(int i=0;i<aux.length;i++)
		{
			if(aux[i])
			{
				res[z] = es[i];
				z++;
			}
		}
		
		return res;
	}
	
	protected static boolean isType(String type, String[] types)
	{
		boolean res = false;
		
		for(int i=0;i<types.length && !res;i++)
		{
			res = types[i].equals(type);
		}
		
		return res;
	}
	
	
	public static String[][][] exclusiveEdges(INetwork net1, INetwork net2)
	{
		INode[] n = net1.getNodes();
		
		ArrayList<String[]> lis1 = new ArrayList<String[]>();
		ArrayList<String[]> lis2 = new ArrayList<String[]>();
		
		for(int a=0;a<n.length;a++)
		{
			IEdge[] eds = net1.getOutEdges(n[a]);
			
			for(int b=0;b<eds.length;b++)
			{
				INode en = net1.getConectedNodes(eds[b])[1];
				if(!inList(lis1, eds[b].isType(), n[a].getDb_id(), n[a].getType(), en.getDb_id(), en.getType()))
				{
					if(!inGraph(n[a].getDb_id(), n[a].getType(), en.getDb_id(), en.getType(), eds[b].isType(), net2))
					{
						String[] newE =
							new String[]{eds[b].isType(), n[a].getDb_id(), n[a].getType(), en.getDb_id(), en.getType(), n[a].toString(), en.toString()};
						lis1.add(newE);
					}
				}
			}
		}
		
		n = net2.getNodes();
		
		for(int a=0;a<n.length;a++)
		{
			IEdge[] eds = net2.getOutEdges(n[a]);
			
			for(int b=0;b<eds.length;b++)
			{
				INode en = net2.getConectedNodes(eds[b])[1];
				if(!inList(lis2, eds[b].isType(), n[a].getDb_id(), n[a].getType(), en.getDb_id(), en.getType()))
				{
					if(!inGraph(n[a].getDb_id(), n[a].getType(), en.getDb_id(), en.getType(), eds[b].isType(), net1))
					{
//						String[] newE = new String[]{eds[b].isType(), n[a].getDb_id(), n[a].getType(), en.getDb_id(), en.getType()};
						String[] newE =
							new String[]{eds[b].isType(), n[a].getDb_id(), n[a].getType(), en.getDb_id(), en.getType(), n[a].toString(), en.toString()};
						lis2.add(newE);
					}
				}
				
			}
		}

		
		String[][][] res = new String[2][][];
		res[0] = new String[lis1.size()][];
		res[1] = new String[lis2.size()][];
		
		for(int i=0;i<lis1.size();i++)
		{
			res[0][i] = lis1.get(i);
		}

		for(int i=0;i<lis2.size();i++)
		{
			res[1][i] = lis2.get(i);
		}
		
		return res;
	}
	
	protected static boolean inGraph(String sid, String stype, String eid, String etype, String type, INetwork net)
	{
		INode snode = net.getNode(sid, stype);
		INode enode = net.getNode(eid, etype);
		
		if(snode!=null && enode!=null)
		{
			IEdge[] eds = net.getOutEdges(snode);
			
			boolean found = false;
			
			for(int i=0;i<eds.length && !found;i++)
			{
				if(eds[i].isType().equals(type))
				{
					found = (enode==net.getConectedNodes(eds[i])[1]);
				}
			}
			
			return found;
		}
		else return false;
	}
	
	protected static boolean inList(ArrayList<String[]> lis, String type, String sid, String stype, String eid, String etype)
	{
		boolean found = false;
		
		for(int i=0;i<lis.size() && !found;i++)
		{
			String[] fm = lis.get(i);
			
			if(fm[0]==type && fm[1]==sid && fm[2]==stype && fm[3]==eid && fm[4]==etype)
				found = true;
		}
		
		return found;
	}
}
