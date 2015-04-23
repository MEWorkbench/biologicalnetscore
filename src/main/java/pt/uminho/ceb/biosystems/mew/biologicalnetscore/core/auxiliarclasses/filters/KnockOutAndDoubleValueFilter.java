package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import java.util.List;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class KnockOutAndDoubleValueFilter extends AbstractFilter {

   protected JungNode[] nodes;
   protected double[] filter;
   protected String type;


   public KnockOutAndDoubleValueFilter(JungNetwork net, double[] filter, String type, double value, List knocks) {
      this.nodes = net.getNodes();
      this.filter = filter;
      this.type = type;
      super.maskSize = this.nodes.length;
      String[] k = new String[knocks.size()];

      for(int i = 0; i < knocks.size(); ++i) {
         k[i] = (String)knocks.get(i);
      }

      super.comparisons = new AbstractComparison[]{new DoubleValueBiggerThanComparison(value), new KnockOutComparison(type, k)};
   }

   protected void giveData(int row) {
      int index = this.nodes[row].getIndex();
      String type = this.nodes[row].getType();
      String id = this.nodes[row].getDb_id();
      ((KnockOutComparison)super.comparisons[1]).inputData(new String[]{type, id});
      if(type.equals(this.type)) {
         ((DoubleValueBiggerThanComparison)super.comparisons[0]).inputData(new Double(this.filter[index]));
      } else {
         ((DoubleValueBiggerThanComparison)super.comparisons[0]).forceTrue(true);
      }

   }
}
