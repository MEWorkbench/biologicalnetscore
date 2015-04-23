package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics.customJob.closenessCentrality;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import edu.uci.ics.jung.graph.Hypergraph;

public class SelectiveClosenessCentrality extends SelectiveDistanceCentralityScorer {

   public SelectiveClosenessCentrality(Hypergraph graph) {
      super(graph, true);
   }

   public SelectiveClosenessCentrality(Hypergraph graph, String[] edgeTypes, ArrayList permitedEdgeTypes, ArrayList permitedNodeTypes, JungNetwork net) {
      super(graph, true, edgeTypes, permitedEdgeTypes, permitedNodeTypes, net);
   }
}
