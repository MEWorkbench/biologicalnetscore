package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.filters;

public class DoubleValueBiggerThanComparison extends AbstractComparison<Double>{

	protected double value;
	protected double input;
	protected boolean ready = false;
	protected boolean forceTrue = false;
	
	public DoubleValueBiggerThanComparison(double value)
	{
		this.value = value;
	}
	
	@Override
	protected boolean checkReady() {
		return this.ready;
	}

	@Override
	public boolean evaluate() {
		boolean res = value < input;
		this.ready = false;
		
		if(this.forceTrue)
		{
			res = true;
			this.forceTrue = false;
		}
		
		return res;
	}

	@Override
	public void inputData(Double data) {
		this.input = data.doubleValue();
		this.ready = true;
	}
	
	public void forceTrue(boolean forceTrue)
	{
		this.forceTrue = forceTrue; 
		this.ready = true;
	}

}
