package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.fileformate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.fileformate.exporter.IExporterInterface;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.fileformate.importer.ICytoscapeFormateImporterInterface;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.fileformate.importer.IImporterInterface;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class SIFInterface implements IImporterInterface, IExporterInterface, ICytoscapeFormateImporterInterface {

   public void exportData(INetwork net, File f) throws IOException {
      String path = f.getParentFile().toString();
      FileWriter fw = new FileWriter(f);
      BufferedWriter bw = new BufferedWriter(fw);
      FileWriter fwt = new FileWriter(path + "\\type.EA");
      BufferedWriter bwt = new BufferedWriter(fwt);
      IEdge[] edges = net.getEdges();
      INode[] nodes = net.getNodes();
      bwt.write("type (class=java.lang.String)\n");

      for(int types = 0; types < edges.length; ++types) {
         IEdge metaDataTypes = edges[types];
         INode[] a = net.getConectedNodes(metaDataTypes);
         bw.write(a[0].getDb_id() + " (" + a[0].getType() + ")\tDirectedEdge\t" + a[1].getDb_id() + " (" + a[1].getType() + ")\n");
         bwt.write(a[0].getDb_id() + "+%28" + a[0].getType() + "%29+%28DirectedEdge%29+" + a[1].getDb_id() + "+%28" + a[1].getType() + "%29 = " + metaDataTypes.isType().replace("+", "%2B").replace(' ', '+') + "\n");
      }

      bwt.close();
      fwt.close();
      bw.close();
      fw.close();
      String[] var19 = net.getEdgeTypesArray();
      ArrayList var18 = new ArrayList();

      String[] mdata;
      int b;
      int var20;
      for(var20 = 0; var20 < var19.length; ++var20) {
         mdata = net.getEdgesType(var19[var20])[0].getDatabase_datatype();

         for(b = 0; b < mdata.length; ++b) {
            if(!var18.contains(mdata[b])) {
               var18.add(mdata[b]);
            }
         }
      }

      String data;
      String var21;
      for(var20 = 0; var20 < var18.size(); ++var20) {
         var21 = (String)var18.get(var20);
         fw = new FileWriter(path + "\\" + var21 + ".EA");
         bw = new BufferedWriter(fw);
         bw.write((String)var18.get(var20) + " (class=java.lang.String)\n");

         for(b = 0; b < edges.length; ++b) {
            IEdge node = edges[b];
            data = node.getData(var21);
            if(data != null) {
               INode[] ns = net.getConectedNodes(node);
               bw.write(ns[0].getDb_id() + "+%28" + ns[0].getType() + "%29+%28DirectedEdge%29+" + ns[1].getDb_id() + "+%28" + ns[1].getType() + "%29 = " + data.replace("+", "%2B").replace(' ', '+') + "\n");
            }
         }

         bw.close();
         fw.close();
      }

      var19 = net.getNodeTypesArray();
      var18 = new ArrayList();

      for(var20 = 0; var20 < var19.length; ++var20) {
         mdata = net.getNodes(var19[var20])[0].getDatabase_datatype();

         for(b = 0; b < mdata.length; ++b) {
            if(!var18.contains(mdata[b])) {
               var18.add(mdata[b]);
            }
         }
      }

      for(var20 = 0; var20 < var18.size(); ++var20) {
         var21 = (String)var18.get(var20);
         fw = new FileWriter(path + "\\" + var21 + ".NA");
         bw = new BufferedWriter(fw);
         bw.write((String)var18.get(var20) + " (class=java.lang.String)\n");

         INode var22;
         for(b = 0; b < nodes.length; ++b) {
            var22 = nodes[b];
            data = var22.getData(var21);
            if(data != null) {
               bw.write(var22.getDb_id() + "+%28" + var22.getType() + "%29 = " + data.replace("+", "%2B").replace(' ', '+') + "\n");
            }
         }

         bw.close();
         fw.close();
         fw = new FileWriter(path + "\\type.NA");
         bw = new BufferedWriter(fw);
         fwt = new FileWriter(path + "\\name.NA");
         bwt = new BufferedWriter(fwt);
         bw.write("type (class=java.lang.String)\n");
         bwt.write("name (class=java.lang.String)\n");

         for(b = 0; b < nodes.length; ++b) {
            var22 = nodes[b];
            bw.write(var22.getDb_id() + "+%28" + var22.getType() + "%29 = " + var22.getType().replace("+", "%2B").replace(' ', '+') + "\n");
            bwt.write(var22.getDb_id() + "+%28" + var22.getType() + "%29 = " + var22.toString().replace("+", "%2B").replace(' ', '+') + "\n");
         }

         bwt.close();
         fwt.close();
         bw.close();
         fw.close();
      }

   }

   public JungNetwork importData(File f) throws IOException {
      Graph graph = this.readGraph(f);
      JungNetwork res = new JungNetwork(graph, "");
      return res;
   }

   protected Graph readGraph(File f) throws IOException {
      SparseMultigraph graph = new SparseMultigraph();
      FileReader fr = new FileReader(f);
      BufferedReader br = new BufferedReader(fr);
      ArrayList addedNodes = new ArrayList();

      while(br.ready()) {
         String line = br.readLine();
         StringTokenizer st = new StringTokenizer(line, "\t");
         String node1 = st.nextToken();
         st.nextToken();
         String node2 = st.nextToken();
         String type1 = "unknown";
         String type2 = "unknown";
         JungNode jnodes;
         if(!addedNodes.contains(node1)) {
            if(this.createdByMe(node1)) {
               st = new StringTokenizer(node1, " ");
               node1 = st.nextToken();
               type1 = st.nextToken();
               type1 = type1.substring(1, type1.length() - 1);
               jnodes = new JungNode(type1, node1, node1, new String[0], new String[0], addedNodes.size());
               graph.addVertex(jnodes);
            } else {
               jnodes = new JungNode("unknown", node1, node1, new String[0], new String[0], addedNodes.size());
               graph.addVertex(jnodes);
            }

            addedNodes.add(node1);
         } else if(this.createdByMe(node1)) {
            st = new StringTokenizer(node1, " ");
            node1 = st.nextToken();
            type1 = st.nextToken();
            type1 = type1.substring(1, type1.length() - 1);
         }

         if(!addedNodes.contains(node2)) {
            if(this.createdByMe(node2)) {
               st = new StringTokenizer(node2, " ");
               node2 = st.nextToken();
               type2 = st.nextToken();
               type2 = type2.substring(1, type2.length() - 1);
               jnodes = new JungNode(type2, node2, node2, new String[0], new String[0], addedNodes.size());
               graph.addVertex(jnodes);
            } else {
               jnodes = new JungNode("unknown", node2, node2, new String[0], new String[0], addedNodes.size());
               graph.addVertex(jnodes);
            }

            addedNodes.add(node2);
         } else if(this.createdByMe(node2)) {
            st = new StringTokenizer(node2, " ");
            node2 = st.nextToken();
            type2 = st.nextToken();
            type2 = type2.substring(1, type2.length() - 1);
         }

         JungNode[] var17 = (JungNode[])graph.getVertices().toArray(new JungNode[0]);
         JungNode jn1 = null;
         JungNode jn2 = null;

         for(int edge = 0; jn1 == null || jn2 == null; ++edge) {
            if(jn1 == null && var17[edge].getDb_id().equals(node1) && var17[edge].getType().equals(type1)) {
               jn1 = var17[edge];
            }

            if(jn2 == null && var17[edge].getDb_id().equals(node2) && var17[edge].getType().equals(type2)) {
               jn2 = var17[edge];
            }
         }

         JungEdge var18 = new JungEdge("unknown", new String[0], new String[0]);
         graph.addEdge(var18, jn1, jn2, EdgeType.DIRECTED);
      }

      br.close();
      fr.close();
      return graph;
   }

   protected boolean createdByMe(String node) {
      String[] sp = node.split("\\s");
      if(sp.length == 2) {
         String type = sp[1];
         if(type.length() > 3 && type.charAt(0) == 40 && type.charAt(type.length() - 1) == 41) {
            return true;
         }
      }

      return false;
   }

   public JungNetwork importData(File f, File[] nodeMetaData, File[] edgeMetaData) throws IOException {
      Graph graph = this.readGraph(f);
      JungNetwork res = new JungNetwork(graph, "");

      int i;
      for(i = 0; i < nodeMetaData.length; ++i) {
         this.readNodeAtribute(res, nodeMetaData[i]);
      }

      for(i = 0; i < edgeMetaData.length; ++i) {
         this.readEdgeAtribute(res, edgeMetaData[i]);
      }

      return res;
   }

   protected void readNodeAtribute(JungNetwork net, File f) throws IOException {
      FileReader fr = new FileReader(f);
      BufferedReader br = new BufferedReader(fr);
      String type = br.readLine().split("\\s")[0];
      JungNode[] nodes;
      if(type.equals("type")) {
         nodes = net.getNodes("unknown");
      } else {
         nodes = net.getNodes();
      }

      while(br.ready()) {
         String line = br.readLine();
         line = line.replace('+', ' ').replace("%28", "(").replace("%29", ")").replace("%2B", "+");
         String[] t2 = line.split("\\s=\\s");
         String node = t2[0];
         String metaData = t2[1];
         boolean stop;
         int i;
         if(type.equals("type")) {
            stop = false;

            for(i = 0; !stop && i < nodes.length; ++i) {
               if(node.equals(nodes[i].getDb_id())) {
                  nodes[i].setType(metaData);
                  stop = true;
               }
            }
         } else {
            stop = false;
            if(this.createdByMe(node)) {
               for(i = 0; !stop && i < nodes.length; ++i) {
                  if(node.equals(nodes[i].getDb_id() + " (" + nodes[i].getType() + ")")) {
                     if(type.equals("name")) {
                        nodes[i].setName(metaData);
                     } else {
                        nodes[i].addData(type, metaData);
                     }

                     stop = true;
                  }
               }
            } else {
               for(i = 0; !stop && i < nodes.length; ++i) {
                  if(node.equals(nodes[i].getDb_id()) && nodes[i].getType().equals("unknown")) {
                     if(type.equals("name")) {
                        nodes[i].setName(metaData);
                     } else {
                        nodes[i].addData(type, metaData);
                     }

                     stop = true;
                  }
               }
            }
         }
      }

      br.close();
      fr.close();
   }

   protected void readEdgeAtribute(JungNetwork net, File f) throws IOException {
      FileReader fr = new FileReader(f);
      BufferedReader br = new BufferedReader(fr);
      String type = br.readLine().split("\\s")[0];
      JungEdge[] edges;
      if(type.equals("type")) {
         edges = net.getEdgesType("unknown");
      } else {
         edges = net.getEdges();
      }

      while(br.ready()) {
         String line = br.readLine();
         line = line.replace('+', ' ').replace("%28", "(").replace("%29", ")").replace("%2B", "+");
         String[] t2 = line.split("\\s=\\s");
         String edgeData = t2[0];
         String metaData = t2[1];
         t2 = edgeData.split("\\s\\(DirectedEdge\\)\\s");
         String node1 = t2[0];
         String node2 = t2[1];
         boolean node1Mbm = this.createdByMe(node1);
         boolean node2Mbm = this.createdByMe(node2);

         for(int i = 0; i < edges.length; ++i) {
            INode[] ns = net.getConectedNodes(edges[i]);
            boolean node1Valid = false;
            boolean node2Valid = false;
            if(node1Mbm) {
               node1Valid = node1.equals(ns[0].getDb_id() + " (" + ns[0].getType() + ")");
            } else {
               node1Valid = node1.equals(ns[0].getDb_id());
            }

            if(node2Mbm) {
               node2Valid = node2.equals(ns[1].getDb_id() + " (" + ns[1].getType() + ")");
            } else {
               node2Valid = node2.equals(ns[1].getDb_id());
            }

            if(node1Valid && node2Valid) {
               if(type.equals("type")) {
                  edges[i].setType(metaData);
               } else {
                  edges[i].addData(type, metaData);
               }
            }
         }
      }

      br.close();
      fr.close();
   }
}
