package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class QRDTTable extends AbstractTableModel implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String[] columnsNames = null;

	protected ArrayList <Object[]> table = null;
	protected String name;
	
	protected int[] type;
    
    public QRDTTable(ArrayList<String> columnsNames, String name, int[] type)
    {
    	this.table = new ArrayList <Object[]>();
    	
    	this.columnsNames = new String[columnsNames.size()];
    	
    	for(int i=0;i<columnsNames.size();i++) this.columnsNames[i] = columnsNames.get(i); 
    	this.name = name;
    	this.type = type;
    }
    
    public void addLine(ArrayList<Object> line)
    {
    	Object[] ln = new Object[line.size()];
    	
    	for(int i=0;i<line.size();i++) ln[i] = line.get(i);
    	
    	this.table.add(ln);
    }
    
    public void addLine(Object[] line)
    {
    	this.table.add(line);
    }
    
	public int getColumnCount() {
		return this.columnsNames.length;
	}

	public int getRowCount() {
		return table.size();
	}

	public Object getValueAt(int row, int column) {
        return this.table.get(row)[column];
	}
    
	public String getColumnName(int col) {
		return columnsNames[col];
	}
	 
	public String getName()
	{
		return this.name;
	}

	public ArrayList<Object[]> getTable() {
		return table;
	}

	public Object[] getRow(int row) {
        return this.table.get(row);
	}
	
	public String[] getColumnsNames() {
		return columnsNames;
	}
	
	public Class<?> getColumnClass(int c) {
		Class<?> ret;
		
		if(this.type[c]==0) ret = String.class;
		else if(this.type[c]==1) ret = Double.class;
		else ret = Integer.class;
		
		return ret;
		
    }
}
