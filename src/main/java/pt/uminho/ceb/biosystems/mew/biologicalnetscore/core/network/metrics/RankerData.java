package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

/**
 * This class stores the data obtained by several ranker metrics (using classes 
 * that extend IRanker most likely).
 * 
 * @author Jose Pedro
 *
 */

public class RankerData implements Serializable{

	private static final long serialVersionUID = 1L;
	protected INode[] nodes;
	protected ArrayList<HashMap<INode,String>> data;
	protected ArrayList<String> dataNames;
	
	public RankerData(INode[] nodes)
	{
		this.nodes = nodes;
		this.data = new ArrayList<HashMap<INode,String>>();
		this.dataNames = new ArrayList<String>();
	}
	
	public void addData(HashMap<INode,String> dak, String dataName)
	{
		data.add(dak);
		dataNames.add(dataName);
	}
	
	public int numberOfRankers()
	{
		return dataNames.size();
	}
	
	public QRTable getData()
	{
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		
		for(int i=0;i<dataNames.size();i++)
		{
			columnsNames.add(dataNames.get(i));
		}
		
		QRTable qrt = new QRTable(columnsNames, "Rankers");
		

		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			
			ArrayList<Object> ql = new ArrayList<Object>();
			ql.add(n.getType());
			ql.add(n.toString());
			ql.add(n.getDb_id());

			for(int z=0;z<data.size();z++)
			{
				ql.add(data.get(z).get(n));
			}
			
        	qrt.addLine(ql);
		}
		
		return qrt;
	}
}
