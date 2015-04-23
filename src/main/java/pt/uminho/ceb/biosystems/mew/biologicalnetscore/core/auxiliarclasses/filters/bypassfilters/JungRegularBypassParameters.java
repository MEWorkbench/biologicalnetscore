package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.bypassfilters;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class JungRegularBypassParameters implements IBypassParameters {

   protected IBypassTransformer[] bypassT;


   public JungRegularBypassParameters(IBypassTransformer[] bypassT) {
      this.bypassT = bypassT;
   }

   public void getReplacementEdges(INetwork orginalNet, INetwork transformedNet, IEdge edge, INode start, INode end) {
      Graph graph = ((JungNetwork)transformedNet).getGraph();
      boolean bypass = false;

      for(int i = 0; !bypass && i < this.bypassT.length; ++i) {
         bypass = this.bypassT[i].transform(orginalNet, transformedNet, edge, start, end);
      }

      if(!bypass) {
         graph.addEdge((JungEdge)edge, (JungNode)start, (JungNode)end, EdgeType.DIRECTED);
      }

   }

   public IBypassTransformer[] getBypassTransformers() {
      return this.bypassT;
   }
}
