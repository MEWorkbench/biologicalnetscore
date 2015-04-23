package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import edu.uci.ics.jung.graph.Graph;

public class EdgeSandDPredicate implements Predicate<JungEdge>{

   protected String[] source;
   protected String[] destiny;
   protected Graph graph;


   public EdgeSandDPredicate(String[] source, String[] destiny, Graph graph) {
      this.source = source;
      this.destiny = destiny;
      this.graph = graph;
   }

   public boolean evaluate(JungEdge e) {
      boolean res = true;

      for(int i = 0; i < this.source.length && res; ++i) {
         res = !this.source[i].equals(e.getSource(this.graph).getDb_id()) || !this.destiny[i].equals(e.getDest(this.graph).getDb_id());
      }

      return res;
   }
}
