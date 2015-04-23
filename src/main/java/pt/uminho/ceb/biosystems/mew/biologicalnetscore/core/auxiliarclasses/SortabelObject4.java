package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;


public class SortabelObject4 implements Comparable<Object>{

	protected double value;
	protected Object n;
	protected int position;
	
	public SortabelObject4(int position, double value, Object n)
	{
		this.value = value;
		this.n = n;
		this.position = position;
	}
	
	public int compareTo(Object arg0) {
		double so = ((SortabelObject4)arg0).getValue();
		double gak = value-so;
		
		if(gak>0) return 1;
		else if(gak<0) return -1;
		else return 0;
	}

	public Object getNode() {
		return n;
	}

	public double getValue() {
		return value;
	}

	public int getPosition() {
		return position;
	}

}
