package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;
import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class PopUpDegreeData implements Serializable, PopUpOperation{
	
	private static final long serialVersionUID = 1L;
	private IEdge[] edges;
	private boolean out;
	private INetwork net;
	
	public PopUpDegreeData(IEdge[] edges, boolean indegree, INetwork net)
	{
		this.edges = edges;
		this.out = !indegree;
		this.net = net;
	}
	
	public QRTable[] getPopUpData(INode node) {
		// TODO Auto-generated method stub
		
		QRTable[] res = new QRTable[1];
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		if(this.out)
		{
			columnsNames.add("outDegree");
			columnsNames.add("Id");
		
			res[0] = new QRTable(columnsNames, "outDegrees"); 
		
			for(int i=0;i<this.edges.length;i++)
			{
				IEdge nde = this.edges[i];
				
				INode[] ns = this.net.getConectedNodes(nde);
				
				if(ns[0].getDb_id().equals(node.getDb_id()) && 
					ns[0].getType().equals(node.getType()))
				{
					INode to = ns[1];
					ArrayList<Object> line = new ArrayList<Object>();
					line.add(to.toString());
					line.add(to.getDb_id());
					res[0].addLine(line);
				}
				
			}
		}
		else
		{
			columnsNames.add("inDegree");
			columnsNames.add("Id");
			
			res[0] = new QRTable(columnsNames, "inDegrees"); 
		
			for(int i=0;i<this.edges.length;i++)
			{
				
				IEdge nde = this.edges[i];
				
				INode[] ns = this.net.getConectedNodes(nde);
				
				if(ns[1].getDb_id().equals(node.getDb_id()) && 
					ns[1].getType().equals(node.getType()))
				{
					INode to = ns[0];
					ArrayList<Object> line = new ArrayList<Object>();
					line.add(to.toString());
					line.add(to.getDb_id());
					res[0].addLine(line);
				}
				
			}
		}
		
		return res;
	}

}
