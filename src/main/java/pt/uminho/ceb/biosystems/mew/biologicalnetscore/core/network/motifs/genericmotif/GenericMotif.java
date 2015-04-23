package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.genericmotif;

import java.io.Serializable;
import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotif;

public class GenericMotif implements IMotif, Serializable{

	private static final long serialVersionUID = 1L;

	protected String name;
	protected String[] nName;
	protected IEdge[] edges;
	protected INode[] nodes;
	
	public GenericMotif(String name, IEdge[] edges, INode[] nodes) {
		this.name = name;
		this.nName = null;
		this.edges = edges;
		this.nodes = nodes;
	}
	
	public GenericMotif(String name, String[] nName, IEdge[] edges, INode[] nodes) {
		this.name = name;
		this.nName = nName;
		this.edges = edges;
		this.nodes = nodes;
	}

	public IEdge[] getEdges() {
		return edges;
	}

	@Override
	public ArrayList<Object>[] getMotifData() {
		
		ArrayList<Object>[] res = new ArrayList[nodes.length];
		
		for(int i=0;i<nodes.length;i++)
		{
			ArrayList<Object> ql = new ArrayList<Object>();
			if(this.nName== null) ql.add(""+(1+i));
			else ql.add(this.nName[i]);
			ql.add(nodes[i].getType());
			ql.add(nodes[i].getDb_id());
			ql.add(nodes[i].toString());
			res[i] = ql;
		}
		
		return res;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public INode[] getNodes() {
		return nodes;
	}
	
	public String[] getNNodes() {
		return nName;
	}
}
