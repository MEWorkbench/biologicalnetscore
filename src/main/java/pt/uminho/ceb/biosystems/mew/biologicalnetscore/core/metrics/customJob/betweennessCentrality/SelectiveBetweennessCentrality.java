package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics.customJob.betweennessCentrality;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.collections15.buffer.UnboundedFifoBuffer;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

public class SelectiveBetweennessCentrality extends SelectiveAbstractRanker {

   public static final String CENTRALITY = "centrality.BetweennessCentrality";


   public SelectiveBetweennessCentrality(Graph g) {
      this.initialize(g, true, true);
   }

   public SelectiveBetweennessCentrality(Graph g, boolean rankNodes) {
      this.initialize(g, rankNodes, true);
   }

   public SelectiveBetweennessCentrality(Graph g, boolean rankNodes, boolean rankEdges) {
      this.initialize(g, rankNodes, rankEdges);
   }

   public SelectiveBetweennessCentrality(Graph g, String[] nodeTypes, String[] edgeTypes) {
      this.initialize(g, true, true, nodeTypes, edgeTypes);
   }

   public SelectiveBetweennessCentrality(Graph g, boolean rankNodes, String[] nodeTypes, String[] edgeTypes) {
      this.initialize(g, rankNodes, true, nodeTypes, edgeTypes);
   }

   public SelectiveBetweennessCentrality(Graph g, boolean rankNodes, boolean rankEdges, String[] nodeTypes, String[] edgeTypes) {
      this.initialize(g, rankNodes, rankEdges, nodeTypes, edgeTypes);
   }

   protected void computeBetweenness() {
      HashMap decorator = new HashMap();
      Map bcVertexDecorator = (Map)this.vertexRankScores.get(this.getRankScoreKey());
      bcVertexDecorator.clear();
      Map bcEdgeDecorator = (Map)this.edgeRankScores.get(this.getRankScoreKey());
      bcEdgeDecorator.clear();
      Collection vertices = super.getVertices();
      Iterator var6 = vertices.iterator();

      JungNode vertex;
      while(var6.hasNext()) {
         vertex = (JungNode)var6.next();
         this.initializeData(decorator);
         ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(vertex)).numSPs = 1.0D;
         ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(vertex)).distance = 0.0D;
         Stack bcValue = new Stack();
         UnboundedFifoBuffer queue = new UnboundedFifoBuffer();
         queue.add(vertex);

         JungNode w;
         JungNode bcValue1;
         Iterator var11;
         SelectiveBetweennessCentrality.BetweennessData var10000;
         while(!queue.isEmpty()) {
            w = (JungNode)queue.remove();
            bcValue.push(w);
            var11 = this.getSuccessors(w).iterator();

            while(var11.hasNext()) {
               bcValue1 = (JungNode)var11.next();
               if(((SelectiveBetweennessCentrality.BetweennessData)decorator.get(bcValue1)).distance < 0.0D) {
                  queue.add(bcValue1);
                  ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(bcValue1)).distance = ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(w)).distance + 1.0D;
               }

               if(((SelectiveBetweennessCentrality.BetweennessData)decorator.get(bcValue1)).distance == ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(w)).distance + 1.0D) {
                  var10000 = (SelectiveBetweennessCentrality.BetweennessData)decorator.get(bcValue1);
                  var10000.numSPs += ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(w)).numSPs;
                  ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(bcValue1)).predecessors.add(w);
               }
            }
         }

         while(!bcValue.isEmpty()) {
            w = (JungNode)bcValue.pop();
            var11 = ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(w)).predecessors.iterator();

            while(var11.hasNext()) {
               bcValue1 = (JungNode)var11.next();
               double partialDependency = ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(bcValue1)).numSPs / ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(w)).numSPs;
               partialDependency *= 1.0D + ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(w)).dependency;
               var10000 = (SelectiveBetweennessCentrality.BetweennessData)decorator.get(bcValue1);
               var10000.dependency += partialDependency;
               JungEdge currentEdge = this.findEdge(bcValue1, w);
               double edgeValue = ((Number)bcEdgeDecorator.get(currentEdge)).doubleValue();
               edgeValue += partialDependency;
               bcEdgeDecorator.put(currentEdge, Double.valueOf(edgeValue));
            }

            if(w != vertex) {
               double bcValue3 = ((Number)bcVertexDecorator.get(w)).doubleValue();
               bcValue3 += ((SelectiveBetweennessCentrality.BetweennessData)decorator.get(w)).dependency;
               bcVertexDecorator.put(w, Double.valueOf(bcValue3));
            }
         }
      }

      if(super.getGraph() instanceof UndirectedGraph) {
         var6 = vertices.iterator();

         double bcValue2;
         while(var6.hasNext()) {
            vertex = (JungNode)var6.next();
            bcValue2 = ((Number)bcVertexDecorator.get(vertex)).doubleValue();
            bcValue2 /= 2.0D;
            bcVertexDecorator.put(vertex, Double.valueOf(bcValue2));
         }

         var6 = super.getEdges().iterator();

         while(var6.hasNext()) {
            JungEdge vertex1 = (JungEdge)var6.next();
            bcValue2 = ((Number)bcEdgeDecorator.get(vertex1)).doubleValue();
            bcValue2 /= 2.0D;
            bcEdgeDecorator.put(vertex1, Double.valueOf(bcValue2));
         }
      }

      var6 = vertices.iterator();

      while(var6.hasNext()) {
         vertex = (JungNode)var6.next();
         decorator.remove(vertex);
      }

   }

   private void initializeData(Map decorator) {
      JungNode e;
      Iterator var3;
      Map bcEdgeDecorator;
      for(var3 = super.getVertices().iterator(); var3.hasNext(); decorator.put(e, new SelectiveBetweennessCentrality.BetweennessData())) {
         e = (JungNode)var3.next();
         bcEdgeDecorator = (Map)this.vertexRankScores.get(this.getRankScoreKey());
         if(!bcEdgeDecorator.containsKey(e)) {
            bcEdgeDecorator.put(e, Double.valueOf(0.0D));
         }
      }

      var3 = super.getEdges().iterator();

      while(var3.hasNext()) {
         JungEdge e1 = (JungEdge)var3.next();
         bcEdgeDecorator = (Map)this.edgeRankScores.get(this.getRankScoreKey());
         if(!bcEdgeDecorator.containsKey(e1)) {
            bcEdgeDecorator.put(e1, Double.valueOf(0.0D));
         }
      }

   }

   public String getRankScoreKey() {
      return "centrality.BetweennessCentrality";
   }

   public void step() {
      this.computeBetweenness();
   }

   class BetweennessData {

      double distance = -1.0D;
      double numSPs = 0.0D;
      List predecessors = new ArrayList();
      double dependency = 0.0D;


   }
}
