package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * This class is a container for subnetworks.
 * 
 * @author Jose Pedro
 *
 */

public class SubNetworksList extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<INetwork> subNetworks = null;

	public SubNetworksList()
	{
		this.subNetworks = new ArrayList<INetwork>();
	}
	
	public ArrayList<INetwork> getSubnetowrks() {
		return subNetworks;
	}

	public void setSubNetworks(ArrayList<INetwork> subNetworks) {
		this.subNetworks = subNetworks;
		setChanged();
		notifyObservers();
	}

	public void addSubNetworks(INetwork subNetwork) {
		this.subNetworks.add(subNetwork);
		setChanged();
		notifyObservers();
	}
	
	public ArrayList<INetwork> getSubNetworks() {
		return subNetworks;
	}
	
}
