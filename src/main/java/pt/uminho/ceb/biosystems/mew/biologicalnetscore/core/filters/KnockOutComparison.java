package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.filters;

public class KnockOutComparison extends AbstractComparison<String[]>{

	protected String type;
	protected String[] knockOuts;
	protected boolean ready = false;
	protected String inputType;
	protected String inputId;
	
	public KnockOutComparison(String type, String[] knockOuts)
	{
		this.type = type;
		this.knockOuts = knockOuts;
	}
	
	@Override
	protected boolean checkReady() {
		return this.ready;
	}

	@Override
	public boolean evaluate() {
		boolean res = this.type.equals(this.inputType);
		
		if(res)
		{
			boolean isThere = false;
			
			for(int i=0;!isThere && i<knockOuts.length;i++)
			{
				isThere = this.knockOuts[i].equals(this.inputId);
			}
			
			res = !isThere;
		}
		
		this.ready = false;
		
		return res;
	}

	@Override
	public void inputData(String[] data) {
		this.inputType = data[0];
		this.inputId = data[1];
		this.ready = true;
	}

}
