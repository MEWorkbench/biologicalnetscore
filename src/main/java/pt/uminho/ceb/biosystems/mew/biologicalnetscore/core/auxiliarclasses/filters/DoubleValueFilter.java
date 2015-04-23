package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class DoubleValueFilter extends AbstractFilter {

   protected JungNode[] nodes;
   protected double[] filter;
   protected String type;


   public DoubleValueFilter(JungNetwork net, double[] filter, String type, double value) {
      this.nodes = net.getNodes();
      this.filter = filter;
      this.type = type;
      super.maskSize = this.nodes.length;
      super.comparisons = new AbstractComparison[]{new DoubleValueBiggerThanComparison(value)};
   }

   protected void giveData(int row) {
      int index = this.nodes[row].getIndex();
      String type = this.nodes[row].getType();
      if(type.equals(this.type)) {
         ((DoubleValueBiggerThanComparison)super.comparisons[0]).inputData(new Double(this.filter[index]));
      } else {
         ((DoubleValueBiggerThanComparison)super.comparisons[0]).forceTrue(true);
      }

   }
}
