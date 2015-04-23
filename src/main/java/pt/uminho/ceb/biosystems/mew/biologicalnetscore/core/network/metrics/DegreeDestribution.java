package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.SortabelObject;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

/**
 * Objects of this class calculate and store the degree distribution of the network.
 * 
 * @author Jose Pedro
 *
 */

public class DegreeDestribution implements Serializable, StatData {
	
	private static final long serialVersionUID = 1L;
	private INetwork net;
	private String giveDegree;
	private int[] inDegree;
	private int[] inDegreeNodes;
	private int[] outDegree;
	private int[] outDegreeNodes;
	
	public DegreeDestribution(INetwork net)
	{
		this.net = net;
		
		this.giveDegree = "inDegree";
		
		ArrayList<int[]> inDegreeList = new ArrayList<int[]>(); 
		ArrayList<int[]> outDegreeList = new ArrayList<int[]>();
		
		INode[] nodes = this.net.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			int in = this.net.inDegree(nodes[i]);
			int out = this.net.outDegree(nodes[i]);
			
			boolean stop = false;
			
			for(int x=0;x<inDegreeList.size() && !stop;x++)
			{
				int[] arr = inDegreeList.get(x);
				
				if(arr[0]==in) 
				{
					arr[1]++;
					stop = true;
				}
			}
			
			if(!stop) inDegreeList.add(new int[]{in,1});
			
			stop = false;
			
			for(int x=0;x<outDegreeList.size() && !stop;x++)
			{
				int[] arr = outDegreeList.get(x);
				
				if(arr[0]==out) 
				{
					arr[1]++;
					stop = true;
				}
			}
			
			if(!stop) outDegreeList.add(new int[]{out,1});
			
		}
		
		SortabelObject[] sos1 = new SortabelObject[inDegreeList.size()];
		SortabelObject[] sos2 = new SortabelObject[outDegreeList.size()];
		
		for(int i=0;i<inDegreeList.size();i++)
		{
			sos1[i] = new SortabelObject(inDegreeList.get(i)[0], inDegreeList.get(i));
		}
		
		for(int i=0;i<outDegreeList.size();i++)
		{
			sos2[i] = new SortabelObject(outDegreeList.get(i)[0], outDegreeList.get(i));
		}
		
		Arrays.sort(sos1);
		Arrays.sort(sos2);
		
		inDegree = new int[sos1.length];
		inDegreeNodes = new int[sos1.length];
		outDegree = new int[sos2.length];
		outDegreeNodes = new int[sos2.length];
		
		for(int i=0;i<sos1.length;i++)
		{
			inDegree[i] = ((int[])sos1[i].getNode())[0];
			inDegreeNodes[i] = ((int[])sos1[i].getNode())[1];
		}
		
		for(int i=0;i<sos2.length;i++)
		{
			outDegree[i] = ((int[])sos2[i].getNode())[0];
			outDegreeNodes[i] = ((int[])sos2[i].getNode())[1];
		}
	}
	
	public DegreeDestribution(INetwork net, String[] types)
	{
		this.net = net;
		
		this.giveDegree = "inDegree";
		
		ArrayList<int[]> inDegreeList = new ArrayList<int[]>(); 
		ArrayList<int[]> outDegreeList = new ArrayList<int[]>();
		
		INode[] nodes = this.net.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			int in = this.net.inDegree(nodes[i]);
			int out = this.net.outDegree(nodes[i]);
			
			boolean stop = false;
			
			for(int x=0;x<inDegreeList.size() && !stop;x++)
			{
				int[] arr = inDegreeList.get(x);
				
				if(arr[0]==in) 
				{
					arr[1]++;
					stop = true;
				}
			}
			
			if(!stop) inDegreeList.add(new int[]{in,1});
			
			stop = false;
			
			for(int x=0;x<outDegreeList.size() && !stop;x++)
			{
				int[] arr = outDegreeList.get(x);
				
				if(arr[0]==out) 
				{
					arr[1]++;
					stop = true;
				}
			}
			
			if(!stop) outDegreeList.add(new int[]{out,1});
			
		}
		
		SortabelObject[] sos1 = new SortabelObject[inDegreeList.size()];
		SortabelObject[] sos2 = new SortabelObject[outDegreeList.size()];
		
		for(int i=0;i<inDegreeList.size();i++)
		{
			sos1[i] = new SortabelObject(inDegreeList.get(i)[0], inDegreeList.get(i));
		}
		
		for(int i=0;i<outDegreeList.size();i++)
		{
			sos2[i] = new SortabelObject(outDegreeList.get(i)[0], outDegreeList.get(i));
		}
		
		Arrays.sort(sos1);
		Arrays.sort(sos2);
		
		inDegree = new int[sos1.length];
		inDegreeNodes = new int[sos1.length];
		outDegree = new int[sos2.length];
		outDegreeNodes = new int[sos2.length];
		
		for(int i=0;i<sos1.length;i++)
		{
			inDegree[i] = ((int[])sos1[i].getNode())[0];
			inDegreeNodes[i] = ((int[])sos1[i].getNode())[1];
		}
		
		for(int i=0;i<sos2.length;i++)
		{
			outDegree[i] = ((int[])sos2[i].getNode())[0];
			outDegreeNodes[i] = ((int[])sos2[i].getNode())[1];
		}
	}

	/**
	 * Returns the indegree and outdegrees of all the nodes of a network as a 
	 * QRReacTable object, this method also calculates the average indegree and 
	 * outdegrees of the network (because of efficacy issues).
	 */
	@Override
	public QRTable getData() {
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		if(this.giveDegree.equals("inDegree")) columnsNames.add("InDegree");
		else if(this.giveDegree.equals("outDegree")) columnsNames.add("OutDegree");
		columnsNames.add("Number of nodes");
		
		QRTable qrt = new QRTable(columnsNames, "Regulations", new int[]{2,2});
		
		if(this.giveDegree.equals("inDegree"))
		{
			for(int i=0;i<inDegree.length;i++)
			{
				ArrayList<Object> ql = new ArrayList<Object>();
				ql.add(new Integer(inDegree[i]));
				ql.add(new Integer(inDegreeNodes[i]));
				qrt.addLine(ql);
				
			}
		}
		else
		{
			for(int i=0;i<outDegree.length;i++)
			{
				ArrayList<Object> ql = new ArrayList<Object>();
				ql.add(new Integer(outDegree[i]));
				ql.add(new Integer(outDegreeNodes[i]));
				qrt.addLine(ql);
				
			}
		}
		
		return qrt;
	}
	
	
	@Override
	public String getName() {
		return "Degree data";
	}

	public void setGiveDegree(String giveDegree) {
		this.giveDegree = giveDegree;
	}
}
