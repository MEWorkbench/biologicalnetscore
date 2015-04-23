package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import java.util.List;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;

public class KnockOutFilter extends AbstractFilter {

   protected JungNode[] nodes;
   protected double[] filter;


   public KnockOutFilter(JungNetwork net, String type, List knocks) {
      this.nodes = net.getNodes();
      super.maskSize = this.nodes.length;
      String[] k = new String[knocks.size()];

      for(int i = 0; i < knocks.size(); ++i) {
         k[i] = (String)knocks.get(i);
      }

      super.comparisons = new AbstractComparison[]{new KnockOutComparison(type, k)};
   }

   public KnockOutFilter(JungNetwork net, String type, String[] knockOuts) {
      this.nodes = net.getNodes();
      super.maskSize = this.nodes.length;
      super.comparisons = new AbstractComparison[]{new KnockOutComparison(type, knockOuts)};
   }

   protected void giveData(int row) {
      String id = this.nodes[row].getDb_id();
      String type = this.nodes[row].getType();
      ((KnockOutComparison)super.comparisons[0]).inputData(new String[]{type, id});
   }
}
