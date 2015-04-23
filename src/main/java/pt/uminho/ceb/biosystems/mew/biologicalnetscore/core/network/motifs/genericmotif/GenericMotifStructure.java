package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.motifs.genericmotif;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class GenericMotifStructure {

	protected String[] verticesTypes;
	protected String[] verticesNames;
	protected String[][] type_edges; //postion number of vertex, 0-String, type of edge, 1- Integer Postion of connected edge
	protected int[][] connected_edges;
	
	public GenericMotifStructure(String[] verticesTypes, String[] verticesNames, String[][] typeEdges, int[][] connectedEdges) {
		this.verticesTypes = verticesTypes;
		this.verticesNames = verticesNames;
		type_edges = typeEdges;
		connected_edges = connectedEdges;
	}
	
	public GenericMotifStructure(String[] verticesTypes, String[][] typeEdges, int[][] connectedEdges) {
		this.verticesTypes = verticesTypes;
		this.verticesNames = null;
		type_edges = typeEdges;
		connected_edges = connectedEdges;
	}

	public GenericMotifStructure(File f) throws Exception{

		ArrayList<String> nNames = new ArrayList<String>();
		ArrayList<String> nTypes = new ArrayList<String>();
		HashMap<String, ArrayList<String[]>> con = new HashMap<String, ArrayList<String[]>>();
		
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		while(br.ready())
		{
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line,"\t");

			String lineType = st.nextToken();
			if(lineType.equals("Vertex Type"))
			{
				String nodeName = st.nextToken();
				String nodeType = st.nextToken();
				nNames.add(nodeName);
				nTypes.add(nodeType);
			}
			else if(lineType.equals("Edge"))
			{
				String edgeType = st.nextToken();
				String firstNode = st.nextToken();
				String secondNode = st.nextToken();
				
				if(!con.containsKey(firstNode))
				{
					con.put(firstNode, new ArrayList<String[]>());
				}
				
				con.get(firstNode).add(new String[]{edgeType, secondNode});
			}
		}
		
		br.close();
		fr.close();
		
		this.verticesTypes = new String[nNames.size()];
		this.verticesNames = new String[nNames.size()];
		this.type_edges = new String[nNames.size()][];
		this.connected_edges = new int[nNames.size()][];
		
		
		for(int a=0;a<nNames.size();a++)
		{
			this.verticesTypes[a] = nTypes.get(a);
			this.verticesNames[a] = nNames.get(a);
			
			if(con.containsKey(nNames.get(a)))
			{
				ArrayList<String[]> sx = con.get(nNames.get(a));
			
				this.type_edges[a] = new String[sx.size()];
				this.connected_edges[a] = new int[sx.size()];
			
				for(int b=0;b<sx.size();b++)
				{
					this.type_edges[a][b] = sx.get(b)[0];
					this.connected_edges[a][b] = nNames.indexOf(sx.get(b)[1]);
				}
			}
			else
			{
				this.type_edges[a] = new String[0];
				this.connected_edges[a] = new int[0];
			}
		}
		
	}

	public String[] getVerticesTypes() {
		return verticesTypes;
	}

	public String[][] getType_edges() {
		return type_edges;
	}

	public int[][] getConnected_edges() {
		return connected_edges;
	}

	public Object[][] getPointer(int i) //Integer - conneted node possition, String - edge type
	{
		ArrayList<Object[]> temp = new ArrayList<Object[]>();
		
		for(int a=0;a<connected_edges.length;a++)
		{
			if(a!=i)
			{
				for(int b=0;b<connected_edges[a].length;b++)
				{
					if(connected_edges[a][b]==i)
					{
						temp.add(new Object[]{new Integer(a), type_edges[a][b]});
					}
				}
			}
		}
		
		Object[][] res = new Object[temp.size()][];
		
		for(int a=0;a<temp.size();a++)
		{
			res[a] = temp.get(a);
		}
		
		return res;
	}

	public String[] getVerticesNames() {
		return verticesNames;
	}
	
}
