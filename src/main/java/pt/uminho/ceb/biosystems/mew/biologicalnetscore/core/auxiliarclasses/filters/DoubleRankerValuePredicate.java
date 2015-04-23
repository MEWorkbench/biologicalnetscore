package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class DoubleRankerValuePredicate implements Predicate<JungNode> {

   protected double[] values;
   protected INode[] nodes;
   protected double limit;


   public DoubleRankerValuePredicate(double[] values, INode[] nodes, double limit) {
      this.values = values;
      this.nodes = nodes;
      this.limit = limit;
   }

   public boolean evaluate(JungNode n) {
      boolean res = false;
      boolean stop = false;

      for(int i = 0; i < this.nodes.length && !stop; ++i) {
         stop = this.nodes[i].getType().endsWith(n.getType()) && this.nodes[i].getDb_id().endsWith(n.getDb_id());
         if(stop) {
            res = this.values[i] > this.limit;
         }
      }

      return res;
   }
}
