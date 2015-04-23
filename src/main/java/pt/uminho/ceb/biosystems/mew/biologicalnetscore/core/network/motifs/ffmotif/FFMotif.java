package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.ffmotif;

import java.io.Serializable;
import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.IMotif;

public class FFMotif implements IMotif, Serializable{
	
	private static final long serialVersionUID = 1L;
	protected INode x;
	protected INode y;
	protected INode z;
	protected IEdge xy;
	protected IEdge yz;
	protected IEdge xz;
	
	public FFMotif(INode x, INode y, INode z, IEdge xy, IEdge yz, IEdge xz) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.xy = xy;
		this.yz = yz;
		this.xz = xz;
	}
	
	@Override
	public IEdge[] getEdges() {
		return new IEdge[]{this.xy,this.yz,this.xz};
	}
	
	@Override
	public INode[] getNodes() {
		return new INode[]{this.x,this.y,this.z};
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object>[] getMotifData() {
		
		ArrayList<Object> ql1 = new ArrayList<Object>();
		ql1.add("x");
		ql1.add(x.getType());
		ql1.add(x.getDb_id());
		ql1.add(x.toString());
		
		ArrayList<Object> ql2 = new ArrayList<Object>();
		ql2.add("y");
		ql2.add(y.getType());
		ql2.add(y.getDb_id());
		ql2.add(y.toString());
		
		ArrayList<Object> ql3 = new ArrayList<Object>();
		ql3.add("z");
		ql3.add(z.getType());
		ql3.add(z.getDb_id());
		ql3.add(z.toString());
		
		ArrayList<Object> ql4 = new ArrayList<Object>();
		ql4.add("x -> y");
		ql4.add(xy.isType());
		ql4.add("");
		ql4.add("");
		
		ArrayList<Object> ql5 = new ArrayList<Object>();
		ql5.add("y -> z");
		ql5.add(yz.isType());
		ql5.add("");
		ql5.add("");
		
		ArrayList<Object> ql6 = new ArrayList<Object>();
		ql6.add("x -> z");
		ql6.add(xz.isType());
		ql6.add("");
		ql6.add("");
		
		ArrayList<Object>[] res = new ArrayList[6];
		
		res[0] = ql1;
		res[1] = ql2;
		res[2] = ql3;
		res[3] = ql4;
		res[4] = ql5;
		res[5] = ql6;
		
		return res;
	}

	@Override
	public String getName() {
		return this.x.getDb_id()+"-"+this.y.getDb_id()+"-"+this.z.getDb_id()+" ("+xy.isType()+","+yz.isType()+","+xz.isType()+")";
	}

	public INode getX() {
		return x;
	}

	public INode getY() {
		return y;
	}

	public INode getZ() {
		return z;
	}
	
	public String toString() {
		
		String res = "x\t"+x.getType()+"\t"+x.getDb_id()+"\t"+x.toString()+"\n";
		res += "y\t"+y.getType()+"\t"+y.getDb_id()+"\t"+y.toString()+"\n";
		res += "z\t"+z.getType()+"\t"+z.getDb_id()+"\t"+z.toString()+"\n";
		
		res += "xy\t"+xy.isType()+"\n";
		res += "yz\t"+yz.isType()+"\n";
		res += "xz\t"+xz.isType()+"\n";

		return res;
	}

}
