package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters.bypassfilters;

import java.util.ArrayList;
import java.util.HashMap;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import edu.uci.ics.jung.graph.util.EdgeType;

public class JungChainBypassTransformer implements IBypassTransformer {

   protected String startType;
   protected String edgesOfChainType;
   protected boolean allowSelfLoops;
   protected boolean keepNonReplacables;
   protected HashMap replacements;


   public JungChainBypassTransformer(String startType, String edgesOfChainType) {
      this.startType = startType;
      this.edgesOfChainType = edgesOfChainType;
      this.allowSelfLoops = false;
      this.keepNonReplacables = true;
      this.replacements = null;
   }

   public JungChainBypassTransformer(String startType, String edgesOfChainType, boolean allowSelfLoops, boolean keepNonReplacables) {
      this.startType = startType;
      this.edgesOfChainType = edgesOfChainType;
      this.allowSelfLoops = allowSelfLoops;
      this.keepNonReplacables = keepNonReplacables;
      this.replacements = null;
   }

   protected void getChain(INetwork orginalNet, JungNode start, ArrayList inChain) {
      IEdge[] redges = orginalNet.getOutEdges(start);

      for(int d = 0; d < redges.length; ++d) {
         if(redges[d].isType().equals(this.edgesOfChainType)) {
            JungNode replacement = (JungNode)orginalNet.getConectedNodes(redges[d])[1];
            inChain.add(replacement);
            if(this.isChain(orginalNet, replacement)) {
               this.getChain(orginalNet, replacement, inChain);
            }
         }
      }

   }

   protected boolean isChain(INetwork orginalNet, JungNode no) {
      IEdge[] redges = orginalNet.getOutEdges(no);

      for(int d = 0; d < redges.length; ++d) {
         if(redges[d].isType().equals(this.edgesOfChainType)) {
            return true;
         }
      }

      return false;
   }

   protected void cleanExcess() {
      JungNode[] ns = (JungNode[])this.replacements.keySet().toArray(new JungNode[0]);

      for(int i = 0; i < ns.length; ++i) {
         ArrayList vug = (ArrayList)this.replacements.get(ns[i]);
         ArrayList temp = new ArrayList();

         int d;
         for(d = 0; d < vug.size(); ++d) {
            if(this.replacements.containsKey(vug.get(d))) {
               temp.add((JungNode)vug.get(d));
            }
         }

         for(d = 0; d < temp.size(); ++d) {
            vug.remove(temp.get(d));
         }
      }

   }

   protected void initialize(INetwork orginalNet) {
      INode[] ori = orginalNet.getNodes(this.startType);
      this.replacements = new HashMap();

      for(int i = 0; i < ori.length; ++i) {
         ArrayList chain = new ArrayList();
         this.getChain((JungNetwork)orginalNet, (JungNode)ori[i], chain);

         for(int d = 0; d < chain.size(); ++d) {
            JungNode cha = (JungNode)chain.get(d);
            if(!this.replacements.containsKey(cha)) {
               this.replacements.put(cha, new ArrayList());
            }

            if(!((ArrayList)this.replacements.get(cha)).contains((JungNode)ori[i])) {
               ((ArrayList)this.replacements.get(cha)).add((JungNode)ori[i]);
            }
         }
      }

      this.cleanExcess();
   }

   public boolean transform(INetwork orginalNet, INetwork transformedNet, IEdge edge, INode start, INode end) {
      boolean res = false;
      if(orginalNet instanceof JungNetwork && transformedNet instanceof JungNetwork && edge instanceof JungEdge && start instanceof JungNode && end instanceof JungNode) {
         if(this.replacements == null) {
            this.initialize(orginalNet);
         }

         if(edge.isType().equals(this.edgesOfChainType)) {
            res = true;
         } else {
            ArrayList replecementNodes;
            int z;
            int nedge;
            JungNode nend;
            boolean go;
            JungEdge startreplecementNodes;
            int h;
            JungNode nstart;
            JungEdge nedge1;
            JungEdge var17;
            ArrayList var20;
            JungEdge var22;
            if(this.replacements.containsKey((JungNode)start)) {
               replecementNodes = (ArrayList)this.replacements.get((JungNode)start);
               z = replecementNodes.size();
               res = true;

               for(nedge = 0; nedge < replecementNodes.size(); ++nedge) {
                  nend = (JungNode)replecementNodes.get(nedge);
                  go = true;
                  if(!this.replacements.containsKey((JungNode)end)) {
                     if(nend.equals((JungNode)end) && !this.allowSelfLoops) {
                        go = false;
                     }

                     if(go) {
                        startreplecementNodes = ((JungEdge)edge).clone();
                        ((JungNetwork)transformedNet).getGraph().addEdge(startreplecementNodes, nend, (JungNode)end, EdgeType.DIRECTED);
                     }
                  } else {
                     var20 = (ArrayList)this.replacements.get((JungNode)end);
                     if(var20.size() == 0 && this.keepNonReplacables) {
                        if(nend.equals((JungNode)end) && !this.allowSelfLoops) {
                           go = false;
                        }

                        if(go) {
                           var22 = ((JungEdge)edge).clone();
                           ((JungNetwork)transformedNet).getGraph().addEdge(var22, nend, (JungNode)end, EdgeType.DIRECTED);
                        }
                     } else {
                        for(h = 0; h < var20.size(); ++h) {
                           go = true;
                           nstart = (JungNode)var20.get(h);
                           if(nend.equals(nstart) && !this.allowSelfLoops) {
                              go = false;
                           }

                           if(go) {
                              nedge1 = ((JungEdge)edge).clone();
                              ((JungNetwork)transformedNet).getGraph().addEdge(nedge1, nend, nstart, EdgeType.DIRECTED);
                           }
                        }
                     }
                  }
               }

               if(z == 0 && this.keepNonReplacables) {
                  if(!this.replacements.containsKey((JungNode)end)) {
                     var17 = ((JungEdge)edge).clone();
                     ((JungNetwork)transformedNet).getGraph().addEdge(var17, (JungNode)start, (JungNode)end, EdgeType.DIRECTED);
                  } else {
                     ArrayList var16 = (ArrayList)this.replacements.get((JungNode)end);
                     if(var16.size() == 0 && this.keepNonReplacables) {
                        JungEdge var18 = ((JungEdge)edge).clone();
                        ((JungNetwork)transformedNet).getGraph().addEdge(var18, (JungNode)start, (JungNode)end, EdgeType.DIRECTED);
                     } else {
                        for(int var19 = 0; var19 < var16.size(); ++var19) {
                           go = true;
                           JungNode var21 = (JungNode)var16.get(var19);
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
            } else if(this.replacements.containsKey((JungNode)end)) {
               replecementNodes = (ArrayList)this.replacements.get((JungNode)end);
               z = replecementNodes.size();
               res = true;

               for(nedge = 0; nedge < replecementNodes.size(); ++nedge) {
                  nend = (JungNode)replecementNodes.get(nedge);
                  go = true;
                  if(!this.replacements.containsKey((JungNode)start)) {
                     if(nend.equals((JungNode)start) && !this.allowSelfLoops) {
                        go = false;
                     }

                     if(go) {
                        startreplecementNodes = ((JungEdge)edge).clone();
                        ((JungNetwork)transformedNet).getGraph().addEdge(startreplecementNodes, (JungNode)start, nend, EdgeType.DIRECTED);
                     }
                  } else {
                     var20 = (ArrayList)this.replacements.get((JungNode)start);
                     if(var20.size() == 0 && this.keepNonReplacables) {
                        if(nend.equals((JungNode)start) && !this.allowSelfLoops) {
                           go = false;
                        }

                        if(go) {
                           var22 = ((JungEdge)edge).clone();
                           ((JungNetwork)transformedNet).getGraph().addEdge(var22, (JungNode)start, nend, EdgeType.DIRECTED);
                        }
                     } else {
                        for(h = 0; h < var20.size(); ++h) {
                           go = true;
                           nstart = (JungNode)var20.get(h);
                           if(nstart.equals(nend) && !this.allowSelfLoops) {
                              go = false;
                           }

                           if(go) {
                              nedge1 = ((JungEdge)edge).clone();
                              ((JungNetwork)transformedNet).getGraph().addEdge(nedge1, nstart, nend, EdgeType.DIRECTED);
                           }
                        }
                     }
                  }
               }

               if(z == 0 && this.keepNonReplacables) {
                  var17 = ((JungEdge)edge).clone();
                  ((JungNetwork)transformedNet).getGraph().addEdge(var17, (JungNode)start, (JungNode)end, EdgeType.DIRECTED);
               }
            }
         }
      }

      return res;
   }
}
