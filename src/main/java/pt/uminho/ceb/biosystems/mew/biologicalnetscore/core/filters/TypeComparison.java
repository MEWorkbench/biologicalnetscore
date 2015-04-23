package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.filters;

public class TypeComparison extends AbstractComparison<String>{

	protected String value;
	protected String input;
	
	public TypeComparison(String value)
	{
		this.value = value;
	}
	
	@Override
	protected boolean checkReady() {
		return (this.input!=null);
	}

	@Override
	public boolean evaluate() {
		boolean res = value.equals(input);
		this.input = null;
		return res;
	}

	@Override
	protected void inputData(String data) {
		this.input = data;
	}

}
