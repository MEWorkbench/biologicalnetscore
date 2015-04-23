package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class SubNetworkPredicate implements Predicate<JungNode>{

   protected String[] subNetNodes;


   public SubNetworkPredicate(String[] subNetNodes) {
      this.subNetNodes = subNetNodes;
   }

   public boolean evaluate(JungNode n) {
      boolean res = false;

      for(int i = 0; i < this.subNetNodes.length && !res; ++i) {
         String ndata = n.getDb_id() + "@" + n.getType();
         res = this.subNetNodes[i].equals(ndata);
      }

      return res;
   }
}
