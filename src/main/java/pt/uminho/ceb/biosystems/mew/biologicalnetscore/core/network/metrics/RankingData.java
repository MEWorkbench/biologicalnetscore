package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRDTTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

/**
 * This class stores the results obtained by a class that extends IRanker.
 * 
 * @author Jose Pedro
 *
 */

public class RankingData implements Serializable, StatData {

	private static final long serialVersionUID = 1L;
	protected String rankerName;
	protected double[] ranker;
	protected INode[] nodes;
	protected String rName;
	protected PopUpDegreeData popUp;
	protected int[] nodeIndex;
	protected String[] INodetypes;
	protected INode[] INodesOfType;
	protected double[] subRanker;
	protected String typeInSub;
	protected String typeSeted;
	protected double averange;

	public RankingData(String rankerName, IRanker ranker, INode[] nodes, String rName, PopUpDegreeData popUp, String[] INodetypes)
	{
		this.INodetypes = INodetypes;
		this.rankerName = rankerName;
		this.nodes = nodes;
		this.ranker = new double[nodes.length];
		this.INodesOfType = null;
		this.typeInSub = "";
		this.typeSeted = "All";
		
		for(int i=0;i<nodes.length;i++)
		{
			this.ranker[i] = ranker.getRankScore(this.nodes[i]);
		}
		
		this.rName = rName;
		this.popUp = popUp;
		
		this.averange = 0;
		
		for(int i=0;i<this.ranker.length;i++)
		{
			this.averange += this.ranker[i];
		}
		
		this.averange = this.averange/this.ranker.length;
	}

	public RankingData(String rankerName, INode[] nodes, String rName, PopUpDegreeData popUp, String[] INodetypes)
	{
		this.INodetypes = INodetypes;
		this.rankerName = rankerName;
		this.nodes = nodes;
		this.ranker = new double[nodes.length];
		this.INodesOfType = null;
		this.typeInSub = "";
		this.typeSeted = "All";
		
		this.rName = rName;
		this.popUp = popUp;
		
		this.averange = 0;
		
		for(int i=0;i<this.ranker.length;i++)
		{
			this.averange += this.ranker[i];
		}
		
		this.averange = this.averange/this.ranker.length;
	}
	
	public double getAverange()
	{
		return this.averange;
	}
	
//	public RankingData(String rankerName, INode[] nodes, String rName, PopUpDegreeData popUp, String[] INodetypes)
//	{
//		this.INodetypes = INodetypes;
//		this.rankerName = rankerName;
//		this.nodes = nodes;
//		
//		this.rName = rName;
//		this.popUp = popUp;
//	}
	
	public void setType(String t)
	{
		this.typeSeted = t;
		if(!t.equals("All") && !typeInSub.equals(t))
		{
			ArrayList<Integer> ints = new ArrayList<Integer>();
			
			for(int i=0;i<nodes.length;i++)
			{
				if(nodes[i].getType().equals(t))
				{
					ints.add(new Integer(i));
				}
			}
			
			this.INodesOfType = new INode[ints.size()];
			this.subRanker = new double[ints.size()];
			
			for(int a=0;a<ints.size();a++)
			{
				int i = ints.get(a).intValue();
				this.INodesOfType[a] = this.nodes[i];
				this.subRanker[a] = this.ranker[i];
			}
			this.typeInSub = t;
		}
	}
	
	
	public String getName() {
		return rankerName;
	}
	
	public QRTable getData()
	{
		QRTable qrt;
		
		if(this.typeSeted.equals("All")) qrt = getData_aux(nodes, ranker);
		else qrt = getData_aux(INodesOfType, subRanker);

		return qrt;
	}
	
	protected QRTable getData_aux(INode[] nodes, double[] ranker)
	{
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add(this.rName);
		
		QRTable qrt = new QRTable(columnsNames, "Regulations");
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			
			ArrayList<Object> ql = new ArrayList<Object>();
			ql.add(n.getType());
			ql.add(n.toString());
			ql.add(n.getDb_id());
			ql.add(""+ranker[i]);
			
			qrt.addLine(ql);
		}
		
		return qrt;
	}
	
	public QRTable getDataByName()
	{
		QRTable qrt;
		
		if(this.typeSeted.equals("All")) qrt = getDataByName_aux(nodes, ranker);
		else qrt = getDataByName_aux(INodesOfType, subRanker);

		return qrt;
	}
	
	protected QRTable getDataByName_aux(INode[] nodes, double[] ranker)
	{
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add(this.rName);
		
		QRTable qrt = new QRTable(columnsNames, "Regulations");
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			
			ArrayList<Object> ql = new ArrayList<Object>();
			ql.add(n.getType());
			ql.add(n.toString());
			ql.add(n.getDb_id());
			ql.add(""+ranker[i]);
			
			qrt.addLine(ql);
		}
		
		return qrt;
	}
	
	public void addData(RankerData rd)
	{
		HashMap<INode,String> dak = new HashMap<INode,String>();
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			
			dak.put(n, ""+this.ranker[i]);
		}
		
		rd.addData(dak, this.rName);
	}
	
	public QRTable[] getPopUp(int z)
	{
		if(this.typeSeted.equals("All")) return this.popUp.getPopUpData(this.nodes[this.nodeIndex[z]]);
		else return this.popUp.getPopUpData(this.INodesOfType[this.nodeIndex[z]]);
	}
	
	public boolean popUp()
	{
		return this.popUp != null;
	}
	
	public String[] getNodeTypes() {
		return INodetypes;
	}
	
	public INode[] getNodes() {
		return nodes;
	}
	
	public double[] getRanker() {
		return ranker;
	}

	public QRDTTable getDData() {
		
		ArrayList<String> columnsNames = new ArrayList<String>();
		
		columnsNames.add("Type");
		columnsNames.add("Name");
		columnsNames.add("Id");
		columnsNames.add(this.rName);
		
		QRDTTable qrt;
		
		if(this.rName.equals("Barycenter Scorer")) qrt = new QRDTTable(columnsNames, "Regulations", new int[]{0,0,0,0});
		else qrt = new QRDTTable(columnsNames, "Regulations", new int[]{0,0,0,1});
		
		for(int i=0;i<nodes.length;i++)
		{
			INode n = nodes[i];
			
			if(!(""+ranker[i]).equals("NaN"))
			{
				ArrayList<Object> ql = new ArrayList<Object>();
				ql.add(n.getType());
				ql.add(n.toString());
				ql.add(n.getDb_id());
				if(this.rName.equals("Barycenter Scorer")) ql.add(""+ranker[i]);
				else ql.add(new Double(ranker[i]));
				qrt.addLine(ql);
			}
		}

		
		return qrt;
	}
}
