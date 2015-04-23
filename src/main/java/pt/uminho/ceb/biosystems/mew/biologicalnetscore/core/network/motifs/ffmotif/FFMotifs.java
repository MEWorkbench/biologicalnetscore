package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.ffmotif;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.SortabelObject3;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotifs;

public class FFMotifs implements IMotifs, Serializable{
	
	private static final long serialVersionUID = 1L;
	protected String name;
	protected FFMotif[] ffms;
	protected INode[] nodes;
	protected int[] occurrences;
	protected int[] xOccurrences;
	protected int[] yOccurrences;
	protected int[] zOccurrences;
	
	public FFMotifs(FFMotif[] ffms, String name) {
		this.name = name;
		
		SortabelObject3[] sos = new SortabelObject3[ffms.length];
		
		for(int i=0;i<sos.length;i++)
		{
			sos[i] = new SortabelObject3(ffms[i].getName(), ffms[i]);
		}
		
		Arrays.sort(sos);

		this.ffms = new FFMotif[sos.length];
		
		for(int i=0;i<sos.length;i++)
		{
			this.ffms[i] = (FFMotif)sos[i].getNode();
		}
		
		ArrayList<INode> tempNodes = new ArrayList<INode>();
		
		for(int i=0;i<this.ffms.length;i++)
		{
			INode x = this.ffms[i].getX();
			INode y = this.ffms[i].getY();
			INode z = this.ffms[i].getZ();
			
			boolean xfound = false;
			boolean yfound = false;
			boolean zfound = false;
			
			for(int a=0;a<tempNodes.size();a++)
			{
				if(tempNodes.get(a).equals(x)) xfound = true;
				else if(tempNodes.get(a).equals(y)) yfound = true;
				else if(tempNodes.get(a).equals(z)) zfound = true;
			}
			
			if(!xfound) tempNodes.add(x);
			if(!yfound) tempNodes.add(y);
			if(!zfound) tempNodes.add(z);
		}
		
		this.nodes = new INode[tempNodes.size()];
		this.occurrences = new int[nodes.length];
		this.xOccurrences = new int[nodes.length];
		this.yOccurrences = new int[nodes.length];
		this.zOccurrences = new int[nodes.length];
		
		
		for(int i=0;i<tempNodes.size();i++)
		{
			this.nodes[i] = tempNodes.get(i);
		}
		
		for(int i=0;i<nodes.length;i++)
		{
			this.occurrences[i] = 0;
			this.xOccurrences[i] = 0;
			this.yOccurrences[i] = 0;
			this.zOccurrences[i] = 0;
		}
		
		for(int i=0;i<this.ffms.length;i++)
		{
			INode x = this.ffms[i].getX();
			INode y = this.ffms[i].getY();
			INode z = this.ffms[i].getZ();
			
//			boolean ax = false;
//			boolean ay = false;
//			boolean az = false;
//			boolean stop = false;
			
			for(int a=0;a<this.nodes.length /*&& !stop*/;a++)
			{
				if(nodes[a].equals(x))
				{
//					ax = true;
					occurrences[a]++;
					xOccurrences[a]++;
//					stop = !(ax && ay && az);
				}
				else if(nodes[a].equals(y))
				{
//					ay = true;
					occurrences[a]++;
					yOccurrences[a]++;
//					stop = !(ax && ay && az);
				}
				else if(nodes[a].equals(z))
				{
//					az = true;
					occurrences[a]++;
					zOccurrences[a]++;
//					stop = !(ax && ay && az);
				}
			}
		}
		
	}
	
	@Override
	public QRTable[] getData() {
		
		QRTable[] qrts = new QRTable[this.ffms.length];
		
		for(int i=0;i<this.ffms.length;i++)
		{
			ArrayList<String> columnsNames = new ArrayList<String>();
			
			columnsNames.add("Motif Roll");
			columnsNames.add("Type");
			columnsNames.add("Vertex Id");
			columnsNames.add("Vertex name");
			
			QRTable qrt = new QRTable(columnsNames, "");
			
			ArrayList<Object>[] ql = this.ffms[i].getMotifData();
			
    		qrt.addLine(ql[0]);
    		qrt.addLine(ql[1]);
    		qrt.addLine(ql[2]);
    		qrt.addLine(ql[3]);
    		qrt.addLine(ql[4]);
    		qrt.addLine(ql[5]);
    		
			qrts[i] = qrt;
		}
		
		return qrts;
	}

	@Override
	public FFMotif[] getMotifs() {
		return ffms;
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
		return new int[][]{this.xOccurrences, this.yOccurrences, this.zOccurrences};
	}

	@Override
	public String[] getStatsNames() {
		return new String[]{"X","Y","Z"};
	}

	@Override
	public QRTable getStatsData() {
		
		ArrayList<String> columnsNames = new ArrayList<String>();

		columnsNames.add("Type");
		columnsNames.add("Id");		
		columnsNames.add("Name");
		columnsNames.add("X");
		columnsNames.add("Y");
		columnsNames.add("Z");

		QRTable qrt = new QRTable(columnsNames, "");

		for(int i=0;i<this.nodes.length;i++)
		{
			ArrayList<Object> ql = new ArrayList<Object>();

			ql.add(""+this.nodes[i].getType());
			ql.add(""+this.nodes[i].getDb_id());
			ql.add(""+this.nodes[i].toString());
			ql.add(""+this.xOccurrences[i]);
			ql.add(""+this.yOccurrences[i]);
			ql.add(""+this.zOccurrences[i]);
				
			qrt.addLine(ql);
		}

		return qrt;
	}

	@Override
	public String getName() {
		return name;
	}

}
