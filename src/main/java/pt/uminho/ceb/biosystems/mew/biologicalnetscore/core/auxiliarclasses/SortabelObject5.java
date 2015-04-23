package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;


public class SortabelObject5 implements Comparable<Object>{

	protected String value;
	protected Object n;
	protected int position;
	
	public SortabelObject5(int position, String value, Object n)
	{
		this.value = value;
		this.n = n;
		this.position = position;
	}
	
	public int compareTo(Object arg0) {
		String so = ((SortabelObject5)arg0).getValue();
		
		return this.value.compareTo(so);
	}

	public Object getNode() {
		return n;
	}

	public String getValue() {
		return value;
	}

	public int getPosition() {
		return position;
	}

}
