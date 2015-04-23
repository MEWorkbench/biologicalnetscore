package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.genericmotif;

import java.io.Serializable;
import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotifs;

public class GenericMotifs implements IMotifs, Serializable{

	private static final long serialVersionUID = 1L;

	protected String name;
	
	protected GenericMotif[] motifs;
	
	protected INode[] nodes;
	protected int[][] occurrences;
	protected int[] totaloccurrences;
	
	protected int size;
	
	public GenericMotifs(String name, GenericMotif[] motifs) {
		super();
		this.name = name;
		this.motifs = motifs;
	}
	
	public GenericMotifs(String name) {
		super();
		this.name = name;
		this.motifs = new GenericMotif[0];
	}
	
	public void obtainOccurenceData(int size)
	{
		this.size = size;
		
		ArrayList<INode> temp = new ArrayList<INode>();
		
		for(int a=0;a<this.motifs.length;a++)
		{
			for(int b=0;b<this.motifs[a].getNodes().length;b++)
			{
				if(!temp.contains(this.motifs[a].getNodes()[b]))
					temp.add(this.motifs[a].getNodes()[b]);
			}
		}
		
		this.nodes = new INode[temp.size()];
		this.occurrences = new int[temp.size()][size];
		this.totaloccurrences = new int[temp.size()];
		
		for(int a=0;a<temp.size();a++)
		{
			this.nodes[a] = temp.get(a);
			this.totaloccurrences[a] = 0;
			for(int b=0;b<size;b++)
			{
				this.occurrences[a][b] = 0;
			}
		}
		
		for(int a=0;a<this.motifs.length;a++)
		{
			for(int b=0;b<this.motifs[a].getNodes().length;b++)
			{
				int p = temp.indexOf(this.motifs[a].getNodes()[b]);
				this.totaloccurrences[p]++;
				this.occurrences[a][b]++;
			}
		}
	}
	
	public void addData(GenericMotif[] motifs)
	{
		GenericMotif[] newmotifs = new GenericMotif[motifs.length+this.motifs.length];
		
		int za = 0;
		
		for(int i=0;i<this.motifs.length;i++)
		{
			newmotifs[za] = this.motifs[i];
			za++;
		}
		
		for(int i=0;i<motifs.length;i++)
		{
			newmotifs[za] = motifs[i];
			za++;
		}
		
		this.motifs = newmotifs;
	}
	
	public void addData(GenericMotif motifs)
	{
		this.addData(new GenericMotif[]{motifs});
	}

	@Override
	public QRTable[] getData() {
		QRTable[] qrts = new QRTable[this.motifs.length];
		
		for(int i=0;i<this.motifs.length;i++)
		{
			ArrayList<String> columnsNames = new ArrayList<String>();
			
			columnsNames.add("Motif Roll");
			columnsNames.add("Type");
			columnsNames.add("Vertex Id");
			columnsNames.add("Vertex name");
			
			QRTable qrt = new QRTable(columnsNames, "");
			
			ArrayList<Object>[] ql = this.motifs[i].getMotifData();
			
			for(int a=0;a<ql.length;a++)
			{
				qrt.addLine(ql[a]);
			}
    		
			qrts[i] = qrt;
		}
		
		return qrts;
	}

	@Override
	public GenericMotif[] getMotifs() {
		return this.motifs;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public INode[] getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getOccurrences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[][] getStats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QRTable getStatsData() {
		
		ArrayList<String> columnsNames = new ArrayList<String>();

		columnsNames.add("Type");
		columnsNames.add("Id");		
		columnsNames.add("Name");
		columnsNames.add("Total");
		
		String[] nName = null;
		
		if(this.motifs.length>0) nName= this.motifs[0].getNNodes();
		
		if(nName==null)
		{
			for(int i=0;i<this.size;i++)
				columnsNames.add(""+(i+1));
		}
		else
		{
			for(int i=0;i<this.size;i++)
				columnsNames.add(nName[i]);
		}
		
		QRTable qrt = new QRTable(columnsNames, "");

		for(int i=0;i<this.nodes.length;i++)
		{
			ArrayList<Object> ql = new ArrayList<Object>();

			ql.add(""+this.nodes[i].getType());
			ql.add(""+this.nodes[i].getDb_id());
			ql.add(""+this.nodes[i].toString());
			ql.add(""+this.totaloccurrences[i]);
			for(int a=0;a<this.size;a++)
			{
				ql.add(""+this.occurrences[i][a]);
				
			}
			
			qrt.addLine(ql);
		}

		return qrt;
	}

	@Override
	public String[] getStatsNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
