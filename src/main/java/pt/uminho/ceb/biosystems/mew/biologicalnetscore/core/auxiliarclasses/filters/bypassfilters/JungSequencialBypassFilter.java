package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.bypassfilters;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.ZeroDegreeValuePredicate;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.fixes.FixedVertexPredicateFilter;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;

public class JungSequencialBypassFilter implements IBypassFilter {

   public JungNetwork createBypassNetwork(INetwork net, IBypassParameters parameters) {
      IBypassTransformer[] trans = parameters.getBypassTransformers();
      JungNetwork currentNet = (JungNetwork)net;

      for(int vpf2 = 0; vpf2 < trans.length; ++vpf2) {
         JungRegularBypassParameters sequParameters = new JungRegularBypassParameters(new IBypassTransformer[]{trans[vpf2]});
         JungBypassFilter jbf = new JungBypassFilter();
         currentNet = jbf.createBypassNetwork(currentNet, sequParameters);
      }

      FixedVertexPredicateFilter var8 = new FixedVertexPredicateFilter(new ZeroDegreeValuePredicate(currentNet.getGraph()));
      currentNet = new JungNetwork(var8.transform(currentNet.getGraph()), "");
      return currentNet;
   }
}
