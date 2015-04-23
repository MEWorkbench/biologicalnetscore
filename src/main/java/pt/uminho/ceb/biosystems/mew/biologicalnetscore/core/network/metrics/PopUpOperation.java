package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;


public interface PopUpOperation {

	public abstract QRTable[] getPopUpData(INode node);
	
}
