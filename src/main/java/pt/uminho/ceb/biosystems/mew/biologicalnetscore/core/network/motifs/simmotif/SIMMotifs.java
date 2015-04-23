package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.simmotif;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.SortabelObject3;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotifs;

public class SIMMotifs implements IMotifs, Serializable {

	private static final long serialVersionUID = 1L;
	protected String name;
	protected SIMMotif[] simms;
	protected INode[] nodes;
	protected int[] occurrences;
	protected int[] coreOccurrences;
	protected int[] regOccurrences;
	
	public SIMMotifs(SIMMotif[] simms, INode[] nodes, String name) {
		this.name = name;
		
		SortabelObject3[] sos = new SortabelObject3[simms.length];
		
		for(int i=0;i<sos.length;i++)
		{
			sos[i] = new SortabelObject3(simms[i].getName(), simms[i]);
		}
		
		Arrays.sort(sos);

		this.simms = new SIMMotif[sos.length];
		
		for(int i=0;i<sos.length;i++)
		{
			this.simms[i] = (SIMMotif)sos[i].getNode();
		}
		
		this.nodes = nodes;
		this.occurrences = new int[nodes.length];
		this.coreOccurrences = new int[nodes.length];
		this.regOccurrences = new int[nodes.length];
		
		for(int i=0;i<nodes.length;i++)
		{
			this.occurrences[i] = 0;
			this.coreOccurrences[i] = 0;
			this.regOccurrences[i] = 0;
		}
		
		for(int i=0;i<this.simms.length;i++)
		{
			INode[] nos = this.simms[i].getNodes();
			
			for(int a=0;a<nos.length;a++)
			{
				boolean stop = false;
				
				for(int b=0;b<this.nodes.length && !stop;b++)
				{
					if(this.nodes[b].equals(nos[a]))
					{
						this.occurrences[b]++;
						if(a==0) this.coreOccurrences[b]++;
						else this.regOccurrences[b]++;
					}
				}
			}
		}
	}
	
	@Override
	public QRTable[] getData() {
		
		QRTable[] qrts = new QRTable[this.simms.length];

		for(int i=0;i<this.simms.length;i++)
		{
			ArrayList<String> columnsNames = new ArrayList<String>();

			columnsNames.add("Vertex Type");
			columnsNames.add("Vertex Id");
			columnsNames.add("Vertex Name");
			columnsNames.add("Connected by");

			QRTable qrt = new QRTable(columnsNames, "");

			ArrayList<Object>[] ql = this.simms[i].getMotifData();

			for(int a=0;a<ql.length;a++)
			{
				qrt.addLine(ql[a]);
			}
			
			qrts[i] = qrt;
		}

		return qrts;
	}

	@Override
	public SIMMotif[] getMotifs() {
		return simms;
	}

	@Override
	public INode[] getNodes() {
		return this.nodes;
	}

	@Override
	public int[] getOccurrences() {
		return this.occurrences;
	}

	@Override
	public int[][] getStats() {
		return new int[][]{this.coreOccurrences, this.regOccurrences};
	}

	@Override
	public String[] getStatsNames() {
		return new String[]{"Core","Regulated"};
	}
	
	@Override
	public QRTable getStatsData() {
		
		ArrayList<String> columnsNames = new ArrayList<String>();

		columnsNames.add("Type");
		columnsNames.add("Id");		
		columnsNames.add("Name");
		columnsNames.add("Core");
		columnsNames.add("Regulated");

		QRTable qrt = new QRTable(columnsNames, "");

		for(int i=0;i<this.nodes.length;i++)
		{
			if(this.occurrences[i]>0)
			{
				ArrayList<Object> ql = new ArrayList<Object>();

				ql.add(""+this.nodes[i].getType());
				ql.add(""+this.nodes[i].getDb_id());
				ql.add(""+this.nodes[i].toString());
				ql.add(""+this.coreOccurrences[i]);
				ql.add(""+this.regOccurrences[i]);

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
