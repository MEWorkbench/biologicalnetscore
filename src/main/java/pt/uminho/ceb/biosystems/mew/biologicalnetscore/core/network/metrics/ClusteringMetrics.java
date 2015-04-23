package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Observable;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.SortabelObject2;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

/**
 * When an object of this class is created it calculates the clustering coefficient 
 * of all the nodes the network then stores this values for future reference. 
 * This class also store the average clustering coefficient of the 
 * network but this value is calculated later (for efficiency reasons).
 * 
 * @author Jose Pedro
 *
 */

public class ClusteringMetrics extends Observable implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	protected Map cc;
	protected INode[] nodes;
	protected double averangeCM;
	protected double[][] ck;
	protected double[][] ick;
	protected double[][] ock;
	protected boolean directed;
	
	public ClusteringMetrics(INetwork net, IClusteringCoefficientsCalculator ccc)
	{
		this.directed = true;
		this.ck = ccc.getCk(net);
		this.ick = ccc.getInCk(net);
		this.ock = ccc.getOutCk(net);
		this.cc = ccc.getClusteringCoefficients(net);
		this.nodes = net.getNodes();
		this.averangeCM = 0;
	}
	
	public ClusteringMetrics(INetwork net, IClusteringCoefficientsCalculator ccc, boolean directed)
	{
		this.directed = directed;
		this.ck = ccc.getCk(net);
		if(directed)
		{
			this.ick = ccc.getInCk(net);
			this.ock = ccc.getOutCk(net);
		}
		this.cc = ccc.getClusteringCoefficients(net);
		this.nodes = net.getNodes();
		this.averangeCM = 0;
	}
	
	/**
	 * Returns the clustering coefficient off all the network nodes, also if the 
	 * average clustering coefficient was not yet calculated it is calculated by this method.
	 * 
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public QRTable getData()
	{
		boolean arg = false;
		
		if(this.averangeCM==0)
    	{
    		arg=true;
    	}
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Node");
		columnsNames.add("Id");
		columnsNames.add("Clustering coefficients");
		
		QRTable qrt = new QRTable(columnsNames, "Regulations", new int[]{0,0,0,1});

		ArrayList<SortabelObject2> lsos = new ArrayList<SortabelObject2>();
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			
			ArrayList<Object> ql = new ArrayList<Object>();
			ql.add(n.getType());
			ql.add(n.toString());
			ql.add(n.getDb_id());
			ql.add(new Double(this.cc.get(n).toString()).doubleValue());
			lsos.add(new SortabelObject2(new Double(this.cc.get(n).toString()).doubleValue(), ql));
        	
        	if(arg)
        	{
        		this.averangeCM += new Double(this.cc.get(n).toString()).doubleValue();
        	}
		}
		
		SortabelObject2[] sos = new SortabelObject2[lsos.size()];
		
		for(int i=0;i<lsos.size();i++)
		{
			sos[i] = lsos.get(i);
		}
		
		Arrays.sort(sos);
		
		for(int i=0;i<sos.length;i++)
		{
			ArrayList<Object> ql = (ArrayList<Object>)(sos[i].getNode());
    		qrt.addLine(ql);
		}
		
		if(arg)
		{
			this.averangeCM = averangeCM/(new Double(sos.length).doubleValue());
		}
		
		return qrt;
	}


	public QRTable[] getCKData()
	{
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Degree");
		columnsNames.add("C(k)");
		
		QRTable qrt2 = null;
		QRTable qrt3 = null;
		
		QRTable qrt = new QRTable(columnsNames, "C(k)", new int[]{0,0,1});
		
		for(int i=0;i<ck.length;i++)
		{
			ArrayList<Object> ql = new ArrayList<Object>();
			
			int k = new Double(ck[i][0]).intValue();
			
			ql.add(k);
			ql.add(new Double(ck[i][1]));
    		qrt.addLine(ql);

		}
		
		if(this.directed)
		{
			ArrayList<String> columnsNames2 = new ArrayList<String>();
			
			columnsNames2.add("Degree");
			columnsNames2.add("InDegree C(k)");
			
			qrt2 = new QRTable(columnsNames2, "InDegree C(k)", new int[]{0,0,1});
			
			for(int i=0;i<ick.length;i++)
			{
				ArrayList<Object> ql = new ArrayList<Object>();
				
				int k = new Double(ick[i][0]).intValue();
				
				ql.add(k);
				ql.add(new Double(ick[i][1]));
				qrt2.addLine(ql);
			}
			
			ArrayList<String> columnsNames3 = new ArrayList<String>();
			
			columnsNames3.add("Degree");
			columnsNames3.add("OutDegree C(k)");
			
			qrt3 = new QRTable(columnsNames3, "OutDegree C(k)", new int[]{0,0,1});
			
			for(int i=0;i<ock.length;i++)
			{
				ArrayList<Object> ql = new ArrayList<Object>();
				
				int k = new Double(ock[i][0]).intValue();
				
				ql.add(k);
				ql.add(new Double(ock[i][1]));
				qrt3.addLine(ql);
			}
		}
		
		QRTable[] res;
		
		if(this.directed) res = new QRTable[3];
		else res = new QRTable[1];
		
		if(this.directed)
		{
			res[0] = qrt;
			res[1] = qrt2;
			res[2] = qrt3;
			
		}
		else res[0] = qrt;
		
		return res;
	}
	
	public INode[] getNodes() {
		return nodes;
	}

	/**
	 * Returns the average clustering coefficient of the network.
	 * @return
	 */
	public double getAverangeCM() {
		return averangeCM;
	}

	public double[][] getCk() {
		return ck;
	}

	public double[][] getIck() {
		return ick;
	}

	public double[][] getOck() {
		return ock;
	}

	public Map getCc() {
		return cc;
	}
}
