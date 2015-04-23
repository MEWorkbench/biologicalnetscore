package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class NodeInSubNetListPredicate implements Predicate<JungNode>{

   protected String[] subNet;


   public NodeInSubNetListPredicate(String[] subNet) {
      this.subNet = subNet;
   }

   public boolean evaluate(JungNode n) {
      boolean res = false;

      for(int i = 0; i < this.subNet.length && !res; ++i) {
         res = this.subNet[i].equals(n.getDb_id() + "@" + n.getType());
      }

      return res;
   }
}
