package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class MultiplePredicate implements Predicate<JungNode>{

   protected Predicate[] predicates;


   public MultiplePredicate(Predicate[] predicates) {
      this.predicates = predicates;
   }

   public boolean evaluate(JungNode n) {
      boolean res = true;

      for(int i = 0; i < this.predicates.length; ++i) {
         res = res && this.predicates[i].evaluate(n);
      }

      return res;
   }
}
