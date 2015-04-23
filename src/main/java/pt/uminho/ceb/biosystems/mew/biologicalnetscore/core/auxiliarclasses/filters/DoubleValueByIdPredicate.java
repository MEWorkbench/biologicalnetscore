package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class DoubleValueByIdPredicate implements Predicate<JungNode> {

   protected String[] ids;
   protected double[] values;
   protected double limit;
   protected boolean useModuleInSimulationValue;


   public DoubleValueByIdPredicate(String[] ids, double[] values, double limit, boolean useModuleInSimulationValue) {
      this.ids = ids;
      this.values = values;
      this.limit = limit;
      this.useModuleInSimulationValue = useModuleInSimulationValue;
   }

   public boolean evaluate(JungNode n) {
      boolean res = true;
      if(n.getType().equals("reaction")) {
         boolean keep = false;
         int ga = 0;

         for(int value = 0; value < this.ids.length && !keep; ++value) {
            keep = this.ids[value].equals(n.getDb_id());
            if(keep) {
               ga = value;
            }
         }

         if(keep) {
            double var7 = this.values[ga];
            if(this.useModuleInSimulationValue && var7 < 0.0D) {
               var7 = 0.0D - var7;
            }

            res = var7 > this.limit;
         }
      }

      return res;
   }
}
