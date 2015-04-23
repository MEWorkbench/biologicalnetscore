package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;


public class SortabelObject implements Comparable<Object>{

	protected int value;
	protected Object n;
	
	public SortabelObject(int value, Object n)
	{
		this.value = value;
		this.n = n;
	}
	
	public int compareTo(Object arg0) {
		int so = ((SortabelObject)arg0).getValue();
		return so-value;
	}

	public Object getNode() {
		return n;
	}

	public int getValue() {
		return value;
	}

}
