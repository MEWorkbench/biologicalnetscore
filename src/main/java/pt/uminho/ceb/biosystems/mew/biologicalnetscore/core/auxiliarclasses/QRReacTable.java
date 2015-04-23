package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;

import java.io.Serializable;
import java.util.ArrayList;

public class QRReacTable extends QRTable implements Serializable{

	private static final long serialVersionUID = 1L;

	protected String windowName;
	protected ArrayList<String> ids;
	protected ArrayList<String> types;
	
	public QRReacTable(ArrayList<String> columnsNames, String name, String windowName)
    {
		super(columnsNames, name);
		this.windowName = windowName;
		this.ids = new ArrayList<String>();
		this.types = new ArrayList<String>();
    }
	
	public QRReacTable(ArrayList<String> columnsNames, String name, String windowName, int[] types)
    {
		super(columnsNames, name, types);
		this.windowName = windowName;
		this.ids = new ArrayList<String>();
		this.types = new ArrayList<String>();
    }
    
    public void addLine(ArrayList<Object> line, String id, String type)
    {
    	super.addLine(line);
    	this.ids.add(id);
    	this.types.add(type);
    }
    
    public String getWindowName()
    {
    	return windowName;
    }
    
    public String getRowId(int row)
    {
    	return ids.get(row);
    }
    
    public String getRowType(int row)
    {
    	return types.get(row);
    }
}