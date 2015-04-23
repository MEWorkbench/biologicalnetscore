package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.exportation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class Xgmml implements IExport{

	protected int[][] convertData; //0-no, 1- double/real 2 - integer
	protected String[] nodeType;
	
	public Xgmml()
	{
		this.convertData = null;
		this.nodeType = null;
	}
	
	public Xgmml(int[][] convertData, String[] nodeType)
	{
		this.convertData = convertData;
		this.nodeType = nodeType;
	}
	
	public void export(INetwork net, File f) throws IOException {
		
		if(this.convertData==null) exportNormal(net, f);
		else exportSpecial(net, f);
	}
	
	protected void exportSpecial(INetwork net, File f) throws IOException {

		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		bw.write("<graph label=\""+net.toString()+"\" xmlns:dc=" +
			"\"http://purl.org/dc/elements/1.1/\" xmlns:xlink=" +
			"\"http://www.w3.org/1999/xlink\" xmlns:rdf=\"" +
			"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:cy=" +
			"\"http://www.cytoscape.org\" xmlns=\"http://www.cs.rpi.edu/XGMML\">\n");
		bw.write("\t<att name=\"networkMetadata\">\n");
		bw.write("\t</att>\n");
		
		INode[] nodes = net.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			INode node = nodes[i];
			
			bw.write("\t<node label=\""+node.toString()+" ("+node.getType()+", "+node.getDb_id()+")"+"\" id=\"-"+(i+1)+"\">\n");

			bw.write("\t\t<att type=\"string\" name=\"Node Type\" value=\""+node.getType()+"\"/>\n");
			bw.write("\t\t<att type=\"string\" name=\"Name\" value=\""+node.toString()+"\"/>\n");
			bw.write("\t\t<att type=\"string\" name=\"Node Id\" value=\""+node.getDb_id()+"\"/>\n");
			
			int[] converT = null;
			
			for(int z=0;converT==null && z<nodeType.length;z++)
			{
				if(this.nodeType[z].equals(node.getType())) converT = this.convertData[z];
			}
			
			String[] datatype = node.getDatabase_datatype();
			String[] data = node.getDatabase_data();
			
			if(converT==null)
			{
				for(int a=0;a<datatype.length;a++)
				{
					bw.write("\t\t<att type=\"string\" name=\""+datatype[a]+"\" value=\""+data[a]+"\"/>\n");
				}
			}
			else
			{
				for(int a=0;a<datatype.length;a++)
				{
					if(converT[a]==0) bw.write("\t\t<att type=\"string\" name=\""+datatype[a]+"\" value=\""+data[a]+"\"/>\n");
					else if(converT[a]==1) bw.write("\t\t<att type=\"real\" name=\""+datatype[a]+"\" value=\""+data[a]+"\"/>\n");
					else if(converT[a]==2) bw.write("\t\t<att type=\"integer\" name=\""+datatype[a]+"\" value=\""+data[a]+"\"/>\n");
				}
			}
			
			bw.write("\t</node>\n");
		}
		
		IEdge[] edges = net.getEdges();
		
		for(int i=0;i<edges.length;i++)
		{
			IEdge edge = edges[i];
			
			INode[] ns = net.getConectedNodes(edge);
			
			int s=-1, t=-1;
			
			for(int a=0;s==-1 || t==-1;a++)
			{
				if(s==-1) if(ns[0].equals(nodes[a])) s=a;
				if(t==-1) if(ns[1].equals(nodes[a])) t=a;
			}
			
			
			bw.write("\t<edge label=\""+i+" ("+edge.isType()+")\" source=\"-"+(s+1)+"\" target=\"-"+(t+1)+"\">\n");
			
			bw.write("\t\t<att type=\"string\" name=\"edge type\" value=\""+edge.isType()+"\"/>\n");
			bw.write("\t\t<att type=\"string\" name=\"source\" value=\""+ns[0].getDb_id()+"\"/>\n");
			bw.write("\t\t<att type=\"string\" name=\"target\" value=\""+ns[1].getDb_id()+"\"/>\n");
			
			bw.write("\t</edge>\n");
		}
		
		bw.write("</graph>\n");
		
		bw.close();
		fw.close();
	}
	
	protected void exportNormal(INetwork net, File f) throws IOException {
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		bw.write("<graph label=\""+net.toString()+".subset.sif\" xmlns:dc=" +
			"\"http://purl.org/dc/elements/1.1/\" xmlns:xlink=" +
			"\"http://www.w3.org/1999/xlink\" xmlns:rdf=\"" +
			"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:cy=" +
			"\"http://www.cytoscape.org\" xmlns=\"http://www.cs.rpi.edu/XGMML\">\n");
		bw.write("\t<att name=\"networkMetadata\">\n");
		bw.write("\t</att>\n");
		
		INode[] nodes = net.getNodes();
		
		for(int i=0;i<nodes.length;i++)
		{
			INode node = nodes[i];
			
			bw.write("\t<node label=\""+node.toString()+" ("+node.getType()+", "+node.getDb_id()+")"+"\" id=\"-"+(i+1)+"\">\n");

			bw.write("\t\t<att type=\"string\" name=\"Node Type\" value=\""+node.getType()+"\"/>\n");
			bw.write("\t\t<att type=\"string\" name=\"Name\" value=\""+node.toString()+"\"/>\n");
			bw.write("\t\t<att type=\"string\" name=\"Node Id\" value=\""+node.getDb_id()+"\"/>\n");
			
			String[] datatype = node.getDatabase_datatype();
			String[] data = node.getDatabase_data();
			
			for(int a=0;a<datatype.length;a++)
			{
				bw.write("\t\t<att type=\"string\" name=\""+datatype[a]+"\" value=\""+data[a]+"\"/>\n");
			}
			
			bw.write("\t</node>\n");
		}
		
		IEdge[] edges = net.getEdges();
		
		for(int i=0;i<edges.length;i++)
		{
			IEdge edge = edges[i];
			
			INode[] ns = net.getConectedNodes(edge);
			
			int s=-1, t=-1;
			
			for(int a=0;s==-1 || t==-1;a++)
			{
				if(s==-1) if(ns[0].equals(nodes[a])) s=a;
				if(t==-1) if(ns[1].equals(nodes[a])) t=a;
			}
			
			
			bw.write("\t<edge label=\""+i+" ("+edge.isType()+")\" source=\"-"+(s+1)+"\" target=\"-"+(t+1)+"\">\n");
			
			bw.write("\t\t<att type=\"string\" name=\"edge type\" value=\""+edge.isType()+"\"/>\n");
			bw.write("\t\t<att type=\"string\" name=\"source\" value=\""+ns[0].getDb_id()+"\"/>\n");
			bw.write("\t\t<att type=\"string\" name=\"target\" value=\""+ns[1].getDb_id()+"\"/>\n");
			
			bw.write("\t</edge>\n");
		}
		
		bw.write("</graph>\n");
		
		bw.close();
		fw.close();
	}
	
}
