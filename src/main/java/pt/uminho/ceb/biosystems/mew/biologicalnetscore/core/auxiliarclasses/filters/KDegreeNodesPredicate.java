package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import java.util.Arrays;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.SortabelObject;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.Graph;

public class KDegreeNodesPredicate implements Predicate<JungNode>{

   protected JungNode[] k;
   protected String type;


   public KDegreeNodesPredicate(int k, String type, Graph graph) {
      this.k = new JungNode[k];
      this.type = type;
      Object[] tarr = graph.getVertices().toArray();
      JungNode[] nodes = new JungNode[tarr.length];

      for(int sos = 0; sos < tarr.length; ++sos) {
         nodes[sos] = (JungNode)tarr[sos];
      }

      SortabelObject[] var9 = new SortabelObject[nodes.length];

      int i;
      for(i = 0; i < nodes.length; ++i) {
         JungNode n = nodes[i];
         if(this.type.equals("indegree")) {
            var9[i] = new SortabelObject(graph.inDegree(n), n);
         } else if(this.type.equals("outdegree")) {
            var9[i] = new SortabelObject(graph.outDegree(n), n);
         } else if(this.type.equals("degree")) {
            var9[i] = new SortabelObject(graph.inDegree(n) + graph.outDegree(n), n);
         }
      }

      Arrays.sort(var9);

      for(i = 0; i < this.k.length; ++i) {
         this.k[i] = (JungNode)var9[i].getNode();
      }

   }

   public KDegreeNodesPredicate(int k, String type, JungNetwork net) {
      this.k = new JungNode[k];
      this.type = type;
      JungNode[] nodes = net.getNodes();
      SortabelObject[] sos = new SortabelObject[nodes.length];

      int i;
      for(i = 0; i < nodes.length; ++i) {
         JungNode n = nodes[i];
         if(this.type.equals("indegree")) {
            sos[i] = new SortabelObject(net.getGraph().inDegree(n), n);
         } else if(this.type.equals("outdegree")) {
            sos[i] = new SortabelObject(net.getGraph().outDegree(n), n);
         } else if(this.type.equals("degree")) {
            sos[i] = new SortabelObject(net.getGraph().inDegree(n) + net.getGraph().outDegree(n), n);
         }
      }

      Arrays.sort(sos);

      for(i = 0; i < this.k.length; ++i) {
         this.k[i] = (JungNode)sos[i].getNode();
      }

   }

   public boolean evaluate(JungNode n) {
      boolean res = true;

      for(int i = 0; i < this.k.length && res; ++i) {
         res = !n.getDb_id().equals(this.k[i].getDb_id()) || !n.getType().equals(this.k[i].getType());
      }

      return res;
   }
}
