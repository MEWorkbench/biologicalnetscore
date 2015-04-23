package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class MaskPredicate implements Predicate<JungNode>{

   protected boolean[] mask;


   public MaskPredicate(boolean[] mask) {
      this.mask = mask;
   }

   public boolean evaluate(JungNode arg0) {
      return this.mask[arg0.getIndex()];
   }
}
