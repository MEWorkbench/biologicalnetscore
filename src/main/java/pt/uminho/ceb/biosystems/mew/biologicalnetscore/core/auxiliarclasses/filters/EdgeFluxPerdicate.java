package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;

import org.apache.commons.collections15.Predicate;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.Graph;

public class EdgeFluxPerdicate implements Predicate<JungEdge>{

   protected String[] reactions;
   protected double[] normal;
   protected double[] reversed;
   protected Graph graph;
   protected double[] values;


   public EdgeFluxPerdicate(Graph graph, String[] reactions, double[] normal, double[] reversed) {
      this.reactions = reactions;
      this.normal = normal;
      this.reversed = reversed;
      this.graph = graph;
      this.values = null;
   }

   public EdgeFluxPerdicate(Graph graph, double[] values) {
      this.reactions = null;
      this.normal = null;
      this.reversed = null;
      this.graph = graph;
      this.values = values;
   }

   public boolean evaluate(JungEdge e) {
      boolean res = true;
      boolean stop = false;
      JungNode node = e.getSource(this.graph);
      if(!node.getType().equals("reaction")) {
         node = e.getDest(this.graph);
      }

      if(this.values == null) {
         for(int value = 0; value < this.reactions.length && !stop; ++value) {
            if(this.reactions[value].equals(node.getDb_id())) {
               stop = true;
               if(e.getDatabase_data()[1].equals("True - original flux")) {
                  if(this.normal[value] == 0.0D) {
                     res = false;
                  }
               } else if(e.getDatabase_data()[1].equals("True - generated flux") && this.reversed[value] == 0.0D) {
                  res = false;
               }
            }
         }
      } else {
         double var8 = this.values[node.getIndex()];
         String setdata = e.getData("Set");
         if(setdata != null && (setdata.equals("True - generated flux") || setdata.equals("True - original flux") || setdata.equals("Set 1") || setdata.equals("Set 2"))) {
            if(var8 > 0.0D) {
               if(setdata.equals("True - generated flux") || setdata.equals("Set 2")) {
                  res = false;
               }
            } else if(setdata.equals("True - original flux") || setdata.equals("Set 1")) {
               res = false;
            }
         }
      }

      return res;
   }
}
