package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics.customJob.betweennessCentrality;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.algorithms.importance.AbstractRanker;
import edu.uci.ics.jung.algorithms.importance.Ranking;
import edu.uci.ics.jung.graph.Graph;

public abstract class SelectiveAbstractRanker extends AbstractRanker {

   private String[] nodeTypes;
   private String[] edgeTypes;
   private boolean mRemoveRankScoresOnFinalizeDux;
   private List mRankings;


   protected void initialize(Graph graph, boolean isNodeRanker, boolean isEdgeRanker) {
      super.initialize(graph, isNodeRanker, isEdgeRanker);
      this.nodeTypes = null;
      this.edgeTypes = null;
      this.mRemoveRankScoresOnFinalizeDux = true;
   }

   protected void initialize(Graph graph, boolean isNodeRanker, boolean isEdgeRanker, String[] nodeTypes, String[] edgeTypes) {
      super.initialize(graph, isNodeRanker, isEdgeRanker);
      this.nodeTypes = nodeTypes;
      this.edgeTypes = edgeTypes;
   }

   protected Collection getVertices() {
      if(this.nodeTypes == null) {
         return super.getVertices();
      } else {
         Graph mGraph = super.getGraph();
         ArrayList res = new ArrayList();
         JungNode[] aux = (JungNode[])mGraph.getVertices().toArray(new JungNode[0]);

         for(int i = 0; i < aux.length; ++i) {
            boolean aceptable = false;

            for(int d = 0; !aceptable && d < this.nodeTypes.length; ++d) {
               aceptable = aux[i].getType().equals(this.nodeTypes[d]);
            }

            if(aceptable) {
               res.add(aux[i]);
            }
         }

         return res;
      }
   }

   protected int getVertexCount() {
      if(this.nodeTypes == null) {
         return super.getVertexCount();
      } else {
         Graph mGraph = super.getGraph();
         int res = 0;
         JungNode[] aux = (JungNode[])mGraph.getVertices().toArray(new JungNode[0]);

         for(int i = 0; i < aux.length; ++i) {
            boolean aceptable = false;

            for(int d = 0; !aceptable && d < this.nodeTypes.length; ++d) {
               aceptable = aux[i].getType().equals(this.nodeTypes[d]);
            }

            if(aceptable) {
               ++res;
            }
         }

         return res;
      }
   }

   public void setRemoveRankScoresOnFinalize(boolean removeRankScoresOnFinalizeDux) {
      this.mRemoveRankScoresOnFinalizeDux = removeRankScoresOnFinalizeDux;
   }

   protected void finalizeIterations() {
      ArrayList sortedRankings = new ArrayList();
      int id = 1;
      Iterator var4;
      Ranking ranking;
      if(super.isRankingNodes()) {
         var4 = this.getVertices().iterator();

         while(var4.hasNext()) {
            JungNode currentEdge = (JungNode)var4.next();
            ranking = new Ranking(id, this.getVertexRankScore(currentEdge), currentEdge);
            sortedRankings.add(ranking);
            if(this.mRemoveRankScoresOnFinalizeDux) {
               ((Map)this.vertexRankScores.get(this.getRankScoreKey())).remove(currentEdge);
            }

            ++id;
            this.onFinalize(currentEdge);
         }
      }

      if(super.isRankingEdges()) {
         var4 = this.getEdges().iterator();

         while(var4.hasNext()) {
            JungEdge var6 = (JungEdge)var4.next();
            ranking = new Ranking(id, this.getEdgeRankScore(var6), var6);
            sortedRankings.add(ranking);
            if(this.mRemoveRankScoresOnFinalizeDux) {
               ((Map)this.edgeRankScores.get(this.getRankScoreKey())).remove(var6);
            }

            ++id;
            this.onFinalize(var6);
         }
      }

      this.mRankings = sortedRankings;
      Collections.sort(this.mRankings);
   }

   protected void assignDefaultEdgeTransitionWeights() {
      Iterator var2 = this.getVertices().iterator();

      while(var2.hasNext()) {
         JungNode currentVertex = (JungNode)var2.next();
         Collection outgoingEdges = this.getOutEdges(currentVertex);
         double numOutEdges = (double)outgoingEdges.size();
         Iterator var7 = outgoingEdges.iterator();

         while(var7.hasNext()) {
            JungEdge currentEdge = (JungEdge)var7.next();
            this.setEdgeWeight(currentEdge, 1.0D / numOutEdges);
         }
      }

   }

   protected void normalizeEdgeTransitionWeights() {
      Iterator var2 = this.getVertices().iterator();

      while(var2.hasNext()) {
         JungNode currentVertex = (JungNode)var2.next();
         Collection outgoingEdges = this.getOutEdges(currentVertex);
         double totalEdgeWeight = 0.0D;

         JungEdge currentEdge;
         Iterator var7;
         for(var7 = outgoingEdges.iterator(); var7.hasNext(); totalEdgeWeight += this.getEdgeWeight(currentEdge)) {
            currentEdge = (JungEdge)var7.next();
         }

         var7 = outgoingEdges.iterator();

         while(var7.hasNext()) {
            currentEdge = (JungEdge)var7.next();
            this.setEdgeWeight(currentEdge, this.getEdgeWeight(currentEdge) / totalEdgeWeight);
         }
      }

   }

   protected Collection getEdges() {
      Graph mGraph = super.getGraph();
      if(this.edgeTypes == null) {
         return mGraph.getEdges();
      } else {
         ArrayList res = new ArrayList();
         JungEdge[] aux = (JungEdge[])mGraph.getEdges().toArray(new JungEdge[0]);

         for(int i = 0; i < aux.length; ++i) {
            boolean aceptable = false;

            for(int d = 0; !aceptable && d < this.edgeTypes.length; ++d) {
               aceptable = aux[i].isType().equals(this.edgeTypes[d]);
            }

            if(aceptable) {
               res.add(aux[i]);
            }
         }

         return res;
      }
   }

   protected Collection getOutEdges(JungNode currentVertex) {
      Graph mGraph = super.getGraph();
      if(this.edgeTypes == null) {
         return mGraph.getOutEdges(currentVertex);
      } else {
         ArrayList res = new ArrayList();
         JungEdge[] aux = (JungEdge[])mGraph.getOutEdges(currentVertex).toArray(new JungEdge[0]);

         for(int i = 0; i < aux.length; ++i) {
            boolean aceptable = false;

            for(int d = 0; !aceptable && d < this.edgeTypes.length; ++d) {
               aceptable = aux[i].isType().equals(this.edgeTypes[d]);
            }

            if(aceptable) {
               res.add(aux[i]);
            }
         }

         return res;
      }
   }

   protected Collection getSuccessors(JungNode v) {
      if(this.nodeTypes == null) {
         return super.getGraph().getSuccessors(v);
      } else {
         ArrayList res = new ArrayList();
         JungEdge[] es = (JungEdge[])super.getGraph().getOutEdges(v).toArray(new JungEdge[0]);

         for(int i = 0; i < es.length; ++i) {
            if(this.aceptable(this.edgeTypes, es[i].isType())) {
               JungNode n = (JungNode)super.getGraph().getEndpoints(es[i]).getSecond();
               if(this.aceptable(this.nodeTypes, n.getType())) {
                  res.add(n);
               }
            }
         }

         return res;
      }
   }

   protected boolean aceptable(String[] types, String type) {
      boolean aceptable = false;

      for(int i = 0; !aceptable && i < types.length; ++i) {
         aceptable = types[i].equals(type);
      }

      return aceptable;
   }

   protected JungEdge findEdge(JungNode v1, JungNode v2) {
      if(this.nodeTypes == null) {
         return (JungEdge)super.getGraph().findEdge(v1, v2);
      } else {
         JungEdge res = null;
         if(super.getGraph().containsVertex(v1) && super.getGraph().containsVertex(v2)) {
            JungEdge[] es = (JungEdge[])super.getGraph().getOutEdges(v1).toArray(new JungEdge[0]);

            for(int i = 0; res == null && i < es.length; ++i) {
               if(this.aceptable(this.edgeTypes, es[i].isType())) {
                  JungNode n = (JungNode)super.getGraph().getEndpoints(es[i]).getSecond();
                  if(n.equals(v2)) {
                     res = es[i];
                  }
               }
            }

            return res;
         } else {
            return null;
         }
      }
   }
}
