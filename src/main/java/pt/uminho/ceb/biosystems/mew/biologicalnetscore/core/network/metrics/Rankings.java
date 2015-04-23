package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Rankings extends Observable implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<StatData> ranks = null;
	protected RankerData rd;
	
	public Rankings(RankerData rd)
	{
		this.rd = rd;
		this.ranks = new ArrayList<StatData>();
	}
	
	public ArrayList<StatData> getRanks() {
		return ranks;
	}

	public void setRanks(ArrayList<StatData> ranks) {
		this.ranks = ranks;
		setChanged();
		notifyObservers();
	}

	public void addRank(StatData rank) {
		this.ranks.add(rank);
		setChanged();
		notifyObservers();
		((RankingData)rank).addData(this.rd);
	}
	
}
