package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;


public class SortabelObject2 implements Comparable<Object>{

	protected double value;
	protected Object n;
	
	public SortabelObject2(double value, Object n)
	{
		this.value = value;
		this.n = n;
	}
	
	public int compareTo(Object arg0) {
		double so = ((SortabelObject2)arg0).getValue();
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

}
