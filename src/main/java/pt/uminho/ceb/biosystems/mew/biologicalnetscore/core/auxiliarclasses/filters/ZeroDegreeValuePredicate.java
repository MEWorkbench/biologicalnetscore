package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.Graph;

public class ZeroDegreeValuePredicate implements Predicate<JungNode>{

   protected Graph graph;


   public ZeroDegreeValuePredicate(Graph graph) {
      this.graph = graph;
   }

   public boolean evaluate(JungNode n) {
      int numb = this.graph.inDegree(n) + this.graph.outDegree(n);
      return numb > 0;
   }
}
