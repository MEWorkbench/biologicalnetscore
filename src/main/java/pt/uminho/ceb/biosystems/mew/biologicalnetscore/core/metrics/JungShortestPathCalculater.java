package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics.IShortestPathCalculater;
import edu.uci.ics.jung.algorithms.shortestpath.ShortestPathUtils;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.util.Pair;

public class JungShortestPathCalculater implements Serializable, IShortestPathCalculater {

   private static final long serialVersionUID = 1L;
   protected transient UnweightedShortestPath usp;
   protected JungNetwork net;


   public JungShortestPathCalculater(JungNetwork net) {
      this.net = net;
      this.usp = new UnweightedShortestPath(net.getGraph());
   }

   public Number getDistance(INode n1, INode n2) {
      if(this.usp == null) {
         this.usp = new UnweightedShortestPath(this.net.getGraph());
      }

      return this.usp.getDistance((JungNode)n1, (JungNode)n2);
   }

   public Map getIncomingEdgeMap(INode n) {
      if(this.usp == null) {
         this.usp = new UnweightedShortestPath(this.net.getGraph());
      }

      HashMap res = new HashMap();
      Map temp = this.usp.getIncomingEdgeMap((JungNode)n);
      Object[] keys = temp.keySet().toArray();

      for(int i = 0; i < keys.length; ++i) {
         JungNode key = (JungNode)keys[i];
         JungEdge edge = (JungEdge)temp.get(key);
         res.put(key, edge);
      }

      return res;
   }

   public Object[][] getPath(INode n1, INode n2) {
      if(this.usp == null) {
         this.usp = new UnweightedShortestPath(this.net.getGraph());
      }

      List lis = ShortestPathUtils.getPath(this.net.getGraph(), this.usp, (JungNode)n1, (JungNode)n2);
      INode[] res1 = new INode[lis.size() + 1];
      IEdge[] res2 = new IEdge[lis.size()];
      res1[0] = n1;
      INode former = n1;

      for(int i = 0; i < lis.size(); ++i) {
         Pair p = this.net.getGraph().getEndpoints((JungEdge)lis.get(i));
         INode nodeSec = (INode)p.getSecond();
         if(!nodeSec.getDb_id().equals(former.getDb_id())) {
            res1[i + 1] = (INode)p.getSecond();
            former = (INode)p.getSecond();
         } else {
            res1[i + 1] = (INode)p.getFirst();
            former = (INode)p.getFirst();
         }

         res2[i] = (IEdge)lis.get(i);
      }

      return new Object[][]{res1, res2};
   }

   public int getDiameter() {
      if(this.usp == null) {
         this.usp = new UnweightedShortestPath(this.net.getGraph());
      }

      boolean infinity = false;
      int res = 0;
      JungNode[] ns = this.net.getNodes();

      for(int a = 0; a < ns.length && !infinity; ++a) {
         for(int b = 0; b < ns.length && !infinity; ++b) {
            if(a != b) {
               Number tn = this.usp.getDistance(ns[a], ns[b]);
               if(tn == null) {
                  infinity = true;
                  res = -1;
               } else {
                  int t = tn.intValue();
                  if(t > res) {
                     res = t;
                  }
               }
            }
         }
      }

      return res;
   }

   public void reset() {
      if(this.usp == null) {
         this.usp = new UnweightedShortestPath(this.net.getGraph());
      }

      this.usp.reset();
   }
}
