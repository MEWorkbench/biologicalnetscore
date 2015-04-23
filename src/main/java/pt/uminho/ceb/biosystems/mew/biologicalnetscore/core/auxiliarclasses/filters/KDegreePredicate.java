package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.Graph;

public class KDegreePredicate implements Predicate<JungNode>{

   protected int inDegreeK;
   protected int outDegreeK;
   protected int degreeK;
   protected Graph graph;


   public KDegreePredicate(int inDegreeK, int outDegreeK, int degreeK, Graph graph) {
      this.inDegreeK = inDegreeK;
      this.outDegreeK = outDegreeK;
      this.degreeK = degreeK;
      this.graph = graph;
   }

   public boolean evaluate(JungNode n) {
      boolean res = true;
      if(n.getType().equals("compound") && this.inDegreeK > -1) {
         res = this.inDegreeK >= this.graph.inDegree(n);
      }

      if(n.getType().equals("compound") && this.outDegreeK > -1 && res) {
         res = this.outDegreeK >= this.graph.outDegree(n);
      }

      if(n.getType().equals("compound") && this.degreeK > -1 && res) {
         res = this.outDegreeK >= this.graph.inDegree(n) + this.graph.outDegree(n);
      }

      return res;
   }
}
