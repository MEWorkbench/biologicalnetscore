package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class NodeBelongsToListPredicate implements Predicate<JungNode>{

   protected String[] ids;
   protected String type;
   protected JungNode[] nodes;


   public NodeBelongsToListPredicate(String[] ids, String type) {
      this.ids = ids;
      this.type = type;
      this.nodes = null;
   }

   public NodeBelongsToListPredicate(JungNode[] nodes) {
      this.ids = null;
      this.type = null;
      this.nodes = nodes;
   }

   public boolean evaluate(JungNode n) {
      boolean res;
      int i;
      if(this.nodes == null) {
         if(!n.getType().equals(this.type)) {
            return true;
         } else {
            res = false;

            for(i = 0; i < this.ids.length && !res; ++i) {
               res = this.ids[i].equals(n.getDb_id());
            }

            return res;
         }
      } else {
         res = false;

         for(i = 0; i < this.nodes.length && !res; ++i) {
            res = n.equals(this.nodes[i]);
         }

         return res;
      }
   }
}
