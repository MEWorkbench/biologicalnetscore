package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;
import java.util.Observable;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class Statistics extends Observable implements Serializable {
	//TODO: possivlemente remover
	private static final long serialVersionUID = 1L;
	protected Rankings ranks;
	protected DegreeData dd;
	protected ShortestPathMetrics spm;
	protected ClusteringMetrics cc;
	protected RankerData rd;
	protected SubGraphsFinder sgf;
	
	public Statistics(INode[] nodes)
	{
		this.rd = new RankerData(nodes);
		this.ranks = new Rankings(this.rd);
		this.dd = null;
		this.spm = null;
		this.sgf = null;
		this.cc = null;
	}
	
	public RankerData getRankerData() {
		return rd;
	}

	public Rankings getRanks() {
		return ranks;
	}

	public ShortestPathMetrics getSPM() {
		return spm;
	}

	public ClusteringMetrics getCC() {
		return cc;
	}

	public DegreeData getDD() {
		return dd;
	}
	
	public SubGraphsFinder getSGF() {
		return sgf;
	}

	public void setShortestPathMetrics(ShortestPathMetrics spm) {
		this.spm = spm;
	}

	public void setClusteringMetrics(ClusteringMetrics cc) {
		this.cc = cc;
	}

	public void setDegreeData(DegreeData dd) {
		this.dd = dd;
	}

	public void setSGF(SubGraphsFinder sgf) {
		this.sgf = sgf;
	}
}
