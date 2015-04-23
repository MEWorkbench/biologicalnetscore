package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.bowtiemotif;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.SortabelObject3;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotifs;

public class BowTieMotifs implements IMotifs, Serializable {

	private static final long serialVersionUID = 1L;
	protected String name;
	protected BowTieMotif[] motifs;
	protected INode[] nodes;
	protected int[] occurrences;
	protected int[] upperOccurrences;
	protected int[] centralOccurrences;
	protected int[] lowerOccurrences;
	
	public BowTieMotifs(BowTieMotif[] motifs, INode[] nodes, String name) {
		this.name = name;
		
		SortabelObject3[] sos = new SortabelObject3[motifs.length];
		
		for(int i=0;i<sos.length;i++)
		{
			sos[i] = new SortabelObject3(motifs[i].getName(), motifs[i]);
		}
		
		Arrays.sort(sos);

		this.motifs = new BowTieMotif[sos.length];
		
		for(int i=0;i<sos.length;i++)
		{
			this.motifs[i] = (BowTieMotif)sos[i].getNode();
		}
		
		this.nodes = nodes;
		this.occurrences = new int[nodes.length];
		this.upperOccurrences = new int[nodes.length];
		this.centralOccurrences = new int[nodes.length];
		this.lowerOccurrences = new int[nodes.length];

		for(int i=0;i<nodes.length;i++)
		{
			this.occurrences[i] = 0;
			this.upperOccurrences[i] = 0;
			this.centralOccurrences[i] = 0;
			this.lowerOccurrences[i] = 0;
		}
		
		for(int i=0;i<this.motifs.length;i++)
		{
			INode cn = this.motifs[i].getCentralNode();
			
			int c = getPost(cn, nodes);
			
			this.occurrences[c]++;
			this.centralOccurrences[c]++;
			
			INode[] ns = this.motifs[i].getUpperNodes();
			
			for(int a=0;a<ns.length;a++)
			{
				int z = getPost(ns[a], nodes);
				this.upperOccurrences[z]++;
				occurrences[z]++;
			}
			
			ns = this.motifs[i].getLowerNodes();
			
			for(int a=0;a<ns.length;a++)
			{
				int z = getPost(ns[a], nodes);
				this.lowerOccurrences[z]++;
				occurrences[z]++;
			}
		}
	}
	
	protected int getPost(INode n, INode[] ns)
	{
		int z = -1;
		
		for(int i=0;z==-1 && i<ns.length;i++)
		{
			if(ns[i].equals(n)) z = i;
		}
		
		return z;
	}
	
	@Override
	public QRTable[] getData() {
		
		QRTable[] qrts = new QRTable[this.motifs.length];
		
		for(int i=0;i<this.motifs.length;i++)
		{
			ArrayList<String> columnsNames = new ArrayList<String>();
			
			columnsNames.add("Motif Roll");
			columnsNames.add("Type");
			columnsNames.add("Id");
			columnsNames.add("Name");
			columnsNames.add("Connected by");
			
			QRTable qrt = new QRTable(columnsNames, "");
			
			ArrayList<Object>[] ql = this.motifs[i].getMotifData();
			
			for(int z=0;z<ql.length;z++)
			{
				qrt.addLine(ql[z]);
			}
			
			qrts[i] = qrt;
		}
		
		return qrts;
	}

	@Override
	public BowTieMotif[] getMotifs() {
		return motifs;
	}

	@Override
	public INode[] getNodes() {
		return nodes;
	}

	@Override
	public int[] getOccurrences() {
		return occurrences;
	}

	@Override
	public int[][] getStats() {
		return new int[][]{this.centralOccurrences, this.upperOccurrences, this.lowerOccurrences};
	}

	@Override
	public String[] getStatsNames() {
		return new String[]{"Central","Upper","Lower"};
	}
	
	@Override
	public QRTable getStatsData() {
		
		ArrayList<String> columnsNames = new ArrayList<String>();

		columnsNames.add("Type");
		columnsNames.add("Id");		
		columnsNames.add("Name");
		columnsNames.add("Central");
		columnsNames.add("Upper");
		columnsNames.add("Lower");

		QRTable qrt = new QRTable(columnsNames, "");

		for(int i=0;i<this.nodes.length;i++)
		{
			if(this.occurrences[i]>0)
			{
				ArrayList<Object> ql = new ArrayList<Object>();

				ql.add(""+this.nodes[i].getType());
				ql.add(""+this.nodes[i].getDb_id());
				ql.add(""+this.nodes[i].toString());
				ql.add(""+this.centralOccurrences[i]);
				ql.add(""+this.upperOccurrences[i]);
				ql.add(""+this.lowerOccurrences[i]);

				qrt.addLine(ql);
			}
		}

		return qrt;
	}

	@Override
	public String getName() {
		return name;
	}


}
