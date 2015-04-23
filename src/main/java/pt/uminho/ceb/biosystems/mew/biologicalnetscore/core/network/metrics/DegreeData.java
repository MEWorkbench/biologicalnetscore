package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRReacTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.SortabelObject;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

/**
 * Objects of this class calculate and store network metrics related with the degree value.
 * 
 * @author Jose Pedro
 *
 */

public class DegreeData implements Serializable, StatData {
	
	private static final long serialVersionUID = 1L;
	protected INetwork net;
	protected String orderBy;
	protected String giveType;
	protected double averangeInDegree;
	protected double averangeOutDegree;
	
	
	public DegreeData(INetwork net)
	{
		this.orderBy = "inDegree";
		
		this.net = net;
	}

	/**
	 * Returns the indegree and outdegrees of all the nodes of a network as a 
	 * QRReacTable object, this method also calculates the average indegree and 
	 * outdegrees of the network (because of efficacy issues).
	 */
	@Override
	public QRReacTable getData() {
		
		this.averangeInDegree = 0;
		this.averangeOutDegree = 0;
		
		INode[] nodes = this.net.getNodes();
		
		int nInDegree = 0;
		int nOutDegree = 0;
		
		SortabelObject[] sos = new SortabelObject[nodes.length];
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			if(this.orderBy.equals("inDegree"))	sos[i] = new SortabelObject(this.net.inDegree(n), n);
			if(this.orderBy.equals("outDegree")) sos[i] = new SortabelObject(this.net.outDegree(n), n);
		}
		
		Arrays.sort(sos);
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add("InDegree");
		columnsNames.add("OutDegree");
		
		QRReacTable qrt = new QRReacTable(columnsNames, "Regulations", "", new int[]{0,0,0,2,2});
		
		for(int i=0;i<sos.length;i++)
		{
			INode n = (INode)sos[i].getNode();
			
			if(this.giveType==null || this.giveType.equals(n.getType()))
			{
				int in = this.net.inDegree(n);
				int out = this.net.outDegree(n);
				
				ArrayList<Object> ql = new ArrayList<Object>();
				ql.add(n.getType());
				ql.add(n.toString());
				ql.add(n.getDb_id());
				ql.add(new Integer(in));
				ql.add(new Integer(out));
        		qrt.addLine(ql, n.getDb_id(), n.getType());
    		
        		this.averangeInDegree += new Double(in).doubleValue();
        		this.averangeOutDegree += new Double(out).doubleValue();
        	
        		if(new Double(in).doubleValue()>0) nInDegree++;
        		if(new Double(out).doubleValue()>0) nOutDegree++;
			}
		}

		this.averangeInDegree = averangeInDegree/(new Double(nInDegree).doubleValue());
    	this.averangeOutDegree = averangeOutDegree/(new Double(nOutDegree).doubleValue());
		
		return qrt;
	}
	
	public QRReacTable getFullData() {
		
		this.averangeInDegree = 0;
		this.averangeOutDegree = 0;
		
		INode[] nodes = this.net.getNodes();
		
		int nInDegree = 0;
		int nOutDegree = 0;
		
		SortabelObject[] sos = new SortabelObject[nodes.length];
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			if(this.orderBy.equals("inDegree"))	sos[i] = new SortabelObject(this.net.inDegree(n), n);
			if(this.orderBy.equals("outDegree")) sos[i] = new SortabelObject(this.net.outDegree(n), n);
		}
		
		Arrays.sort(sos);
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add("Degree");
		columnsNames.add("Indegree");
		columnsNames.add("Outdegree");
		
		QRReacTable qrt = new QRReacTable(columnsNames, "Regulations", "", new int[]{0,0,0,2,2,2});
		
		for(int i=0;i<sos.length;i++)
		{
			INode n = (INode)sos[i].getNode();
			
			if(this.giveType==null || this.giveType.equals(n.getType()))
			{
				int de = this.net.degree(n);
				int in = this.net.inDegree(n);
				int out = this.net.outDegree(n);
				
				ArrayList<Object> ql = new ArrayList<Object>();
				ql.add(n.getType());
				ql.add(n.toString());
				ql.add(n.getDb_id());
				ql.add(new Integer(de));
				ql.add(new Integer(in));
				ql.add(new Integer(out));
        		qrt.addLine(ql, n.getDb_id(), n.getType());
    		
        		this.averangeInDegree += new Double(in).doubleValue();
        		this.averangeOutDegree += new Double(out).doubleValue();
        	
        		if(new Double(in).doubleValue()>0) nInDegree++;
        		if(new Double(out).doubleValue()>0) nOutDegree++;
			}
		}

		this.averangeInDegree = averangeInDegree/(new Double(nInDegree).doubleValue());
    	this.averangeOutDegree = averangeOutDegree/(new Double(nOutDegree).doubleValue());
		
		return qrt;
	}
	

	public QRReacTable getDataDir() {
		
		this.averangeInDegree = 0;
		this.averangeOutDegree = 0;
		
		INode[] nodes = this.net.getNodes();
		
		int nInDegree = 0;
		int nOutDegree = 0;
		
		SortabelObject[] sos = new SortabelObject[nodes.length];
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			if(this.orderBy.equals("inDegree"))	sos[i] = new SortabelObject(this.net.inDegree(n), n);
			if(this.orderBy.equals("outDegree")) sos[i] = new SortabelObject(this.net.outDegree(n), n);
		}
		
		Arrays.sort(sos);
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add("Degree");
		
		QRReacTable qrt = new QRReacTable(columnsNames, "Regulations", "", new int[]{0,0,0,2,2});
		
		for(int i=0;i<sos.length;i++)
		{
			INode n = (INode)sos[i].getNode();
			
			if(this.giveType==null || this.giveType.equals(n.getType()))
			{
				int in = this.net.inDegree(n);
				int out = this.net.outDegree(n);
				
				ArrayList<Object> ql = new ArrayList<Object>();
				ql.add(n.getType());
				ql.add(n.toString());
				ql.add(n.getDb_id());
				ql.add(new Integer(in));
        		qrt.addLine(ql, n.getDb_id(), n.getType());
    		
        		this.averangeInDegree += new Double(in).doubleValue();
        		this.averangeOutDegree += new Double(out).doubleValue();
        	
        		if(new Double(in).doubleValue()>0) nInDegree++;
        		if(new Double(out).doubleValue()>0) nOutDegree++;
			}
		}

		this.averangeInDegree = averangeInDegree/(new Double(nInDegree).doubleValue());
    	this.averangeOutDegree = averangeOutDegree/(new Double(nOutDegree).doubleValue());
		
		return qrt;
	}
	
	public QRReacTable getDataDir(String type) {
		
		this.averangeInDegree = 0;
		this.averangeOutDegree = 0;
		
		INode[] nodes = this.net.getNodes();
		
		int nInDegree = 0;
		int nOutDegree = 0;
		
		SortabelObject[] sos = new SortabelObject[nodes.length];
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			
			int in = 0;
			int out = 0;
			
			IEdge[] tedge = this.net.getInEdges(n);
			
			for(int v=0;v<tedge.length;v++)
			{
				IEdge edge = tedge[v];
				
				if(type.equals(edge.isType())) in++;
			}
			
			tedge = this.net.getOutEdges(n);
			
			for(int v=0;v<tedge.length;v++)
			{
				IEdge edge = tedge[v];
				
				if(type.equals(edge.isType())) out++;
			}
			
			if(this.orderBy.equals("inDegree"))	sos[i] = new SortabelObject(in, new Object[]{n, new Integer(in), new Integer(out)});
			if(this.orderBy.equals("outDegree")) sos[i] = new SortabelObject(out, new Object[]{n, new Integer(in), new Integer(out)});
		}
		
		Arrays.sort(sos);
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add("Degree");
		
		QRReacTable qrt = new QRReacTable(columnsNames, "Regulations", "", new int[]{0,0,0,2,2});
		
		for(int i=0;i<sos.length;i++)
		{
			Object[] jo = (Object[])sos[i].getNode();
			INode n = (INode)jo[0];
			int in = ((Integer)jo[1]).intValue();
			int out = ((Integer)jo[2]).intValue();
			
			if(this.giveType==null || this.giveType.equals(n.getType()))
			{
				
				ArrayList<Object> ql = new ArrayList<Object>();
				ql.add(n.getType());
				ql.add(n.toString());
				ql.add(n.getDb_id());
				ql.add(new Integer(in));
        		qrt.addLine(ql, n.getDb_id(), n.getType());
    		
        		this.averangeInDegree += new Double(in).doubleValue();
        		this.averangeOutDegree += new Double(out).doubleValue();
        	
        		if(new Double(in).doubleValue()>0) nInDegree++;
        		if(new Double(out).doubleValue()>0) nOutDegree++;
			}
		}

		this.averangeInDegree = averangeInDegree/(new Double(nInDegree).doubleValue());
    	this.averangeOutDegree = averangeOutDegree/(new Double(nOutDegree).doubleValue());
		
		return qrt;
	}
	
	public QRReacTable getData(String type) {
		
		this.averangeInDegree = 0;
		this.averangeOutDegree = 0;
		
		INode[] nodes = this.net.getNodes();
		
		int nInDegree = 0;
		int nOutDegree = 0;
		
		SortabelObject[] sos = new SortabelObject[nodes.length];
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			
			int in = 0;
			int out = 0;
			
			IEdge[] tedge = this.net.getInEdges(n);
			
			for(int v=0;v<tedge.length;v++)
			{
				IEdge edge = tedge[v];
				
				if(type.equals(edge.isType())) in++;
			}
			
			tedge = this.net.getOutEdges(n);
			
			for(int v=0;v<tedge.length;v++)
			{
				IEdge edge = tedge[v];
				
				if(type.equals(edge.isType())) out++;
			}
			
			if(this.orderBy.equals("inDegree"))	sos[i] = new SortabelObject(in, new Object[]{n, new Integer(in), new Integer(out)});
			if(this.orderBy.equals("outDegree")) sos[i] = new SortabelObject(out, new Object[]{n, new Integer(in), new Integer(out)});
		}
		
		Arrays.sort(sos);
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add("InDegree");
		columnsNames.add("OutDegree");
		
		QRReacTable qrt = new QRReacTable(columnsNames, "Regulations", "", new int[]{0,0,0,2,2});
		
		for(int i=0;i<sos.length;i++)
		{
			Object[] jo = (Object[])sos[i].getNode();
			INode n = (INode)jo[0];
			int in = ((Integer)jo[1]).intValue();
			int out = ((Integer)jo[2]).intValue();
			
			if(this.giveType==null || this.giveType.equals(n.getType()))
			{
				
				ArrayList<Object> ql = new ArrayList<Object>();
				ql.add(n.getType());
				ql.add(n.toString());
				ql.add(n.getDb_id());
				ql.add(new Integer(in));
				ql.add(new Integer(out));
        		qrt.addLine(ql, n.getDb_id(), n.getType());
    		
        		this.averangeInDegree += new Double(in).doubleValue();
        		this.averangeOutDegree += new Double(out).doubleValue();
        	
        		if(new Double(in).doubleValue()>0) nInDegree++;
        		if(new Double(out).doubleValue()>0) nOutDegree++;
			}
		}

		this.averangeInDegree = averangeInDegree/(new Double(nInDegree).doubleValue());
    	this.averangeOutDegree = averangeOutDegree/(new Double(nOutDegree).doubleValue());
		
		return qrt;
	}

	/**
	 * Returns the indegree and outdegrees of a node as array of QRTable objects 
	 * (the first contains the outdegree the second the indegree).
	 * @param id  Unique id of the node.
	 * @param type Type of the node.
	 * @return
	 */
	public QRTable[] getRowInfo(String id, String type)
	{
		QRTable[] qrts = new QRTable[2];
		
		ArrayList<String> columnsNames1 = new ArrayList<String>();
		
		columnsNames1.add("inDegrees");
		columnsNames1.add("Id");

		QRTable qrt1 = new QRTable(columnsNames1, "inDegrees");

		ArrayList<String> columnsNames2 = new ArrayList<String>();
		
		columnsNames2.add("outDegree");
		columnsNames2.add("Id");

		INode n = net.getNode(id, type);
		QRTable qrt2 = new QRTable(columnsNames2, "outDegree");
		
		qrts[0] = qrt1;
		qrts[1] = qrt2;
		
//		JungNode n = this.nodes_index.get(id+"@"+type);
		
		IEdge[] inDegreeEdges = this.net.getInEdges(n);
		IEdge[] outDegreeEdges = this.net.getOutEdges(n);
		
		for(int i=0;i<inDegreeEdges.length;i++)
		{
			INode no = this.net.getConectedNodes(inDegreeEdges[i])[0];
			
			ArrayList<Object> ql = new ArrayList<Object>();
			ql.add(no.toString());
			ql.add(no.getDb_id());
        	qrt1.addLine(ql);
		}
		
		for(int i=0;i<outDegreeEdges.length;i++)
		{
			INode no = this.net.getConectedNodes(outDegreeEdges[i])[1];
			
			ArrayList<Object> ql = new ArrayList<Object>();
			ql.add(no.toString());
			ql.add(no.getDb_id());
        	qrt2.addLine(ql);
		}
		
		return qrts;
	}
	
	public QRTable[] getRowInfoDir(String id, String type)
	{
		QRTable[] qrts = new QRTable[1];
		
		ArrayList<String> columnsNames1 = new ArrayList<String>();
		
		columnsNames1.add("Degrees");
		columnsNames1.add("Id");

		QRTable qrt1 = new QRTable(columnsNames1, "inDegrees");

		INode n = net.getNode(id, type);
		
		qrts[0] = qrt1;
		
//		JungNode n = this.nodes_index.get(id+"@"+type);
		
		IEdge[] inDegreeEdges = this.net.getInEdges(n);
		
		for(int i=0;i<inDegreeEdges.length;i++)
		{
			INode[] ends = this.net.getConectedNodes(inDegreeEdges[i]);
			INode no;
			
			if(ends[0].getDb_id().equals(id)) no = ends[1];
			else no = ends[0];
			
			ArrayList<Object> ql = new ArrayList<Object>();
			ql.add(no.toString());
			ql.add(no.getDb_id());
        	qrt1.addLine(ql);
		}
		
		return qrts;
	}
	
	/**
	 * Returns a table with the distribution of the degree value in the network
	 * 
	 * @param useInDegree If true the table will contain the distribution of the indegree 
	 * otherwise it will contain the distribution of the outdegree.
	 * @param percentage If true the distribution will be shown as a percentage.
	 * @return
	 */
	public QRTable getNDegree(boolean useInDegree, boolean percentage) {
		
		HashMap<Integer,Integer> inStats = new HashMap<Integer,Integer>();
		
		ArrayList<Integer> keyIndex = new ArrayList<Integer>();
		
		int number = 0;
		
		INode[] nodes = this.net.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];

			if(this.giveType==null || this.giveType.equals(n.getType()))
			{
				int num;
			
				if(useInDegree) num = this.net.inDegree(n);
				else num = this.net.outDegree(n);
			
				Integer key = new Integer(num);
			
				if(inStats.containsKey(key))
				{
					Integer dalek = inStats.get(key);
					inStats.put(key, new Integer(dalek.intValue()+1));
				}
				else
				{
					keyIndex.add(key);
					inStats.put(key, new Integer(1));
				}
				number++;
			}
		}
		
		SortabelObject[] sos = new SortabelObject[keyIndex.size()];
		
		
		for(int i=0;i<keyIndex.size();i++)
		{
			Integer key = keyIndex.get(i);
			
			sos[i] = new SortabelObject(key.intValue(), key);
		}
		
		Arrays.sort(sos);
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		if(useInDegree) columnsNames.add("InDegree");
		else columnsNames.add("OutDegree");
		if(!percentage) columnsNames.add("Number");
		else columnsNames.add("Percentage");

		QRTable qrt = new QRTable(columnsNames, "Regulations");
		
		for(int i=sos.length;i>0;i--)
		{
			Integer key = (Integer)sos[(i-1)].getNode();
			
			ArrayList<Object> ql = new ArrayList<Object>();
			ql.add(key.toString());
			if(!percentage) ql.add(inStats.get(key).toString());
			else
			{
				int zam = inStats.get(key).intValue();
				double dak = ((new Double(zam)).doubleValue())/((new Double(number).doubleValue()));
				ql.add(dak+"");
			}
        	qrt.addLine(ql);
		}
		
		return qrt;
	}

	public QRTable getNDegree(boolean percentage) {
		
		HashMap<Integer,Integer> inStats = new HashMap<Integer,Integer>();
		
		ArrayList<Integer> keyIndex = new ArrayList<Integer>();
		
		int number = 0;
		
		INode[] nodes = this.net.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];

			if(this.giveType==null || this.giveType.equals(n.getType()))
			{
				int num;
			
				num = this.net.degree(n);
			
				Integer key = new Integer(num);
			
				if(inStats.containsKey(key))
				{
					Integer dalek = inStats.get(key);
					inStats.put(key, new Integer(dalek.intValue()+1));
				}
				else
				{
					keyIndex.add(key);
					inStats.put(key, new Integer(1));
				}
				number++;
			}
		}
		
		SortabelObject[] sos = new SortabelObject[keyIndex.size()];
		
		
		for(int i=0;i<keyIndex.size();i++)
		{
			Integer key = keyIndex.get(i);
			
			sos[i] = new SortabelObject(key.intValue(), key);
		}
		
		Arrays.sort(sos);
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Degree");
		columnsNames.add("Number");

		QRTable qrt = new QRTable(columnsNames, "Regulations");
		
		for(int i=sos.length;i>0;i--)
		{
			Integer key = (Integer)sos[(i-1)].getNode();
			
			ArrayList<Object> ql = new ArrayList<Object>();
			ql.add(key.toString());
			if(!percentage) ql.add(inStats.get(key).toString());
			else
			{
				int zam = inStats.get(key).intValue();
				double dak = ((new Double(zam)).doubleValue())/((new Double(number).doubleValue()));
				ql.add(dak+"");
			}
        	qrt.addLine(ql);
		}
		
		return qrt;
	}
	
	@Override
	public String getName() {
		return "Degree data";
	}

	/**
	 * Sets by which value the table returned by the QRReacTable will organized (indegree or outdegree).
	 * @param orderBy Either "inDegree" or "outDegree".
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * Returns the average indegree of the network of the network 
	 * (0 if the getData method was not used yet).
	 * @return
	 */
	public double getAverangeInDegree() {
		return averangeInDegree;
	}

	/**
	 * Returns the average outdegree of the network of the network 
	 * (0 if the getData method was not used yet).
	 * @return
	 */
	public double getAverangeOutDegree() {
		return averangeOutDegree;
	}

	/**
	 * Returns the types of the nodes of the network.
	 * @return
	 */
	public String[] getTypes() {
		return this.net.getNodeTypesArray();
	}

	/**
	 * Sets the type of node that will be analyzed, if null all types of nodes will be analyzed.
	 * @param giveType
	 */
	public void setGiveType(String giveType) {
		this.giveType = giveType;
	}
	
	public String[] getEdgeTypes() {
		return this.net.getEdgeTypesArray();
	}
	
	public String[] getRDegreePlots() {
		
		String degree = "";
		String indegree = "";
		String outdegree = "";
		
		HashMap<Integer,Integer> inStatsD = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> inStatsI = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> inStatsO = new HashMap<Integer,Integer>();
		
		ArrayList<Integer> keyIndexD = new ArrayList<Integer>();
		ArrayList<Integer> keyIndexI = new ArrayList<Integer>();
		ArrayList<Integer> keyIndexO = new ArrayList<Integer>();
		
		INode[] nodes = this.net.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];

			int num;
		
			num = this.net.degree(n);
		
			Integer key = new Integer(num);
		
			if(inStatsD.containsKey(key))
			{
				Integer dalek = inStatsD.get(key);
				inStatsD.put(key, new Integer(dalek.intValue()+1));
			}
			else
			{
				keyIndexD.add(key);
				inStatsD.put(key, new Integer(1));
			}
			
			num = this.net.inDegree(n);
		
			key = new Integer(num);

			if(inStatsI.containsKey(key))
			{
				Integer dalek = inStatsI.get(key);
				inStatsI.put(key, new Integer(dalek.intValue()+1));
			}
			else
			{
				keyIndexI.add(key);
				inStatsI.put(key, new Integer(1));
			}
			
			num = this.net.outDegree(n);
		
			key = new Integer(num);

			if(inStatsO.containsKey(key))
			{
				Integer dalek = inStatsO.get(key);
				inStatsO.put(key, new Integer(dalek.intValue()+1));
			}
			else
			{
				keyIndexO.add(key);
				inStatsO.put(key, new Integer(1));
			}
			
		}
		
		SortabelObject[] sosD = new SortabelObject[keyIndexD.size()];
		SortabelObject[] sosI = new SortabelObject[keyIndexI.size()];
		SortabelObject[] sosO = new SortabelObject[keyIndexO.size()];
		
		for(int i=0;i<keyIndexD.size();i++)
		{
			Integer key = keyIndexD.get(i);
			
			sosD[i] = new SortabelObject(key.intValue(), key);
		}
		
		Arrays.sort(sosD);
		
		for(int i=0;i<keyIndexI.size();i++)
		{
			Integer key = keyIndexI.get(i);
			
			sosI[i] = new SortabelObject(key.intValue(), key);
		}
		
		Arrays.sort(sosI);
		
		for(int i=0;i<keyIndexO.size();i++)
		{
			Integer key = keyIndexO.get(i);
			
			sosO[i] = new SortabelObject(key.intValue(), key);
		}
		
		Arrays.sort(sosO);
		
		String x = "x <- c(";
		String y = "y <- c("; 
		
		for(int i=sosD.length;i>0;i--)
		{
			Integer key = (Integer)sosD[(i-1)].getNode();
			
			ArrayList<Object> ql = new ArrayList<Object>();
			
			if(i==sosD.length)
			{
				x += key.toString();
				y += inStatsD.get(key).toString();
			}
			else
			{
				x += ","+key.toString();
				y += ","+inStatsD.get(key).toString();
			}
		}
		
		x += ")\n";
		y += ")\n";
		
		degree += x+y;
		degree += "plot(x,y, xlab=\"Degree\", ylab=\"Number of nodes\", main=\"degree distribution plot\")\nabline(myline.fit)\n";
		
		x = "x <- c(";
		y = "y <- c("; 
		
		for(int i=sosI.length;i>0;i--)
		{
			Integer key = (Integer)sosI[(i-1)].getNode();
			
			ArrayList<Object> ql = new ArrayList<Object>();
			
			if(i==sosI.length)
			{
				x += key.toString();
				y += inStatsI.get(key).toString();
			}
			else
			{
				x += ","+key.toString();
				y += ","+inStatsI.get(key).toString();
			}
		}
		
		x += ")\n";
		y += ")\n";
		
		indegree += x+y;
		indegree += "plot(x,y, xlab=\"Degree\", ylab=\"Number of nodes\", main=\"degree distribution plot\")\nabline(myline.fit)\n";
		
		x = "x <- c(";
		y = "y <- c("; 
		
		for(int i=sosO.length;i>0;i--)
		{
			Integer key = (Integer)sosO[(i-1)].getNode();
			
			ArrayList<Object> ql = new ArrayList<Object>();
			
			if(i==sosO.length)
			{
				x += key.toString();
				y += inStatsO.get(key).toString();
			}
			else
			{
				x += ","+key.toString();
				y += ","+inStatsO.get(key).toString();
			}
		}
		
		x += ")\n";
		y += ")\n";
		
		outdegree += x+y;
		outdegree += "plot(x,y, xlab=\"Degree\", ylab=\"Number of nodes\", main=\"degree distribution plot\")\nabline(myline.fit)\n";
		
		return new String[]{degree,indegree,outdegree};
	}
}
