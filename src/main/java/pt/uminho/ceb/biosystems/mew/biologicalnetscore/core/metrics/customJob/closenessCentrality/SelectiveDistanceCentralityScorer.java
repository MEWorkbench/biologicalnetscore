package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics.customJob.closenessCentrality;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.algorithms.scoring.DistanceCentralityScorer;
import edu.uci.ics.jung.graph.Hypergraph;

public class SelectiveDistanceCentralityScorer extends DistanceCentralityScorer {

   private String[] edgeTypes = null;
   private ArrayList permitedEdgeTypes = null;
   private ArrayList permitedNodeTypes = null;
   private JungNetwork net = null;


   public SelectiveDistanceCentralityScorer(Hypergraph graph, boolean averaging) {
      super(graph, averaging);
   }

   public SelectiveDistanceCentralityScorer(Hypergraph graph, boolean averaging, String[] edgeTypes, ArrayList permitedEdgeTypes, ArrayList permitedNodeTypes, JungNetwork net) {
      super(graph, averaging);
      this.edgeTypes = edgeTypes;
      this.permitedEdgeTypes = permitedEdgeTypes;
      this.permitedNodeTypes = permitedNodeTypes;
      this.net = net;
   }

   public Double getVertexScore(JungNode v) {
      Double value = (Double)this.output.get(v);
      if(value != null) {
         return value.doubleValue() < 0.0D?null:value;
      } else {
         Double var11;
         if(this.permitedNodeTypes != null) {
            SelectiveBFS var10 = new SelectiveBFS(this.net, v, this.edgeTypes, this.permitedEdgeTypes, this.permitedNodeTypes);
            var11 = Double.valueOf(0.0D);
            JungNode[] var13 = (JungNode[])this.getVertices().toArray(new JungNode[0]);
            byte var15 = 0;

            for(int var16 = 0; var16 < var13.length; ++var16) {
               JungNode w = var13[var16];
               if(!w.equals(v) && (!w.equals(v) || !this.ignore_self_distances)) {
                  int w_distance1 = var10.getDistance(w);
                  if(w_distance1 == -1) {
                     if(!this.ignore_missing) {
                        int var14 = var15 + 1;
                        this.output.put(v, Double.valueOf(-1.0D));
                        return null;
                     }
                  } else {
                     var11 = Double.valueOf(var11.doubleValue() + (double)w_distance1);
                  }
               }
            }

            if(var15 == 0) {
               return null;
            } else {
               value = var11;
               if(this.averaging) {
                  value = Double.valueOf(var11.doubleValue() / (double)var15);
               }

               double var17 = value.doubleValue() == 0.0D?Double.POSITIVE_INFINITY:1.0D / value.doubleValue();
               this.output.put(v, Double.valueOf(var17));
               return Double.valueOf(var17);
            }
         } else {
            HashMap v_distances = new HashMap(this.distance.getDistanceMap(v));
            if(this.ignore_self_distances) {
               v_distances.remove(v);
            }

            if(!this.ignore_missing) {
               int sum = this.getVertexCount() - (this.ignore_self_distances?1:0);
               if(v_distances.size() != sum) {
                  this.output.put(v, Double.valueOf(-1.0D));
                  return null;
               }
            }

            var11 = Double.valueOf(0.0D);
            Iterator v_distancesize = this.getVertices().iterator();

            while(v_distancesize.hasNext()) {
               JungNode score = (JungNode)v_distancesize.next();
               if(!score.equals(v) || !this.ignore_self_distances) {
                  Number w_distance = (Number)v_distances.get(score);
                  if(w_distance == null) {
                     if(!this.ignore_missing) {
                        this.output.put(v, Double.valueOf(-1.0D));
                        return null;
                     }
                  } else {
                     var11 = Double.valueOf(var11.doubleValue() + w_distance.doubleValue());
                  }
               }
            }

            value = var11;
            if(this.averaging) {
               value = Double.valueOf(var11.doubleValue() / (double)v_distances.size());
            }

            double var12 = value.doubleValue() == 0.0D?Double.POSITIVE_INFINITY:1.0D / value.doubleValue();
            this.output.put(v, Double.valueOf(var12));
            return Double.valueOf(var12);
         }
      }
   }

   protected Collection getVertices() {
      if(this.permitedNodeTypes == null) {
         return this.graph.getVertices();
      } else {
         ArrayList res = new ArrayList();
         JungNode[] aux = (JungNode[])this.graph.getVertices().toArray(new JungNode[0]);

         for(int i = 0; i < aux.length; ++i) {
            boolean aceptable = false;

            for(int d = 0; !aceptable && d < this.permitedNodeTypes.size(); ++d) {
               aceptable = aux[i].getType().equals(this.permitedNodeTypes.get(d));
            }

            if(aceptable) {
               res.add(aux[i]);
            }
         }

         return res;
      }
   }

   protected int getVertexCount() {
      if(this.permitedNodeTypes == null) {
         return this.graph.getVertexCount();
      } else {
         int res = 0;
         JungNode[] aux = (JungNode[])this.graph.getVertices().toArray(new JungNode[0]);

         for(int i = 0; i < aux.length; ++i) {
            boolean aceptable = false;

            for(int d = 0; !aceptable && d < this.permitedNodeTypes.size(); ++d) {
               aceptable = aux[i].getType().equals(this.permitedNodeTypes.get(d));
            }

            if(aceptable) {
               ++res;
            }
         }

         return res;
      }
   }
}
