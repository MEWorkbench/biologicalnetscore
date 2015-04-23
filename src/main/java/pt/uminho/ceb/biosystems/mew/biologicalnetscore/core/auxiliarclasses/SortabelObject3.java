package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;


public class SortabelObject3 implements Comparable<Object>{

	protected String value;
	protected Object n;
	
	public SortabelObject3(String value, Object n)
	{
		this.value = value;
		this.n = n;
	}
	
	public int compareTo(Object arg0) {
		String so = ((SortabelObject3)arg0).getValue();
		
		return this.value.compareTo(so);
	}

	public Object getNode() {
		return n;
	}

	public String getValue() {
		return value;
	}

}
