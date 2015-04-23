package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.edflmotif;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.SortabelObject3;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotifs;

public class EDFLMotifs implements IMotifs, Serializable {

	private static final long serialVersionUID = 1L;
	protected String name;
	protected EDFLMotif[] motifs;
	protected INode[] nodes;
	protected int[] occurrences;
	protected int[] startOccurrences;
	protected int[] endOccurrences;
	protected int[] pathOccurrences;
	protected int[] feedbackOccurrences;
	
	public EDFLMotifs(EDFLMotif[] motifs, INode[] nodes, String name) {
		this.name = name;
		
		SortabelObject3[] sos = new SortabelObject3[motifs.length];
		
		for(int i=0;i<sos.length;i++)
		{
			sos[i] = new SortabelObject3(motifs[i].getName(), motifs[i]);
		}
		
		Arrays.sort(sos);

		this.motifs = new EDFLMotif[sos.length];
		
		for(int i=0;i<sos.length;i++)
		{
			this.motifs[i] = (EDFLMotif)sos[i].getNode();
		}
		
		this.nodes = nodes;
		
		this.occurrences = new int[nodes.length];
		this.startOccurrences = new int[nodes.length];
		this.endOccurrences = new int[nodes.length];
		this.pathOccurrences = new int[nodes.length];
		this.feedbackOccurrences = new int[nodes.length];
		
		for(int i=0;i<nodes.length;i++)
		{
			this.occurrences[i] = 0;
			this.startOccurrences[i] = 0;
			this.endOccurrences[i] = 0;
			this.pathOccurrences[i] = 0;
			this.feedbackOccurrences[i] = 0;
		}
		
		for(int i=0;i<this.motifs.length;i++)
		{
			INode start = this.motifs[i].getStartNode();
			INode end = this.motifs[i].getEndNode();

			boolean startfound = false;
			boolean endfound = false;
			
			for(int a=0;a<this.nodes.length && (!startfound || !endfound);a++)
			{
				if(nodes[a].equals(start))
				{
					occurrences[a]++;
					startOccurrences[a]++;
					startfound = true;
				}
				else if(nodes[a].equals(end))
				{
					occurrences[a]++;
					endOccurrences[a]++;
					endfound = true;
				}
			}
			
			INode[][] paths = this.motifs[i].getPaths();
			
			for(int a=0;a<paths.length;a++)
			{
				for(int b=1;b<(paths[a].length-1);b++)
				{
					int z = getPost(paths[a][b], nodes);
					this.pathOccurrences[z]++;
					occurrences[z]++;
				}
			}
			
			paths = this.motifs[i].getFeedbackPaths();
			
			for(int a=0;a<paths.length;a++)
			{
				for(int b=1;b<(paths[a].length-1);b++)
				{
					int z = getPost(paths[a][b], nodes);
					this.feedbackOccurrences[z]++;
					occurrences[z]++;
				}
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
			
			QRTable qrt = new QRTable(columnsNames, "");
			
			ArrayList<Object>[] ql = this.motifs[i].getMotifData();

			for(int g=0;g<ql.length;g++)
				qrt.addLine(ql[g]);
			
//    		qrt.addLine(ql[0]);
//    		qrt.addLine(ql[1]);
//    		qrt.addLine(ql[2]);
//    		qrt.addLine(ql[3]);
    		
			qrts[i] = qrt;
		}
		
		return qrts;
	}

	@Override
	public EDFLMotif[] getMotifs() {
		return motifs;
	}

	@Override
	public INode[] getNodes() {
		return this.nodes;
	}

	@Override
	public int[] getOccurrences() {
		return occurrences;
	}

	@Override
	public int[][] getStats() {
		return new int[][]{this.startOccurrences, this.endOccurrences, this.pathOccurrences, this.feedbackOccurrences};
	}

	@Override
	public String[] getStatsNames() {
		return new String[]{"Start","End","Path","Feedback"};
	}
	
	@Override
	public QRTable getStatsData() {
		
		ArrayList<String> columnsNames = new ArrayList<String>();

		columnsNames.add("Type");
		columnsNames.add("Id");		
		columnsNames.add("Name");
		columnsNames.add("Start");
		columnsNames.add("End");
		columnsNames.add("Path");
		columnsNames.add("Feedback");

		QRTable qrt = new QRTable(columnsNames, "");

		for(int i=0;i<this.nodes.length;i++)
		{
			if(this.occurrences[i]>0)
			{
				ArrayList<Object> ql = new ArrayList<Object>();

				ql.add(""+this.nodes[i].getType());
				ql.add(""+this.nodes[i].getDb_id());
				ql.add(""+this.nodes[i].toString());
				ql.add(""+this.startOccurrences[i]);
				ql.add(""+this.endOccurrences[i]);
				ql.add(""+this.pathOccurrences[i]);
				ql.add(""+this.feedbackOccurrences[i]);

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
