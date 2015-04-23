package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.simmotif;

import java.io.Serializable;
import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotif;

public class SIMMotif implements IMotif, Serializable{

	private static final long serialVersionUID = 1L;
	protected INode central_node; 
	protected INode[] sec_nodes; //the first is the core
	protected IEdge[][] edges;
	
	public SIMMotif(INode[] nodes, IEdge[] edges) {
		
		this.central_node = nodes[0];
		
		boolean[] checked = new boolean[nodes.length-1];
		
		for(int i=0;i<checked.length;i++)
		{
			checked[i] = false;
		}
		
		ArrayList<INode> tsec_nodes = new ArrayList<INode>();
		ArrayList<IEdge[]> tedges = new ArrayList<IEdge[]>();
		
		for(int i=1;i<nodes.length;i++)
		{
			if(!checked[i-1])
			{
				checked[i-1] = true;
				INode snode = nodes[i];
				ArrayList<IEdge> tedgs = new ArrayList<IEdge>();
				tedgs.add(edges[i-1]);
				
				for(int y=i+1;y<nodes.length;y++)
				{
					if(!checked[y-1])
					{
						if(snode.equals(nodes[y]))
						{
							checked[y-1] = true;
							tedgs.add(edges[y-1]);
						}
					}
				}
				
				tsec_nodes.add(snode);
				IEdge[] eds = new IEdge[tedgs.size()];
				for(int y=0;y<tedgs.size();y++) eds[y] = tedgs.get(y);
				tedges.add(eds);
			}
		}
		
		this.sec_nodes = new INode[tsec_nodes.size()];
		this.edges = new IEdge[tsec_nodes.size()][];
		
		for(int i=0;i<tsec_nodes.size();i++)
		{
			this.sec_nodes[i] = tsec_nodes.get(i);
			this.edges[i] = tedges.get(i);
		}
		
//		this.nodes = nodes;
//		this.edges = edges;
		
		
		
	}

	@Override
	public IEdge[] getEdges() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object>[] getMotifData() {
		
		ArrayList<Object>[] res = new ArrayList[sec_nodes.length];
		
		for(int i=0;i<sec_nodes.length;i++)
		{
			ArrayList<Object> ql = new ArrayList<Object>();
			ql.add(sec_nodes[i].getType());
			ql.add(sec_nodes[i].getDb_id());
			ql.add(sec_nodes[i].toString());
			ql.add(auxC(edges[i]));
			res[i] = ql;
			
		}
		
		return res;
	}

	@Override
	public String getName() {
		return central_node.getType()+":"+central_node.toString();
	}

	@Override
	public INode[] getNodes() {
		INode[] res = new INode[sec_nodes.length+1];
		
		res[0] = central_node;
		
		for(int i=0;i<sec_nodes.length;i++) res[i+1] = sec_nodes[i];
		
		return res;
	}
	
	public String toString() {
		
		String res = "Core Node\t"+central_node.getType()+"\t"+central_node.getDb_id()+"\t"+central_node.toString()+"\n";
		
		for(int i=0;i<sec_nodes.length;i++)
		{
			res += "Secondary Node\t"+sec_nodes[i].getType()+"\t"+sec_nodes[i].getDb_id()+"\t"+sec_nodes[i].toString()+"\n";
		}
		
		return res;
	}

	protected String auxC(IEdge[] edges) {
		
		String res = "";
		
		ArrayList<String> ra = new ArrayList<String>();
		for(int i=0;i<edges.length;i++)
		{
			if(!ra.contains(edges[i].isType()))
			{
				ra.add(edges[i].isType());
				
				if(ra.size()==1) res = edges[i].isType();
				else res += ", "+edges[i].isType();
			}
		}
		
		return res;
	}
	
}
