package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.IFilter;

/**
 * Interface that is implemented by networks.
 * 
 * @author Jose Pedro
 *
 */

public interface INetwork {
	
	/**
	 * 
	 * @return Returns the SubNetworksList object associated with the network, 
	 * this object contains all the subnetworks that were created. 
	 */
//	public SubNetworksList getSubNetworks();

	/**
	 * 
	 * @return Returns the name of the network.
	 */
	public String getName();

	/**
	 * 
	 * @return Returns an array with all the edges of the network.
	 */
	public IEdge[] getEdges();

	/**
	 * 
	 * @return Returns an array with all the nodes of the network.
	 */
	public INode[] getNodes();

	/**
	 * 
	 * @param db_id Unique identification of the node.
	 * @return
	 */
	public INode getNode(String db_id, String type);

	/**
	 * 
	 * @param type Type of nodes.
	 * @return Returns all nodes of one type.
	 */
	public INode[] getNodes(String type);

	/**
	 * 
	 * @return Returns a array with the types of edges that belong to the network.
	 */
	public String[] getEdgeTypesArray();

	/**
	 * 
	 * @return Returns a array with the types of nodes that belong to the network.
	 */
	public String[] getNodeTypesArray();
	
	/**
	 * 
	 * @return Returns a representation of the network using a QRTable object, 
	 * this object stores the data as a table and as such it is used normally 
	 * for the visualization of the network.
	 */
	public QRTable getValues();
	
	/**
	 * 
	 * @param type
	 * @return Returns an array with all edges of a type.
	 */
	public IEdge[] getEdgesType(String type);
	
	/**
	 * 
	 * @param type
	 * @return Returns a representation of the edges of one type of the network using a QRTable object.
	 */
	public QRTable getTypeValues(String type);
	
	/**
	 * 
	 * @return Returns a representation of the edges of the network using a QRTable object.
	 */
	public QRTable getTypeValues();
	
	/**
	 * 
	 * @return Returns a representation of the nodes of the network using a QRTable object.
	 */
	public QRTable getNodesValues();
	
	/**
	 * 
	 * @return Returns a simplified representation of the nodes of the network using a QRTable object.
	 */
	public QRTable getNodesData();
	
	/**
	 * 
	 * @param type
	 * @return Returns a representation of all the nodes of a type using a QRTable object.
	 */
	public QRTable getNodeTypeValues(String type);

	public void setName(String name);
	
	/**
	 * Returns the number of nodes of one type.
	 * @param type
	 * @return
	 */
	public int countType(String type);
	
//	/**
//	 * 
//	 * @param mask
//	 */
//	public void setMask(int mask);
	
	/**
	 * Creates a sub network using a filter.
	 * @param filter
	 * @return
	 */
	public INetwork getSubNetwork(IFilter filter);
	
	public int degree(INode n);
	
	public int inDegree(INode n);
	
	public int outDegree(INode n);
	
	public INode[] getConectedNodes(IEdge e);
	
	public IEdge[] getInEdges(INode n);
	
	public IEdge[] getOutEdges(INode n);
	
	public void addNode(INode n);
	
	public void addEdge(IEdge edge, INode n1, INode n2);
	
	public INetwork clone();
	
	public void removeNode(INode n);
	
	public void removeEdge(IEdge e);
	
	public void setEdgeTypesArray(String[] etypes);
	
	public void setNodeTypesArray(String[] ntypes);
}