package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class DoubleValueAndTypePredicate implements Predicate<JungNode> {

   protected double[] values;
   protected double limit;
   protected String reactionType;
   protected String metaboliteType;


   public DoubleValueAndTypePredicate(double[] values, double limit, String reactionType, String metaboliteType) {
      this.values = values;
      this.limit = limit;
      this.reactionType = reactionType;
      this.metaboliteType = metaboliteType;
   }

   public boolean evaluate(JungNode n) {
      boolean res = true;
      if(n.getType().equals("reaction")) {
         double type = this.values[n.getIndex()];
         res = type > this.limit;
      }

      String type1;
      if(res && this.reactionType != null && n.getType().equals("reaction")) {
         type1 = n.getDatabase_data()[3];
         res = !this.reactionType.equals(type1);
      } else if(this.metaboliteType != null && n.getType().equals("compound")) {
         type1 = n.getDatabase_data()[1];
         res = !this.metaboliteType.equals(type1);
      }

      return res;
   }
}
