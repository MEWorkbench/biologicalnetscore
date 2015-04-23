package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.SortabelObject;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.util.EdgeType;

public class SubGraphsFinder extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String[][] subGraphs;

	protected INetwork nw;

	protected IShortestPathCalculater spc;
	
	@SuppressWarnings("unchecked")
	public SubGraphsFinder(INetwork nw, IShortestPathCalculater spc)
	{
		this.nw = nw;
		this.spc = spc;
		ArrayList<INode> allTheNodes = new ArrayList<INode>();
		ArrayList<ArrayList<INode>> subGraphsTemp = new ArrayList<ArrayList<INode>>();
		
		INode[] nodes = nw.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			
			allTheNodes.add(n);
		}
		
		getSubGraphs(subGraphsTemp, allTheNodes);
		
		SortabelObject[] sos = new SortabelObject[subGraphsTemp.size()];
		
		for(int i=0;i<subGraphsTemp.size();i++)
		{
			ArrayList<INode> nds = subGraphsTemp.get(i);
			
			sos[i] = new SortabelObject(nds.size(), nds);
		}

		Arrays.sort(sos);
		
		this.subGraphs = new String[sos.length][];
		
		for(int i=0;i<sos.length;i++)
		{
			ArrayList<INode> sub = (ArrayList<INode>)sos[i].getNode();
			this.subGraphs[i] = new String[sub.size()];
			
			for(int s=0;s<sub.size();s++)
				this.subGraphs[i][s] = sub.get(s).getDb_id()+"@"+sub.get(s).getType();
				
		}
	}
	
	public void getSubGraphs(ArrayList<ArrayList<INode>> subGraphsTemp, ArrayList<INode> allTheNodes)
	{
		while(!allTheNodes.isEmpty())
		{
			INode n = allTheNodes.get(0);
		
			allTheNodes.remove(0);
			
			ArrayList<INode> sgraph = new ArrayList<INode>();
		
			sgraph.add(n);
			
			getSubGraph(allTheNodes, sgraph);
			
			subGraphsTemp.add(sgraph);
		}
	}
	
	public void getSubGraph(ArrayList<INode> allnodes, ArrayList<INode> sgraph)
	{
		int anodos = allnodes.size();
		
		ArrayList<INode> toRemove = new ArrayList<INode>();

		for(int x=0;x<sgraph.size();x++)
		{
			INode asagi = sgraph.get(x);
			
			for(int i=0;i<allnodes.size();i++)
			{
				INode n = allnodes.get(i);
			
				if(!toRemove.contains(n))
				{
					
					Number zam1 = this.spc.getDistance(asagi, n);
					Number zam2 = this.spc.getDistance(n, asagi);
					
					if(zam1!=null || zam2!=null)
					{
						toRemove.add(n);
					}
				}
			}
		}
		
		for(int i=0;i<toRemove.size();i++)
		{
			INode n = toRemove.get(i);
			allnodes.remove(n);
			sgraph.add(n);
		}
		
		if(anodos>allnodes.size())
		{
			getSubGraph(allnodes, sgraph);
		}
	}
	
	public int getNumberOfSubGraphs()
	{
		return this.subGraphs.length;
	}
	
//	public QRTable getSubGraphNodes(int i)
//	{
//		return this.nw.getNodesData(this.subGraphs[i]);
//	}
	
	public String getName()
	{
		return this.nw.getName();
	}
	
	public INetwork getNetwork()
	{
		return this.nw;
	}
	
	public String[] getEdgeTypes() {
		return this.nw.getEdgeTypesArray();
	}

	public String[] getNodeTypes() {
		return this.nw.getNodeTypesArray();
	}

	public QRTable getTypeValues(int subgraph, String type) {
		
		boolean first = true;
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("From name");
		columnsNames.add("From id");
		columnsNames.add("To name");
		columnsNames.add("To id");
		columnsNames.add("Directed");
		
		QRTable qrt = null;
		Object[] edges = this.nw.getEdges();
		
		for(int i=0;i<edges.length;i++)
		{
			ArrayList<Object> ql = new ArrayList<Object>();
			
			
				JungEdge nde = (JungEdge)edges[i];
				
				if(first)
				{
					String[] dts = nde.getDatabase_datatype();
					for(int x=0;x<dts.length;x++)
					{
						columnsNames.add(dts[x]);
					}
					first = false;
					qrt = new QRTable(columnsNames, "sub-"+nw.getName()+" "+subgraph);
				}
				
				INode[] p = this.nw.getConectedNodes(nde);

				boolean ok1 = nodeIsThere(subgraph, (JungNode)p[0]);
				boolean ok2 = nodeIsThere(subgraph, (JungNode)p[1]);
				
				if(ok1 && ok2 && nde.isType().equals(type))
				{
					ql.add(nde.isType());
					ql.add(p[0].toString());
					ql.add(p[0].getDb_id());
					ql.add(p[1].toString());
					ql.add(p[1].getDb_id());
					ql.add(""+(((JungNetwork)this.nw).getGraph().getEdgeType(nde)==EdgeType.DIRECTED));
					String[] dts = nde.getDatabase_data();
					for(int x=0;x<dts.length;x++)
					{
						ql.add(dts[x]);
					}
	        		qrt.addLine(ql);
				}
			
		}

        return qrt;
        
//		return this.nw.getTypeValues(i);
	}

	public QRTable getTypeValues(int subgraph) {
		
		boolean first = true;
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("From name");
		columnsNames.add("From id");
		columnsNames.add("To name");
		columnsNames.add("To id");
		columnsNames.add("Directed");
		
		QRTable qrt = null;
		Object[] edges = this.nw.getEdges();
		
		for(int i=0;i<edges.length;i++)
		{
			ArrayList<Object> ql = new ArrayList<Object>();
			
			
				JungEdge nde = (JungEdge)edges[i];
				
				if(first)
				{
//					String[] dts = nde.getDatabase_datatype();
//					for(int x=0;x<dts.length;x++)
//					{
//						columnsNames.add(dts[x]);
//					}
					first = false;
					qrt = new QRTable(columnsNames, "sub-"+nw.getName()+" "+subgraph);
				}
				
				INode[] p = this.nw.getConectedNodes(nde);

				boolean ok1 = nodeIsThere(subgraph, (JungNode)p[0]);
				boolean ok2 = nodeIsThere(subgraph, (JungNode)p[1]);
				
				if(ok1 &&ok2)
				{
					ql.add(nde.isType());
					ql.add(p[0].toString());
					ql.add(p[0].getDb_id());
					ql.add(p[1].toString());
					ql.add(p[1].getDb_id());
					ql.add(""+(((JungNetwork)this.nw).getGraph().getEdgeType(nde)==EdgeType.DIRECTED));
//					String[] dts = nde.getDatabase_data();
//					for(int x=0;x<dts.length;x++)
//					{
//						ql.add(dts[x]);
//					}
	        		qrt.addLine(ql);
				}
			
		}

        return qrt;
		
//		return this.nw.getTypeValues();
	}
	
	public QRTable getNodesValues(int subgraph)
	{
		INode[] nodes = this.nw.getNodes();
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add("Indegree");
		columnsNames.add("Outdegree");
		
		QRTable qrt = new QRTable(columnsNames, "sub-"+this.nw.toString()+" "+subgraph);
		
		for(int i=0;i<nodes.length;i++)
		{
			boolean ok = nodeIsThere(subgraph, nodes[i]);
//			boolean ok = false;
//			
//			String ndata = nodes[i].getDb_id()+"@"+nodes[i].getType();
//			
//			for(int d=0;d<subGraphs[subgraph].length && !ok;d++)
//			{
//				ok = subGraphs[subgraph][d].equals(ndata);
//			}
			
			if(ok)
			{
				JungNode n = (JungNode)nodes[i];
				ArrayList<Object> ql = new ArrayList<Object>();
				ql.add(n.getType());
				ql.add(n.toString());
				ql.add(n.getDb_id());
				ql.add(""+this.nw.inDegree(n));
				ql.add(""+this.nw.outDegree(n));
        		qrt.addLine(ql);
			}
		}

        return qrt;
		
//		return this.nw.getNodesValues();
	}
	
	public QRTable getNodeTypeValues(int subgraph, String t)
	{
		INode[] nodes = this.nw.getNodes();
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add("Indegree");
		columnsNames.add("Outdegree");
		
		QRTable qrt = new QRTable(columnsNames, "sub-"+this.nw.toString()+" "+subgraph);
		
		for(int i=0;i<nodes.length;i++)
		{
			boolean ok = nodeIsThere(subgraph, nodes[i]);
			
//			String ndata = nodes[i].getDb_id()+"@"+nodes[i].getType();
//			
//			for(int d=0;d<subGraphs[subgraph].length && !ok;d++)
//			{
//				ok = subGraphs[subgraph][d].equals(ndata);
//			}
			
			if(ok && nodes[i].getType().equals(t))
			{
				JungNode n = (JungNode)nodes[i];
				ArrayList<Object> ql = new ArrayList<Object>();
				ql.add(n.getType());
				ql.add(n.toString());
				ql.add(n.getDb_id());
				ql.add(""+this.nw.inDegree(n));
				ql.add(""+this.nw.outDegree(n));
        		qrt.addLine(ql);
			}
		}

        return qrt;
        
//		return this.nw.getNodeTypeValues(t);
	}
	
	
	protected boolean nodeIsThere(int sg, INode node)
	{
		String ndata = node.getDb_id()+"@"+node.getType();
		
		boolean ok = false;
		
		for(int d=0;d<subGraphs[sg].length && !ok;d++)
		{
			ok = subGraphs[sg][d].equals(ndata);
		}
		
		return ok;
	}
	
	public INetwork getNw() {
		return nw;
	}
	
	public String[][] getSubGraphs() {
		return subGraphs;
	}
}
