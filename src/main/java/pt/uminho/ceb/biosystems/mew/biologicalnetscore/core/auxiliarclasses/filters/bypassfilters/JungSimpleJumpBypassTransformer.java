package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.bypassfilters;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.util.EdgeType;

public class JungSimpleJumpBypassTransformer implements IBypassTransformer {

   protected String startNode = "reaction";
   protected String firstEdge = "produced metabolite";
   protected String bypassNode = "compound";
   protected String secondEdge = "consumed metabolite";
   protected String endNode = "reaction";
   protected String newEdgeType = "metabolism";
   protected boolean allowSelfLoops;
   protected boolean allowParalel;


   public JungSimpleJumpBypassTransformer() {}

   public JungSimpleJumpBypassTransformer(String startNode, String firstEdge, String bypassNode, String secondEdge, String endNode, String newEdgeType) {
      this.startNode = startNode;
      this.firstEdge = firstEdge;
      this.bypassNode = bypassNode;
      this.secondEdge = secondEdge;
      this.endNode = endNode;
      this.newEdgeType = newEdgeType;
      this.allowSelfLoops = true;
      this.allowParalel = true;
   }

   public JungSimpleJumpBypassTransformer(String startNode, String firstEdge, String bypassNode, String secondEdge, String endNode, String newEdgeType, boolean allowSelfLoops, boolean allowParalel) {
      this.startNode = startNode;
      this.firstEdge = firstEdge;
      this.bypassNode = bypassNode;
      this.secondEdge = secondEdge;
      this.endNode = endNode;
      this.newEdgeType = newEdgeType;
      this.allowSelfLoops = allowSelfLoops;
      this.allowParalel = allowParalel;
   }

   public boolean transform(INetwork orginalNet, INetwork transformedNet, IEdge edge, INode start, INode end) {
      boolean res = false;
      if(orginalNet instanceof JungNetwork && transformedNet instanceof JungNetwork && edge instanceof JungEdge && start instanceof JungNode && end instanceof JungNode) {
         IEdge[] edges;
         if(edge.isType().equals(this.secondEdge) && start.getType().equals(this.bypassNode) && end.getType().equals(this.endNode)) {
            edges = orginalNet.getInEdges(start);
            if(edges.length == 0) {
               res = true;
            }

            for(int var13 = 0; var13 < edges.length && !res; ++var13) {
               INode var14 = orginalNet.getConectedNodes(edges[var13])[0];
               if(edges[var13].isType().equals(this.firstEdge) && var14.getType().equals(this.startNode)) {
                  res = true;
               }
            }
         } else if(edge.isType().equals(this.firstEdge) && start.getType().equals(this.startNode) && end.getType().equals(this.bypassNode)) {
            edges = orginalNet.getOutEdges(end);
            if(edges.length == 0) {
               res = true;
            }

            ArrayList temp = null;
            if(!this.allowParalel) {
               temp = new ArrayList();
            }

            for(int i = 0; i < edges.length; ++i) {
               INode nend = orginalNet.getConectedNodes(edges[i])[1];
               if(edges[i].isType().equals(this.secondEdge) && nend.getType().equals(this.endNode)) {
                  JungEdge nedge = new JungEdge(this.newEdgeType);
                  boolean nono = true;
                  if(!this.allowSelfLoops && start.equals(nend)) {
                     nono = false;
                  }

                  if(!this.allowParalel && temp.contains((JungNode)nend)) {
                     if(temp.contains((JungNode)nend)) {
                        nono = false;
                     } else {
                        temp.add((JungNode)nend);
                     }
                  }

                  if(nono) {
                     ((JungNetwork)transformedNet).getGraph().addEdge(nedge, (JungNode)start, (JungNode)nend, EdgeType.DIRECTED);
                     res = true;
                  }
               }
            }
         }
      }

      return res;
   }
}
