package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.bypassfilters;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.util.EdgeType;

public class JungReplacementBypassTransformer implements IBypassTransformer {

   protected String conectorEdgeType;
   protected boolean edgeStartsInNode;
   protected String originalType;
   protected String replecementType;
   protected boolean allowSelfLoops;
   protected boolean keepNonReplacables;
   protected JungNode[] originals;
   protected JungNode[][] replacements;


   public JungReplacementBypassTransformer(String conectorEdgeType, boolean edgeStartsInNode, String originalType, String replecementType) {
      this.conectorEdgeType = conectorEdgeType;
      this.edgeStartsInNode = edgeStartsInNode;
      this.originalType = originalType;
      this.replecementType = replecementType;
      this.allowSelfLoops = false;
      this.keepNonReplacables = true;
      this.originals = null;
      this.replacements = null;
   }

   public JungReplacementBypassTransformer(String conectorEdgeType, boolean edgeStartsInNode, String originalType, String replecementType, boolean allowSelfLoops, boolean keepNonReplacables) {
      this.conectorEdgeType = conectorEdgeType;
      this.edgeStartsInNode = edgeStartsInNode;
      this.originalType = originalType;
      this.replecementType = replecementType;
      this.allowSelfLoops = allowSelfLoops;
      this.keepNonReplacables = keepNonReplacables;
      this.originals = null;
      this.replacements = null;
   }

   protected void initialize(INetwork orginalNet) {
      INode[] ori = orginalNet.getNodes(this.originalType);
      this.originals = new JungNode[ori.length];
      this.replacements = new JungNode[ori.length][];

      for(int i = 0; i < ori.length; ++i) {
         JungNode or = (JungNode)ori[i];
         this.originals[i] = or;
         IEdge[] redges;
         if(this.edgeStartsInNode) {
            redges = orginalNet.getOutEdges(or);
         } else {
            redges = orginalNet.getInEdges(or);
         }

         ArrayList treplacements = new ArrayList();

         for(int d = 0; d < redges.length; ++d) {
            if(redges[d].isType().equals(this.conectorEdgeType)) {
               INode replacement;
               if(this.edgeStartsInNode) {
                  replacement = orginalNet.getConectedNodes(redges[d])[1];
               } else {
                  replacement = orginalNet.getConectedNodes(redges[d])[0];
               }

               if(((JungNode)replacement).getType().equals(this.replecementType)) {
                  treplacements.add((JungNode)replacement);
               }
            }
         }

         this.replacements[i] = (JungNode[])treplacements.toArray(new JungNode[0]);
      }

   }

   private JungNode[] getReplecements(JungNode or) {
      for(int i = 0; i < this.originals.length; ++i) {
         if(this.originals[i].equals(or)) {
            return this.replacements[i];
         }
      }

      return null;
   }

   public boolean transform(INetwork orginalNet, INetwork transformedNet, IEdge edge, INode start, INode end) {
      boolean res = false;
      if(orginalNet instanceof JungNetwork && transformedNet instanceof JungNetwork && edge instanceof JungEdge && start instanceof JungNode && end instanceof JungNode) {
         if(this.originals == null) {
            this.initialize(orginalNet);
         }

         if(edge.isType().equals(this.conectorEdgeType) && this.edgeStartsInNode && start.getType().equals(this.originalType) && end.getType().equals(this.replecementType)) {
            res = true;
         } else if(edge.isType().equals(this.conectorEdgeType) && start.getType().equals(this.replecementType) && end.getType().equals(this.originalType)) {
            res = true;
         } else {
            JungNode[] replecementNodes;
            int z;
            int startreplecementNodes;
            JungNode h;
            boolean go;
            JungEdge nstart;
            int nedge;
            JungNode nstart1;
            JungEdge nedge1;
            JungEdge var17;
            JungNode[] var16;
            int var19;
            JungEdge var18;
            JungNode var21;
            JungNode[] var20;
            JungEdge var22;
            if(start.getType().equals(this.originalType)) {
               replecementNodes = this.getReplecements((JungNode)start);
               z = replecementNodes.length;
               res = true;

               for(startreplecementNodes = 0; startreplecementNodes < replecementNodes.length && replecementNodes[startreplecementNodes] != null; ++startreplecementNodes) {
                  h = replecementNodes[startreplecementNodes];
                  go = true;
                  if(!end.getType().equals(this.originalType)) {
                     if(h.equals((JungNode)end) && !this.allowSelfLoops) {
                        go = false;
                     }

                     if(go) {
                        nstart = ((JungEdge)edge).clone();
                        ((JungNetwork)transformedNet).getGraph().addEdge(nstart, h, (JungNode)end, EdgeType.DIRECTED);
                     }
                  } else {
                     var20 = this.getReplecements((JungNode)end);
                     if(var20.length == 0 && this.keepNonReplacables) {
                        if(h.equals((JungNode)end) && !this.allowSelfLoops) {
                           go = false;
                        }

                        if(go) {
                           var22 = ((JungEdge)edge).clone();
                           ((JungNetwork)transformedNet).getGraph().addEdge(var22, h, (JungNode)end, EdgeType.DIRECTED);
                        }
                     } else {
                        for(nedge = 0; nedge < var20.length; ++nedge) {
                           go = true;
                           nstart1 = var20[nedge];
                           if(h.equals(nstart1) && !this.allowSelfLoops) {
                              go = false;
                           }

                           if(go) {
                              nedge1 = ((JungEdge)edge).clone();
                              ((JungNetwork)transformedNet).getGraph().addEdge(nedge1, h, nstart1, EdgeType.DIRECTED);
                           }
                        }
                     }
                  }
               }

               if(z == 0 && this.keepNonReplacables) {
                  if(!end.getType().equals(this.originalType)) {
                     var17 = ((JungEdge)edge).clone();
                     ((JungNetwork)transformedNet).getGraph().addEdge(var17, (JungNode)start, (JungNode)end, EdgeType.DIRECTED);
                  } else {
                     var16 = this.getReplecements((JungNode)end);
                     if(var16.length == 0 && this.keepNonReplacables) {
                        var18 = ((JungEdge)edge).clone();
                        ((JungNetwork)transformedNet).getGraph().addEdge(var18, (JungNode)start, (JungNode)end, EdgeType.DIRECTED);
                     } else {
                        for(var19 = 0; var19 < var16.length; ++var19) {
                           go = true;
                           var21 = var16[var19];
                           if(start.equals(var21) && !this.allowSelfLoops) {
                              go = false;
                           }

                           if(go) {
                              var22 = ((JungEdge)edge).clone();
                              ((JungNetwork)transformedNet).getGraph().addEdge(var22, (JungNode)start, var21, EdgeType.DIRECTED);
                           }
                        }
                     }
                  }
               }
            } else if(end.getType().equals(this.originalType)) {
               replecementNodes = this.getReplecements((JungNode)end);
               z = replecementNodes.length;
               res = true;

               for(startreplecementNodes = 0; startreplecementNodes < replecementNodes.length && replecementNodes[startreplecementNodes] != null; ++startreplecementNodes) {
                  h = replecementNodes[startreplecementNodes];
                  go = true;
                  if(!start.getType().equals(this.originalType)) {
                     if(h.equals((JungNode)start) && !this.allowSelfLoops) {
                        go = false;
                     }

                     if(go) {
                        nstart = ((JungEdge)edge).clone();
                        ((JungNetwork)transformedNet).getGraph().addEdge(nstart, (JungNode)start, h, EdgeType.DIRECTED);
                     }
                  } else {
                     var20 = this.getReplecements((JungNode)start);
                     if(var20.length == 0 && this.keepNonReplacables) {
                        if(h.equals((JungNode)start) && !this.allowSelfLoops) {
                           go = false;
                        }

                        if(go) {
                           var22 = ((JungEdge)edge).clone();
                           ((JungNetwork)transformedNet).getGraph().addEdge(var22, (JungNode)start, h, EdgeType.DIRECTED);
                        }
                     } else {
                        for(nedge = 0; nedge < var20.length; ++nedge) {
                           go = true;
                           nstart1 = var20[nedge];
                           if(nstart1.equals(h) && !this.allowSelfLoops) {
                              go = false;
                           }

                           if(go) {
                              nedge1 = ((JungEdge)edge).clone();
                              ((JungNetwork)transformedNet).getGraph().addEdge(nedge1, nstart1, h, EdgeType.DIRECTED);
                           }
                        }
                     }
                  }
               }

               if(z == 0 && this.keepNonReplacables) {
                  if(!start.getType().equals(this.originalType)) {
                     var17 = ((JungEdge)edge).clone();
                     ((JungNetwork)transformedNet).getGraph().addEdge(var17, (JungNode)start, (JungNode)end, EdgeType.DIRECTED);
                  } else {
                     var16 = this.getReplecements((JungNode)start);
                     if(var16.length == 0 && this.keepNonReplacables) {
                        var18 = ((JungEdge)edge).clone();
                        if(!start.getType().equals(this.originalType)) {
                           ((JungNetwork)transformedNet).getGraph().addEdge(var18, (JungNode)start, (JungNode)end, EdgeType.DIRECTED);
                        }
                     } else {
                        for(var19 = 0; var19 < var16.length; ++var19) {
                           go = true;
                           var21 = var16[var19];
                           if(var21.equals(end) && !this.allowSelfLoops) {
                              go = false;
                           }

                           if(go) {
                              var22 = ((JungEdge)edge).clone();
                              ((JungNetwork)transformedNet).getGraph().addEdge(var22, var21, (JungNode)end, EdgeType.DIRECTED);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return res;
   }
}
