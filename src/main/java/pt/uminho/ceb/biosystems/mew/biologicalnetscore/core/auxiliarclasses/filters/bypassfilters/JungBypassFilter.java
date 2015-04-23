package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.bypassfilters;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.ZeroDegreeValuePredicate;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.fixes.FixedVertexPredicateFilter;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class JungBypassFilter implements IBypassFilter {

   public JungNetwork createBypassNetwork(INetwork net, IBypassParameters parameters) {
      SparseMultigraph graph = new SparseMultigraph();
      INode[] nodes = net.getNodes();

      for(int transformedNet = 0; transformedNet < nodes.length; ++transformedNet) {
         JungNode edges = (JungNode)nodes[transformedNet];
         graph.addVertex(edges);
      }

      JungNetwork var10 = new JungNetwork(graph, "Transformed Network");
      IEdge[] var11 = net.getEdges();

      for(int vpf2 = 0; vpf2 < var11.length; ++vpf2) {
         JungEdge edge = (JungEdge)var11[vpf2];
         INode[] pair = net.getConectedNodes(edge);
         parameters.getReplacementEdges(net, var10, edge, pair[0], pair[1]);
      }

      FixedVertexPredicateFilter var12 = new FixedVertexPredicateFilter(new ZeroDegreeValuePredicate(var10.getGraph()));
      var10 = new JungNetwork(var12.transform(var10.getGraph()), "");
      return var10;
   }
}
