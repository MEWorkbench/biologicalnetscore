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
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.fileformate.importer.IImporterInterface;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class MBNFInterface implements IImporterInterface, IExporterInterface {

   public void exportData(INetwork net, File f) throws IOException {
      FileWriter fw = new FileWriter(f);
      BufferedWriter bw = new BufferedWriter(fw);
      String[] types = net.getNodeTypesArray();
      INode[] nodes = net.getNodes();

      String[] ns;
      int metaData;
      for(int edges = 0; edges < types.length; ++edges) {
         INode[] i = net.getNodes(types[edges]);
         if(i.length > 0) {
            ns = i[0].getDatabase_datatype();
            bw.write("Node Type\t" + types[edges] + "\t[");
            if(ns.length > 0) {
               bw.write(ns[0]);

               for(metaData = 1; metaData < ns.length; ++metaData) {
                  bw.write("," + ns[metaData]);
               }
            }

            bw.write("]\n");
         }
      }

      types = net.getEdgeTypesArray();
      IEdge[] var12 = net.getEdges();

      int var13;
      for(var13 = 0; var13 < types.length; ++var13) {
         if(net.getEdgesType(types[var13]).length > 0) {
            ns = net.getEdgesType(types[var13])[0].getDatabase_datatype();
            bw.write("Edge Type\t" + types[var13] + "\t[");
            if(ns.length > 0) {
               bw.write(ns[0]);

               for(metaData = 1; metaData < ns.length; ++metaData) {
                  bw.write("," + ns[metaData]);
               }
            }

            bw.write("]\n");
         }
      }

      for(var13 = 0; var13 < nodes.length; ++var13) {
         bw.write((nodes[var13].getType() + "\t" + nodes[var13].toString() + "\t" + nodes[var13].getDb_id() + "\t[").replace(",", "&#44"));
         ns = nodes[var13].getDatabase_data();
         if(ns.length > 0) {
            if(ns[0] == null) {
               bw.write("null");
            } else {
               bw.write(ns[0].replace(",", "&#44"));
            }

            for(metaData = 1; metaData < ns.length; ++metaData) {
               if(ns[metaData] == null) {
                  bw.write(",null");
               } else {
                  bw.write("," + ns[metaData].replace(",", "&#44"));
               }
            }
         }

         bw.write("]\n");
      }

      bw.write("-------------------------------------------------\n");

      for(var13 = 0; var13 < var12.length; ++var13) {
         INode[] var14 = net.getConectedNodes(var12[var13]);
         bw.write((var12[var13].isType() + "\t" + var14[0].getType() + ":" + var14[0].getDb_id() + "\t" + var14[1].getType() + ":" + var14[1].getDb_id() + "\t[").replace(",", "&#44"));
         String[] var15 = var12[var13].getDatabase_data();
         if(var15.length > 0) {
            if(var15[0] == null) {
               bw.write("null");
            } else {
               bw.write(var15[0].replace(",", "&#44"));
            }

            for(int z = 1; z < var15.length; ++z) {
               if(var15[z] == null) {
                  bw.write(",null");
               } else {
                  bw.write("," + var15[z].replace(",", "&#44"));
               }
            }
         }

         bw.write("]\n");
      }

      bw.close();
      fw.close();
   }

   public JungNetwork importData(File f) throws IOException {
      FileReader fr = new FileReader(f);
      BufferedReader br = new BufferedReader(fr);
      ArrayList nodeTypes = new ArrayList();
      ArrayList nodes = new ArrayList();
      ArrayList edgeTypes = new ArrayList();
      byte part = 0;
      int index = 0;
      SparseMultigraph graph = new SparseMultigraph();
      ArrayList nodeMetadaNames = new ArrayList();
      ArrayList edgeMetadaNames = new ArrayList();

      while(br.ready()) {
         String res = br.readLine();
         StringTokenizer st = new StringTokenizer(res, "\t");
         String type;
         String node1;
         String node2;
         JungNode n2;
         String nty1;
         String[] var31;
         int var35;
         StringTokenizer var32;
         switch(part) {
         case 0:
            if(res.startsWith("Node Type")) {
               st.nextToken();
               nodeTypes.add(st.nextToken().replace("&#44", ","));
               type = st.nextToken();
               if(type.equals("[]")) {
                  nodeMetadaNames.add(new String[0]);
                  break;
               }

               type = type.substring(1, type.length() - 1);
               var32 = new StringTokenizer(type, ",");
               var31 = new String[var32.countTokens()];

               for(var35 = 0; var32.hasMoreTokens(); ++var35) {
                  var31[var35] = var32.nextToken().replace("&#44", ",");
               }

               nodeMetadaNames.add(var31);
               break;
            } else {
               part = 1;
            }
         case 1:
            if(res.startsWith("Edge Type")) {
               st.nextToken();
               edgeTypes.add(st.nextToken().replace("&#44", ","));
               type = st.nextToken();
               if(type.equals("[]")) {
                  edgeMetadaNames.add(new String[0]);
                  break;
               }

               type = type.substring(1, type.length() - 1);
               var32 = new StringTokenizer(type, ",");
               var31 = new String[var32.countTokens()];

               for(var35 = 0; var32.hasMoreTokens(); ++var35) {
                  var31[var35] = var32.nextToken().replace("&#44", ",");
               }

               edgeMetadaNames.add(var31);
               break;
            } else {
               part = 2;
            }
         case 2:
            if(res.startsWith("-------------------------------------------------")) {
               part = 3;
               break;
            }

            type = st.nextToken().replace("&#44", ",");
            node1 = st.nextToken().replace("&#44", ",");
            node2 = st.nextToken().replace("&#44", ",");
            String[] var33 = (String[])null;
            String var34 = st.nextToken();
            int var38 = 0;

            for(; var33 == null; ++var38) {
               if(((String)nodeTypes.get(var38)).equals(type)) {
                  var33 = (String[])nodeMetadaNames.get(var38);
               }
            }

            String[] var37 = new String[var33.length];
            if(!var34.equals("[]")) {
               var34 = var34.substring(1, var34.length() - 1);
               StringTokenizer var36 = new StringTokenizer(var34, ",");

               for(int var39 = 0; var36.hasMoreTokens(); ++var39) {
                  nty1 = var36.nextToken();
                  if(nty1.equals("null")) {
                     var37[var39] = null;
                  } else {
                     var37[var39] = nty1.replace("&#44", ",");
                  }
               }
            }

            n2 = new JungNode(type, node1, node2, var33, var37, index);
            graph.addVertex(n2);
            nodes.add(n2);
            ++index;
            break;
         case 3:
            type = st.nextToken().replace("&#44", ",");
            node1 = st.nextToken().replace("&#44", ",");
            node2 = st.nextToken().replace("&#44", ",");
            String metad = st.nextToken();
            String[] metadataName = (String[])null;
            JungNode n1 = null;
            n2 = null;
            StringTokenizer nt = new StringTokenizer(node1, ":");
            nty1 = nt.nextToken();
            String nid1 = nt.nextToken();
            nt = new StringTokenizer(node2, ":");
            String nty2 = nt.nextToken();
            String nid2 = nt.nextToken();

            int metadata;
            for(metadata = 0; n1 == null || n2 == null; ++metadata) {
               JungNode edge = (JungNode)nodes.get(metadata);
               if(edge.getType().equals(nty1) && edge.getDb_id().equals(nid1)) {
                  n1 = edge;
               }

               if(edge.getType().equals(nty2) && edge.getDb_id().equals(nid2)) {
                  n2 = edge;
               }
            }

            for(metadata = 0; metadataName == null; ++metadata) {
               if(((String)edgeTypes.get(metadata)).equals(type)) {
                  metadataName = (String[])edgeMetadaNames.get(metadata);
               }
            }

            String[] var41 = new String[metadataName.length];
            if(!metad.equals("[]")) {
               metad = metad.substring(1, metad.length() - 1);
               StringTokenizer var42 = new StringTokenizer(metad, ",");

               for(int g = 0; var42.hasMoreTokens(); ++g) {
                  String nt2 = var42.nextToken();
                  if(nt2.equals("null")) {
                     var41[g] = null;
                  } else {
                     var41[g] = nt2.replace("&#44", ",");
                  }
               }
            }

            JungEdge var40 = new JungEdge(type, metadataName, var41);
            graph.addEdge(var40, n1, n2, EdgeType.DIRECTED);
         }
      }

      br.close();
      fr.close();
      JungNetwork var30 = new JungNetwork(graph, "", edgeTypes, nodeTypes);
      return var30;
   }
}
