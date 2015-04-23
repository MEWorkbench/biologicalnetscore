package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.dormotif;

import java.io.Serializable;
import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotif;

public class DORMotif implements IMotif, Serializable{

	private static final long serialVersionUID = 1L;
	protected INode[] upperNodes;
	protected IEdge[] edges;
	protected INode[] lowerNodes;
	protected String id;
	
	public DORMotif(String id, INode[] upperNodes, IEdge[] edges, INode[] lowerNodes) {
		this.upperNodes = upperNodes;
		this.edges = edges;
		this.lowerNodes = lowerNodes;
		this.id = id;
	}

	public IEdge[] getEdges() {
		return this.edges;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object>[] getMotifData() {
		
		ArrayList<Object>[] res = new ArrayList[upperNodes.length+lowerNodes.length];
		
		int z = 0;
		
		for(int i=0;i<upperNodes.length;i++)
		{
			ArrayList<Object> ql1 = new ArrayList<Object>();
			ql1.add("Upper Node");
			ql1.add(upperNodes[i].getType());
			ql1.add(upperNodes[i].getDb_id());
			ql1.add(upperNodes[i].toString());
			res[z] = ql1;
			z++;
		}
		
		for(int i=0;i<lowerNodes.length;i++)
		{
			ArrayList<Object> ql3 = new ArrayList<Object>();
			ql3.add("Lower Node");
			ql3.add(lowerNodes[i].getType());
			ql3.add(lowerNodes[i].getDb_id());
			ql3.add(lowerNodes[i].toString());
			res[z] = ql3;
			z++;
		}
		
		return res;
	}

	@Override
	public String getName() {
		return this.id;
	}

	@Override
	public INode[] getNodes() {
		INode[] res = new INode[upperNodes.length+lowerNodes.length];
		
		int z = 0;
		
		for(int i=0;i<upperNodes.length;i++)
		{
			res[z] = upperNodes[i];
			z++;
		}
		
		for(int i=0;i<lowerNodes.length;i++)
		{
			res[z] = lowerNodes[i];
			z++;
		}
		
		return res;
	}

	public INode[] getUpperNodes() {
		return upperNodes;
	}

	public INode[] getLowerNodes() {
		return lowerNodes;
	}

	public String getId() {
		return id;
	}
	
	public String toString() {
		String res = "";
		
		for(int i=0;i<upperNodes.length;i++)
		{
			res += "Upper Node\t"+upperNodes[i].getType()+"\t"+upperNodes[i].getDb_id()+"\t"+upperNodes[i].toString()+"\n";
		}
		
		for(int i=0;i<lowerNodes.length;i++)
		{
			res += "Lower Node\t"+lowerNodes[i].getType()+"\t"+lowerNodes[i].getDb_id()+"\t"+lowerNodes[i].toString()+"\n";
		}
		
		return res;
	}
	
}
