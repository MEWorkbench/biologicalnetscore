package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;

import java.io.Serializable;

public class Rounder implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int n;
	
	public void setN(int n) {
		this.n = n;
	}

	public Rounder(int n) {
		this.n = n;
	}

	public String round(double i)
	{
		int d = 10;
		
		for(int z=0;z<(n-1);z++) d = 10*d;
		
		Double du = new Double((Math.round(i*d)))/d;	
		
		String sdu = du.toString();
		
		String res;
		
		if(sdu.endsWith("0") && sdu.length()==(3+n))
		{
			res = "";
			for(int h=0;h<5;h++) res += sdu.charAt(h);
		}
		else if(sdu.endsWith(".0"))
		{
			res = sdu;
			for(int h=0;h<(n-1);h++) res += "0";
		}
		else res = sdu;
		
		
		return res;
	}

	public Double roundD(double i)
	{
		int d = 10;
		
		for(int z=0;z<(n-1);z++) d = 10*d;
		
		Double du = new Double((Math.round(i*d)))/d;	
		
		String sdu = du.toString();
		
		String res;
		
		if(sdu.endsWith("0") && sdu.length()==(3+n))
		{
			res = "";
			for(int h=0;h<5;h++) res += sdu.charAt(h);
		}
		else if(sdu.endsWith(".0"))
		{
			res = sdu;
			for(int h=0;h<(n-1);h++) res += "0";
		}
		else res = sdu;
		
		
		return new Double(res);
	}
	
	
	
}