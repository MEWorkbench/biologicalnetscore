package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class TypePredicate implements Predicate<JungNode>{

   protected String reactionType;
   protected String metaboliteType;


   public TypePredicate(String reactionType, String metaboliteType) {
      this.reactionType = reactionType;
      this.metaboliteType = metaboliteType;
   }

   public boolean evaluate(JungNode n) {
      boolean res = true;
      String type;
      if(this.reactionType != null && n.getType().equals("reaction")) {
         type = n.getDatabase_data()[3];
         res = !this.reactionType.equals(type);
      } else if(this.metaboliteType != null && n.getType().equals("compound")) {
         type = n.getDatabase_data()[1];
         res = !this.metaboliteType.equals(type);
      }

      return res;
   }
}
