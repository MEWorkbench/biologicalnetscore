package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

import java.util.HashMap;

/**
 * This interface is the generic template that is used to create edge classes.
 * Usually a classes does not implements INodeEdge directly, instead it implements 
 * one of it is subinterfaces.
 * 
 * @author Jose Pedro
 *
 */

public interface IEdge {

	
	/**
	 * Returns the type of the edge, this field is usually used to i
	 * dentify the type of connection that the edge represents.
	 * @return
	 */
	public String isType();

	/**
	 * Returns the data associated with the edge.
	 * @return
	 */
	public String[] getDatabase_data();

	/**
	 * Returns the names of the data fields associated with the edge.
	 * @return
	 */
	public String[] getDatabase_datatype();

	/**
	 * Returns the extra data associated with the edge. 
	 * The extra data is a variable set of data fields that are used 
	 * when an undetermined number of properties have to be associated with an edge type.
	 * @return
	 */
	public HashMap<String, String[]> getExtradata();

	/**
	 * Sets the extra data associated with the edge.
	 * @param extradata
	 * @param extradatanames
	 */
	public void setExtradata(HashMap<String, String[]> extradata, String[] extradatanames);

	/**
	 * Returns the names of the fields of the extra data.
	 * @return
	 */
	public String[] getExtradatanames();
	
	/**
	 * Return the identification of the starter node.
	 * @return
	 */

	public String getData(String dataname);
	
	public void addData(String dataname, String data);
	
	public IEdge clone();
	
	public boolean equals(IEdge e);
}
