package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;

public class MultipleEdgePredicate implements Predicate<JungEdge>{

   protected Predicate[] predicates;


   public MultipleEdgePredicate(Predicate[] predicates) {
      this.predicates = predicates;
   }

   public boolean evaluate(JungEdge n) {
      boolean res = true;

      for(int i = 0; i < this.predicates.length; ++i) {
         res = res && this.predicates[i].evaluate(n);
      }

      return res;
   }
}
