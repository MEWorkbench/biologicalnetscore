package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

/**
 * Interface for nodes, all classes that implement this interface 
 * are used as nodes in networks that implement the INetwork interface.
 * All nodes by default contain a unique identification field.
 * 
 * @author Jose Pedro
 *
 */

public interface INode {

	/**
	 * 
	 * @return Returns the data associated with the node.
	 */
	public String[] getDatabase_data();

	/**
	 * 
	 * @return Returns the names of the data fields associated with the node.
	 */
	public String[] getDatabase_datatype();

	/**
	 * 
	 * @return Returns the unique identification of the node.
	 */
	public String getDb_id();

	public String toString();

	/**
	 * 
	 * @return Returns the type of the entity that the node represents.
	 */
	public String getType();
	
	public boolean equals(Object n);
	
	public String getData(String dataname);
	
	public INode clone();
	
	public void addData(String dataname, String data);
	
	public void removeData(String dataname);
}
