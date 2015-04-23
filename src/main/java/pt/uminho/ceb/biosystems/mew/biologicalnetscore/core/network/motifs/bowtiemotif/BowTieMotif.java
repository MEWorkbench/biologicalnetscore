package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.bowtiemotif;

import java.io.Serializable;
import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotif;

public class BowTieMotif implements IMotif, Serializable{

	private static final long serialVersionUID = 1L;
	protected INode[] upperNodes;
	protected IEdge[][] upperEdges;
	protected INode centralNode;
	protected IEdge[][] lowerEdges;
	protected INode[] lowerNodes;
	
	public BowTieMotif(INode centralNode, INode[] upperNodes, IEdge[] upperEdges, INode[] lowerNodes, IEdge[] lowerEdges) {
		
		boolean[] checked = new boolean[upperNodes.length];
		
		for(int i=0;i<checked.length;i++)
		{
			checked[i] = false;
		}
		
		
		ArrayList<INode> tsec_nodes = new ArrayList<INode>();
		ArrayList<IEdge[]> tedges = new ArrayList<IEdge[]>();
		
		for(int i=0;i<checked.length;i++)
		{
			if(!checked[i])
			{
				checked[i] = true;
				INode snode = upperNodes[i];
				ArrayList<IEdge> tedgs = new ArrayList<IEdge>();
				tedgs.add(upperEdges[i]);
				
				for(int y=i+1;y<upperNodes.length;y++)
				{
					if(!checked[y])
					{
						if(snode.equals(upperNodes[y]))
						{
							checked[y] = true;
							tedgs.add(upperEdges[y]);
						}
					}
				}

				tsec_nodes.add(snode);
				IEdge[] eds = new IEdge[tedgs.size()];
				for(int y=0;y<tedgs.size();y++) eds[y] = tedgs.get(y);
				tedges.add(eds);
				
			}
		}
		
		this.upperNodes = new INode[tsec_nodes.size()];
		this.upperEdges = new IEdge[tsec_nodes.size()][];
		
		for(int i=0;i<tsec_nodes.size();i++)
		{
			this.upperNodes[i] = tsec_nodes.get(i);
			this.upperEdges[i] = tedges.get(i);
		}
		
		this.centralNode = centralNode;
		
		checked = new boolean[lowerNodes.length];
		
		for(int i=0;i<checked.length;i++)
		{
			checked[i] = false;
		}
		
		tsec_nodes = new ArrayList<INode>();
		tedges = new ArrayList<IEdge[]>();
		
		for(int i=0;i<checked.length;i++)
		{
			if(!checked[i])
			{
				checked[i] = true;
				INode snode = lowerNodes[i];
				ArrayList<IEdge> tedgs = new ArrayList<IEdge>();
				tedgs.add(lowerEdges[i]);
				
				for(int y=i+1;y<lowerNodes.length;y++)
				{
					if(!checked[y])
					{
						if(snode.equals(lowerNodes[y]))
						{
							checked[y] = true;
							tedgs.add(lowerEdges[y]);
						}
					}
				}

				tsec_nodes.add(snode);
				IEdge[] eds = new IEdge[tedgs.size()];
				for(int y=0;y<tedgs.size();y++) eds[y] = tedgs.get(y);
				tedges.add(eds);
				
			}
		}
		
		this.lowerNodes = new INode[tsec_nodes.size()];
		this.lowerEdges = new IEdge[tsec_nodes.size()][];
		
		for(int i=0;i<tsec_nodes.size();i++)
		{
			this.lowerNodes[i] = tsec_nodes.get(i);
			this.lowerEdges[i] = tedges.get(i);
		}
		
	}

	@Override
	public IEdge[] getEdges() {
//		IEdge[] res = new IEdge[upperEdges.length+lowerEdges.length];
//		
//		int z = 0;
//		
//		for(int i=0;i<upperEdges.length;i++)
//		{
//			res[z] = upperEdges[i];
//			z++;
//		}
//		
//		for(int i=0;i<lowerEdges.length;i++)
//		{
//			res[z] = lowerEdges[i];
//			z++;
//		}
//		
//		return res;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object>[] getMotifData() {
		
		ArrayList<Object>[] res = new ArrayList[upperNodes.length+1+lowerNodes.length];
		
		int z = 0;
		
		for(int i=0;i<upperNodes.length;i++)
		{
			ArrayList<Object> ql1 = new ArrayList<Object>();
			ql1.add("Upper Node");
			ql1.add(upperNodes[i].getType());
			ql1.add(upperNodes[i].getDb_id());
			ql1.add(upperNodes[i].toString());
			ql1.add(auxC(upperEdges[i]));
			res[z] = ql1;
			z++;
		}
		
		ArrayList<Object> ql2 = new ArrayList<Object>();
		ql2.add("Central Node");
		ql2.add(centralNode.getType());
		ql2.add(centralNode.getDb_id());
		ql2.add(centralNode.toString());
		ql2.add("--");
		res[z] = ql2;
		z++;
		
		for(int i=0;i<lowerNodes.length;i++)
		{
			ArrayList<Object> ql3 = new ArrayList<Object>();
			ql3.add("Lower Node");
			ql3.add(lowerNodes[i].getType());
			ql3.add(lowerNodes[i].getDb_id());
			ql3.add(lowerNodes[i].toString());
			ql3.add(auxC(lowerEdges[i]));
			res[z] = ql3;
			z++;
		}
		
		return res;
	}

	@Override
	public String getName() {
		return centralNode.getType()+":"+centralNode.getDb_id()+":"+centralNode.toString();
	}

	@Override
	public INode[] getNodes() {
		INode[] res = new INode[upperNodes.length+lowerNodes.length+1];
		
		res[0] = this.centralNode;
		
		int z = 1;
		
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

	public IEdge[][] getUpperEdges() {
		return upperEdges;
	}

	public INode getCentralNode() {
		return centralNode;
	}

	public IEdge[][] getLowerEdges() {
		return lowerEdges;
	}

	public INode[] getLowerNodes() {
		return lowerNodes;
	}
	
	public String toString() {
		String res = "";
		
		for(int i=0;i<upperNodes.length;i++)
		{
			res += "Upper Node\t"+upperNodes[i].getType()+"\t"+upperNodes[i].getDb_id()+"\t"+upperNodes[i].toString()+"\t"+auxC(upperEdges[i])+"\n";
		}
		
		res += "Central Node\t"+centralNode.getType()+"\t"+centralNode.getDb_id()+"\t"+centralNode.toString()+"\t--\n";
		
		for(int i=0;i<lowerNodes.length;i++)
		{
			res += "Lower Node\t"+lowerNodes[i].getType()+"\t"+lowerNodes[i].getDb_id()+"\t"+lowerNodes[i].toString()+"\t"+auxC(lowerEdges[i])+"\n";
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
