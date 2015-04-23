package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.edflmotif;

import java.io.Serializable;
import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotif;

public class EDFLMotif implements IMotif, Serializable {

	private static final long serialVersionUID = 1L;
	protected INode startNode;
	protected INode endNode;
	protected INode[][] feedbackPaths;
	protected INode[][] paths;


	public EDFLMotif(INode startNode, INode endNode, INode[][] feedbackPaths, INode[][] paths) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.feedbackPaths = feedbackPaths;
		this.paths = paths;
	}
	
	@Override
	public IEdge[] getEdges() {
		return new IEdge[]{};
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
		
		int s = 0;
		
		for(int a=0;a<this.paths.length;a++)
		{
			for(int b=0;b<this.paths[a].length;b++)
			{
				s++;
			}
			s++;
		}
		
		for(int a=0;a<this.feedbackPaths.length;a++)
		{
			for(int b=0;b<this.feedbackPaths[a].length;b++)
			{
				s++;
			}
			s++;
		}
		
		
//		ArrayList<Object> ql3 = new ArrayList<Object>();
//		ql3.add("Number of Paths");
//		ql3.add(""+paths.length);
//		ql3.add("");
//		ql3.add("");
//		
//		ArrayList<Object> ql4 = new ArrayList<Object>();
//		ql4.add("Number of Feedback Paths");
//		ql4.add(""+feedbackPaths.length);
//		ql4.add("");
//		ql4.add("");
		
		ArrayList<Object>[] res = new ArrayList[2+s];
		
		res[0] = ql1;
		res[1] = ql2;
//		res[2] = ql3;
//		res[3] = ql4;
		
		int z = 2;
		
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
		
		for(int a=0;a<this.feedbackPaths.length;a++)
		{
			ArrayList<Object> qla = new ArrayList<Object>();
			qla.add("Feedback Path "+(a+1));
			qla.add("--");
			qla.add("--");
			qla.add("--");
			res[z] = qla;
			z++;
			for(int b=0;b<this.feedbackPaths[a].length;b++)
			{
				ArrayList<Object> qlb = new ArrayList<Object>();
				if(b==0) qlb.add("End Node");
				else if(b==(this.feedbackPaths[a].length-1)) qlb.add("Start Node");
				else qlb.add("Feedback Node");
				qlb.add(feedbackPaths[a][b].getType());
				qlb.add(feedbackPaths[a][b].getDb_id());
				qlb.add(feedbackPaths[a][b].toString());
				res[z] = qlb;
				z++;
			}
		}
		
		return res;
	}

	@Override
	public String getName() {
		return this.startNode.getDb_id()+"->"+endNode.getDb_id()+" "+paths.length+" Paths "+feedbackPaths.length+" Feedback Paths";
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
	
	public INode[][] getFeedbackPaths()
	{
		return this.feedbackPaths;
	}
	
	public String toString() {
		String res = "Start Node\t"+startNode.getType()+"\t"+startNode.getDb_id()+"\t"+startNode.toString()+"\n";
		
		res += "End Node\t"+endNode.getType()+"\t"+endNode.getDb_id()+"\t"+endNode.toString()+"\n";
		
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
		
		for(int a=0;a<this.feedbackPaths.length;a++)
		{
			res += "Feedback Path "+(a+1)+"\n";
			
			for(int b=0;b<this.feedbackPaths[a].length;b++)
			{
				if(b==0) res += "End Node\t";
				else if(b==(this.feedbackPaths[a].length-1)) res += "Start Node\t";
				else res += "Feedback Node\t";
				res += feedbackPaths[a][b].getType()+"\t"+feedbackPaths[a][b].getDb_id()+"\t"+feedbackPaths[a][b].toString()+"\n";
			}
		}
		
		return res;
	}
}
