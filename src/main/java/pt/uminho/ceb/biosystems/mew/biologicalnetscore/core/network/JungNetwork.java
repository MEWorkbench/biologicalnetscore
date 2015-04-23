package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.IFilter;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class JungNetwork extends Observable implements Serializable, INetwork {

   private static final long serialVersionUID = 1L;
   private Graph graph;
   private String name;
   private ArrayList edgeTypes;
   private ArrayList nodeTypes;
   protected ArrayList masks;
   protected int mask;


   public JungNetwork(Graph graph, String name, ArrayList edgeTypes, ArrayList nodeTypes) {
      this.graph = graph;
      this.name = name;
      this.edgeTypes = edgeTypes;
      this.nodeTypes = nodeTypes;
      this.mask = -1;
      this.masks = new ArrayList();
   }

   public JungNetwork(Graph graph, String name) {
      this.graph = graph;
      this.name = name;
      this.edgeTypes = new ArrayList();
      this.nodeTypes = new ArrayList();
      Object[] ob = graph.getVertices().toArray();

      int i;
      for(i = 0; i < ob.length; ++i) {
         JungNode n = (JungNode)ob[i];
         if(!this.nodeTypes.contains(n.getType())) {
            this.nodeTypes.add(n.getType());
         }
      }

      ob = graph.getEdges().toArray();

      for(i = 0; i < ob.length; ++i) {
         JungEdge var6 = (JungEdge)ob[i];
         if(!this.edgeTypes.contains(var6.isType())) {
            this.edgeTypes.add(var6.isType());
         }
      }

      this.mask = -1;
      this.masks = new ArrayList();
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.name;
   }

   public JungEdge[] getEdges() {
      Object[] arr = this.graph.getEdges().toArray();
      JungEdge[] edges = new JungEdge[arr.length];

      for(int i = 0; i < arr.length; ++i) {
         edges[i] = (JungEdge)arr[i];
      }

      return edges;
   }

   public Graph getGraph() {
      return this.graph;
   }

   public JungNode[] getNodes() {
      Object[] tarr = this.graph.getVertices().toArray();
      JungNode[] nodes = new JungNode[tarr.length];

      for(int i = 0; i < tarr.length; ++i) {
         nodes[i] = (JungNode)tarr[i];
      }

      return nodes;
   }

   public JungNode getNode(String db_id) {
      JungNode node = null;
      Object[] tarr = this.graph.getVertices().toArray();

      for(int i = 0; i < tarr.length && node == null; ++i) {
         if(((JungNode)tarr[i]).getDb_id().equals(db_id)) {
            node = (JungNode)tarr[i];
         }
      }

      return node;
   }

   public JungNode getNode(String db_id, String type) {
      JungNode node = null;
      Object[] tarr = this.graph.getVertices().toArray();

      for(int i = 0; i < tarr.length && node == null; ++i) {
         if(((JungNode)tarr[i]).getDb_id().equals(db_id) && ((JungNode)tarr[i]).getType().equals(type)) {
            node = (JungNode)tarr[i];
         }
      }

      return node;
   }

   public JungNode[] getNodes(String type) {
      Object[] tarr = this.graph.getVertices().toArray();
      ArrayList tempnodes = new ArrayList();

      for(int nodes = 0; nodes < tarr.length; ++nodes) {
         if(((JungNode)tarr[nodes]).getType().equals(type)) {
            tempnodes.add((JungNode)tarr[nodes]);
         }
      }

      JungNode[] var6 = new JungNode[tempnodes.size()];

      for(int i = 0; i < tempnodes.size(); ++i) {
         var6[i] = (JungNode)tempnodes.get(i);
      }

      return var6;
   }

   public String[] getEdgeTypesArray() {
      String[] res = new String[this.edgeTypes.size()];

      for(int i = 0; i < this.edgeTypes.size(); ++i) {
         res[i] = (String)this.edgeTypes.get(i);
      }

      return res;
   }

   public String[] getNodeTypesArray() {
      String[] nodeTypes = new String[this.nodeTypes.size()];

      for(int i = 0; i < this.nodeTypes.size(); ++i) {
         nodeTypes[i] = (String)this.nodeTypes.get(i);
      }

      return nodeTypes;
   }

   public QRTable getValues() {
      ArrayList columnsNames = new ArrayList();
      columnsNames.add("Type");
      columnsNames.add("From name");
      columnsNames.add("From id");
      columnsNames.add("To name");
      columnsNames.add("To id");
      QRTable qrt = new QRTable(columnsNames, this.name);
      Object[] edges = this.graph.getEdges().toArray();

      for(int i = 0; i < edges.length; ++i) {
         ArrayList ql = new ArrayList();
         JungEdge nde = (JungEdge)edges[i];
         ql.add(nde.isType());
         Pair p = this.graph.getEndpoints(nde);
         ql.add(((JungNode)p.getFirst()).toString());
         ql.add(((JungNode)p.getFirst()).getDb_id());
         ql.add(((JungNode)p.getSecond()).toString());
         ql.add(((JungNode)p.getSecond()).getDb_id());
         qrt.addLine(ql);
      }

      return qrt;
   }

   public JungEdge[] getEdgesType(String type) {
      ArrayList edg = new ArrayList();
      Object[] edges = this.graph.getEdges().toArray();

      for(int res = 0; res < edges.length; ++res) {
         JungEdge i = (JungEdge)edges[res];
         if(i.isType().equals(type)) {
            edg.add(i);
         }
      }

      JungEdge[] var6 = new JungEdge[edg.size()];

      for(int var7 = 0; var7 < edg.size(); ++var7) {
         var6[var7] = (JungEdge)edg.get(var7);
      }

      return var6;
   }

   public QRTable getTypeValues(String type) {
      boolean first = true;
      ArrayList columnsNames = new ArrayList();
      columnsNames.add("Type");
      columnsNames.add("From type");
      columnsNames.add("From name");
      columnsNames.add("From id");
      columnsNames.add("To type");
      columnsNames.add("To name");
      columnsNames.add("To id");
      QRTable qrt = null;
      Object[] edges = this.graph.getEdges().toArray();

      for(int i = 0; i < edges.length; ++i) {
         ArrayList ql = new ArrayList();
         JungEdge nde = (JungEdge)edges[i];
         if(nde.isType().equals(type)) {
            if(first) {
               String[] p = nde.getDatabase_datatype();

               for(int dts = 0; dts < p.length; ++dts) {
                  columnsNames.add(p[dts]);
               }

               first = false;
               qrt = new QRTable(columnsNames, this.name);
            }

            Pair var12 = this.graph.getEndpoints(nde);
            ql.add(nde.isType());
            ql.add(((JungNode)var12.getFirst()).getType());
            ql.add(((JungNode)var12.getFirst()).toString());
            ql.add(((JungNode)var12.getFirst()).getDb_id());
            ql.add(((JungNode)var12.getSecond()).getType());
            ql.add(((JungNode)var12.getSecond()).toString());
            ql.add(((JungNode)var12.getSecond()).getDb_id());
            String[] var13 = nde.getDatabase_data();

            for(int x = 0; x < var13.length; ++x) {
               ql.add(var13[x]);
            }

            qrt.addLine(ql);
         }
      }

      return qrt;
   }

   public QRTable getTypeValues() {
      boolean first = true;
      ArrayList columnsNames = new ArrayList();
      columnsNames.add("Type");
      columnsNames.add("From name");
      columnsNames.add("From id");
      columnsNames.add("To name");
      columnsNames.add("To id");
      columnsNames.add("Directed");
      QRTable qrt = null;
      Object[] edges = this.graph.getEdges().toArray();

      for(int i = 0; i < edges.length; ++i) {
         ArrayList ql = new ArrayList();
         JungEdge nde = (JungEdge)edges[i];
         if(first) {
            String[] p = nde.getDatabase_datatype();

            for(int dts = 0; dts < p.length; ++dts) {
               columnsNames.add(p[dts]);
            }

            first = false;
            qrt = new QRTable(columnsNames, this.name);
         }

         Pair var11 = this.graph.getEndpoints(nde);
         ql.add(nde.isType());
         ql.add(((JungNode)var11.getFirst()).toString());
         ql.add(((JungNode)var11.getFirst()).getDb_id());
         ql.add(((JungNode)var11.getSecond()).toString());
         ql.add(((JungNode)var11.getSecond()).getDb_id());
         ql.add("" + (this.graph.getEdgeType(nde) == EdgeType.DIRECTED));
         String[] var12 = nde.getDatabase_data();

         for(int x = 0; x < var12.length; ++x) {
            ql.add(var12[x]);
         }

         qrt.addLine(ql);
      }

      return qrt;
   }

   public QRTable getNodesValues() {
      ArrayList columnsNames = new ArrayList();
      columnsNames.add("Type");
      columnsNames.add("Name");
      columnsNames.add("Id");
      columnsNames.add("Indegree");
      columnsNames.add("Outdegree");
      QRTable qrt = new QRTable(columnsNames, this.name);
      Object[] nodes = this.graph.getVertices().toArray();

      for(int i = 0; i < nodes.length; ++i) {
         if(this.mask == -1 || ((boolean[])this.masks.get(this.mask))[i]) {
            JungNode n = (JungNode)nodes[i];
            ArrayList ql = new ArrayList();
            ql.add(n.getType());
            ql.add(n.toString());
            ql.add(n.getDb_id());
            ql.add("" + this.graph.inDegree(n));
            ql.add("" + this.graph.outDegree(n));
            qrt.addLine(ql);
         }
      }

      return qrt;
   }

   public QRTable getNodesData() {
      ArrayList columnsNames = new ArrayList();
      columnsNames.add("Type");
      columnsNames.add("Name");
      columnsNames.add("Id");
      QRTable qrt = new QRTable(columnsNames, this.name);
      Object[] nodes = this.graph.getVertices().toArray();

      for(int i = 0; i < nodes.length; ++i) {
         if(this.mask == -1 || ((boolean[])this.masks.get(this.mask))[i]) {
            JungNode n = (JungNode)nodes[i];
            ArrayList ql = new ArrayList();
            ql.add(n.getType());
            ql.add(n.toString());
            ql.add(n.getDb_id());
            qrt.addLine(ql);
         }
      }

      return qrt;
   }

   public QRTable getNodeTypeValues(String type) {
      boolean first = true;
      ArrayList columnsNames = new ArrayList();
      columnsNames.add("Type");
      columnsNames.add("Name");
      columnsNames.add("Id");
      columnsNames.add("Indegree");
      columnsNames.add("Outdegree");
      QRTable qrt = new QRTable(columnsNames, this.name);
      Object[] nodes = this.graph.getVertices().toArray();

      for(int i = 0; i < nodes.length; ++i) {
         JungNode n = (JungNode)nodes[i];
         if(n.getType().equals(type) && (this.mask == -1 || ((boolean[])this.masks.get(this.mask))[i])) {
            if(first) {
               String[] ql = n.getDatabase_datatype();

               for(int dts = 0; dts < ql.length; ++dts) {
                  columnsNames.add(ql[dts]);
               }

               first = false;
               qrt = new QRTable(columnsNames, this.name);
            }

            ArrayList var11 = new ArrayList();
            var11.add(n.getType());
            var11.add(n.toString());
            var11.add(n.getDb_id());
            var11.add("" + this.graph.inDegree(n));
            var11.add("" + this.graph.outDegree(n));
            String[] var12 = n.getDatabase_data();

            for(int x = 0; x < var12.length; ++x) {
               var11.add(var12[x]);
            }

            qrt.addLine(var11);
         }
      }

      return qrt;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int countType(String type) {
      int res = 0;
      Object[] nodes = this.graph.getVertices().toArray();

      for(int i = 0; i < nodes.length; ++i) {
         JungNode n = (JungNode)nodes[i];
         if(n.getType().equals(type)) {
            ++res;
         }
      }

      return res;
   }

   public JungNetwork getSubNetwork(IFilter filter) {
      return (JungNetwork)filter.filterNetwork(this);
   }

   public ArrayList getEdgeTypes() {
      return this.edgeTypes;
   }

   public String[] getNodeTypes() {
      String[] res = (String[])this.nodeTypes.toArray(new String[0]);
      return res;
   }

   public ArrayList getNodeTypesList() {
      return this.nodeTypes;
   }

   public ArrayList getMasks() {
      return this.masks;
   }

   public int degree(INode n) {
      return this.graph.degree((JungNode)n);
   }

   public int inDegree(INode n) {
      return this.graph.inDegree((JungNode)n);
   }

   public int outDegree(INode n) {
      return this.graph.outDegree((JungNode)n);
   }

   public INode[] getConectedNodes(IEdge e) {
      Pair p = this.graph.getEndpoints((JungEdge)e);
      return new INode[]{(INode)p.getFirst(), (INode)p.getSecond()};
   }

   public JungEdge[] getInEdges(INode n) {
      Collection j = this.graph.getInEdges((JungNode)n);
      return j == null?new JungEdge[0]:(JungEdge[])j.toArray(new JungEdge[0]);
   }

   public JungEdge[] getOutEdges(INode n) {
      Collection j = this.graph.getOutEdges((JungNode)n);
      return j == null?new JungEdge[0]:(JungEdge[])j.toArray(new JungEdge[0]);
   }

   public boolean isDirected(JungEdge e) {
      return this.graph.getDest(e) != null;
   }

   public INode[] getNeighbours(INode n) {
      ArrayList tres = new ArrayList();
      JungEdge[] e = this.getOutEdges(n);

      int res;
      for(res = 0; res < e.length; ++res) {
         tres.add(this.getConectedNodes(e[res])[1]);
      }

      e = this.getInEdges(n);

      for(res = 0; res < e.length; ++res) {
         tres.add(this.getConectedNodes(e[res])[0]);
      }

      INode[] var6 = new INode[tres.size()];

      for(int i = 0; i < tres.size(); ++i) {
         var6[i] = (INode)tres.get(i);
      }

      return var6;
   }

   public JungEdge getEdge(String type, JungNode start, JungNode end) {
      JungEdge res = null;
      Object[] edges = this.graph.getEdges().toArray();

      for(int i = 0; i < edges.length && res == null; ++i) {
         JungEdge edge = (JungEdge)edges[i];
         if(edge.isType().equals(type)) {
            INode[] es = this.getConectedNodes(edge);
            if(es[0].equals(start) && es[1].equals(end)) {
               res = edge;
            }
         }
      }

      return res;
   }

   public void addNode(INode n) {
      if(n instanceof JungNode) {
         this.graph.addVertex((JungNode)n);
      }

   }

   public void removeNode(INode n) {
      if(n instanceof JungNode) {
         this.graph.removeVertex((JungNode)n);
      }

   }

   public void addEdge(IEdge edge, INode v1, INode v2) {
      if(edge instanceof JungEdge && v1 instanceof JungNode && v2 instanceof JungNode) {
         JungEdge[] es = (JungEdge[])this.graph.getOutEdges((JungNode)v1).toArray(new JungEdge[0]);
         boolean found = false;
         this.graph.addEdge((JungEdge)edge, (JungNode)v1, (JungNode)v2, EdgeType.DIRECTED);
      }

   }

   public JungNetwork clone() {
      SparseMultigraph graph = new SparseMultigraph();
      JungNode[] nodes = this.getNodes();
      JungNode[] clone_nodes = new JungNode[nodes.length];

      int a;
      for(a = 0; a < nodes.length; ++a) {
         clone_nodes[a] = nodes[a].clone();
         graph.addVertex(clone_nodes[a]);
      }

      for(a = 0; a < nodes.length; ++a) {
         JungEdge[] edges = this.getOutEdges(nodes[a]);

         for(int b = 0; b < edges.length; ++b) {
            INode sec = this.getConectedNodes(edges[b])[1];
            int d = this.position(sec, nodes);
            graph.addEdge(edges[b].clone(), clone_nodes[a], clone_nodes[d], EdgeType.DIRECTED);
         }
      }

      return new JungNetwork(graph, this.name, this.edgeTypes, this.nodeTypes);
   }

   protected int position(INode node, INode[] nodes) {
      int res = -1;

      for(int i = 0; res == -1 && i < nodes.length; ++i) {
         if(nodes[i].equals(node)) {
            res = i;
         }
      }

      return res;
   }

   public void removeEdge(IEdge e) {
      if(e instanceof JungEdge) {
         this.graph.removeEdge((JungEdge)e);
      }

   }

   public void setEdgeTypesArray(String[] etypes) {
      this.edgeTypes = new ArrayList();

      for(int i = 0; i < etypes.length; ++i) {
         this.edgeTypes.add(etypes[i]);
      }

   }

   public void setNodeTypesArray(String[] ntypes) {
      this.nodeTypes = new ArrayList();

      for(int i = 0; i < ntypes.length; ++i) {
         this.nodeTypes.add(ntypes[i]);
      }

   }
}
