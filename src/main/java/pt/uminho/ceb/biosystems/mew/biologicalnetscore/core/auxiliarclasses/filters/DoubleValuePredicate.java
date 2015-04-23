package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class DoubleValuePredicate implements Predicate<JungNode>{

   protected double[] values;
   protected double limit;
   protected boolean useModuleInSimulationValue;


   public DoubleValuePredicate(double[] values, double limit, boolean useModuleInSimulationValue) {
      this.values = values;
      this.limit = limit;
      this.useModuleInSimulationValue = useModuleInSimulationValue;
   }

   public boolean evaluate(JungNode n) {
      boolean res = true;
      if(n.getType().equals("reaction")) {
         double value = this.values[n.getIndex()];
         if(this.useModuleInSimulationValue && value < 0.0D) {
            value = 0.0D - value;
         }

         res = value > this.limit;
      }

      return res;
   }
}
