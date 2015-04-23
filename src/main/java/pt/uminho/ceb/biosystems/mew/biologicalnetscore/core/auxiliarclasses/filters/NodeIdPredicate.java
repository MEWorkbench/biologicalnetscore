package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class NodeIdPredicate implements Predicate<JungNode>{

   protected String[] ids;
   protected String[] types;
   protected String type;


   public NodeIdPredicate(String[] ids, String[] types) {
      this.ids = ids;
      this.types = types;
      this.type = null;
   }

   public NodeIdPredicate(String[] ids, String type) {
      this.ids = ids;
      this.types = null;
      this.type = type;
   }

   public boolean evaluate(JungNode n) {
      boolean res = true;

      for(int i = 0; i < this.ids.length && res; ++i) {
         if(this.types != null) {
            res = !this.ids[i].equals(n.getDb_id()) || !this.types[i].equals(n.getType());
         } else {
            res = !this.ids[i].equals(n.getDb_id()) || !this.type.equals(n.getType());
         }
      }

      return res;
   }
}
