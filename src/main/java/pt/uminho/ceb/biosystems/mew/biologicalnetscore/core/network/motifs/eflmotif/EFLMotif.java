package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.eflmotif;

import java.io.Serializable;
import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotif;

public class EFLMotif implements IMotif, Serializable {

	private static final long serialVersionUID = 1L;
	protected INode startNode;
	protected INode endNode;
	protected IEdge feedbackEdge;
	protected INode[][] paths;


	public EFLMotif(INode startNode, INode endNode, IEdge feedbackEdge, INode[][] paths) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.feedbackEdge = feedbackEdge;
		this.paths = paths;
	}
	
	@Override
	public IEdge[] getEdges() {
		return new IEdge[]{feedbackEdge};
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object>[] getMotifData() {
		
		ArrayList<Object> ql1 = new ArrayList<Object>();
		ql1.add("Start Node");
		ql1.add(startNode.getType());
		ql1.add(startNode.getDb_id());
		ql1.add(startNode.toString());
		
		ArrayList<Object> ql2 = new ArrayList<Object>();
		ql2.add("End Node");
		ql2.add(endNode.getType());
		ql2.add(endNode.getDb_id());
		ql2.add(endNode.toString());
		
		ArrayList<Object> ql3 = new ArrayList<Object>();
		ql3.add("Feedback Edge");
		ql3.add(feedbackEdge.isType());
		ql3.add("");
		ql3.add("");
		
		int s = 0;
		
		for(int a=0;a<this.paths.length;a++)
		{
			for(int b=0;b<this.paths[a].length;b++)
			{
				s++;
			}
			s++;
		}
		
		ArrayList<Object>[] res = new ArrayList[3+s];
		
		res[0] = ql1;
		res[1] = ql2;
		res[2] = ql3;
		
		int z = 3;
		
		for(int a=0;a<this.paths.length;a++)
		{
			ArrayList<Object> qla = new ArrayList<Object>();
			qla.add("Path "+(a+1));
			qla.add("--");
			qla.add("--");
			qla.add("--");
			res[z] = qla;
			z++;
			for(int b=0;b<this.paths[a].length;b++)
			{
				ArrayList<Object> qlb = new ArrayList<Object>();
				if(b==0) qlb.add("Start Node");
				else if(b==(this.paths[a].length-1)) qlb.add("End Node");
				else qlb.add("Path Node");
				qlb.add(paths[a][b].getType());
				qlb.add(paths[a][b].getDb_id());
				qlb.add(paths[a][b].toString());
				res[z] = qlb;
				z++;
			}
		}
		
		return res;
	}

	@Override
	public String getName() {
		return this.startNode.getDb_id()+"->"+endNode.getDb_id()+" feedback by "+feedbackEdge.isType();
	}

	@Override
	public INode[] getNodes() {
		return new INode[]{this.startNode, this.endNode};
	}

	public INode getStartNode()
	{
		return this.startNode;
	}

	public INode getEndNode()
	{
		return this.endNode;
	}
	
	public INode[][] getPaths()
	{
		return this.paths;
	}
	
	public String toString() {
		String res = "Start Node\t"+startNode.getType()+"\t"+startNode.getDb_id()+"\t"+startNode.toString()+"\n";
		
		res += "End Node\t"+endNode.getType()+"\t"+endNode.getDb_id()+"\t"+endNode.toString()+"\n";
		
		res += "Feedback Edge\t"+feedbackEdge.isType()+"\n";
		
		for(int a=0;a<this.paths.length;a++)
		{
			res += "Path "+(a+1)+"\n";
			
			for(int b=0;b<this.paths[a].length;b++)
			{
				if(b==0) res += "Start Node\t";
				else if(b==(this.paths[a].length-1)) res += "End Node\t";
				else res += "Path Node\t";
				res += paths[a][b].getType()+"\t"+paths[a][b].getDb_id()+"\t"+paths[a][b].toString()+"\n";
			}
		}
		
		return res;
	}
}
