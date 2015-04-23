package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics.customJob.closenessCentrality;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.IEdge;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;

public class SelectiveBFS {

   protected INode[] nodes;
   protected int[] distance;
   protected ArrayList[] connectesBy;
   protected ArrayList[] connectesBySet;


   public SelectiveBFS(INetwork net, INode start, String[] edgeTypes, ArrayList permitedEdgeTypes, ArrayList permitedNodeTypes) {
      this.nodes = net.getNodes();
      this.distance = new int[this.nodes.length];
      this.connectesBy = new ArrayList[this.nodes.length];
      this.connectesBySet = new ArrayList[this.nodes.length];

      for(int current = 0; current < this.nodes.length; ++current) {
         this.distance[current] = -1;
         this.connectesBy[current] = new ArrayList();
         this.connectesBySet[current] = new ArrayList();
      }

      ArrayList var24 = new ArrayList();
      var24.add(start);
      int dis = 0;
      boolean change = true;
      boolean stop = false;

      for(int ns = 0; ns < this.nodes.length && !stop; ++ns) {
         if(this.nodes[ns].equals(start)) {
            stop = true;
            this.distance[ns] = 0;
         }
      }

      while(change) {
         ++dis;
         change = false;
         INode[] var25 = new INode[var24.size()];

         int i;
         for(i = 0; i < var24.size(); ++i) {
            var25[i] = (INode)var24.get(i);
         }

         var24 = new ArrayList();

         for(i = 0; i < var25.length; ++i) {
            IEdge[] edges = net.getOutEdges(var25[i]);

            for(int a = 0; a < edges.length; ++a) {
               IEdge aedge = edges[a];
               INode anode = net.getConectedNodes(edges[a])[1];
               if(permitedEdgeTypes.contains(aedge.isType()) && permitedNodeTypes.contains(anode.getType())) {
                  boolean ok = false;
                  boolean keyComplete = false;
                  int found = -1;

                  for(int enteredBySet = 0; enteredBySet < this.nodes.length && found == -1; ++enteredBySet) {
                     if(var25[i].equals(this.nodes[enteredBySet])) {
                        found = enteredBySet;
                     }
                  }

                  ArrayList var27 = this.connectesBySet[found];

                  for(int pc = 0; pc < edgeTypes.length && !ok; ++pc) {
                     if(edges[a].isType().equals(edgeTypes[pc])) {
                        String c = edges[a].getData("Set");
                        if(c != null && var27.size() != 0) {
                           if(c.equals("nill")) {
                              ok = true;
                              keyComplete = true;
                           } else if(var27.contains(c)) {
                              ok = true;
                              keyComplete = true;
                           }
                        } else {
                           ok = true;
                        }
                     }
                  }

                  if(ok) {
                     INode[] var26 = net.getConectedNodes(edges[a]);
                     INode var28 = var26[1];
                     if(var28.equals(var25[i])) {
                        var28 = var26[0];
                     }

                     int pos = -1;

                     for(int set = 0; set < this.nodes.length && pos == -1; ++set) {
                        if(this.nodes[set].equals(var28)) {
                           pos = set;
                        }
                     }

                     String var29 = null;
                     if(!keyComplete) {
                        var29 = edges[a].getData("Set");
                        if(var29 == null || var29.equals("nill")) {
                           var29 = null;
                        }
                     }

                     if(this.distance[pos] == -1) {
                        this.distance[pos] = dis;
                        this.connectesBy[pos].add(var25[i]);
                        if(var29 != null) {
                           this.connectesBySet[pos].add(var29);
                        }

                        var24.add(this.nodes[pos]);
                        change = true;
                     } else if(this.distance[pos] == dis && !this.connectesBy[pos].contains(var25[i])) {
                        this.connectesBy[pos].add(var25[i]);
                        if(var29 != null) {
                           this.connectesBySet[pos].add(var29);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public INode[][] getPaths(INode end) {
      ArrayList inco = new ArrayList();
      ArrayList tres = new ArrayList();
      boolean stop = false;

      int x;
      for(int res = 0; res < this.nodes.length && !stop; ++res) {
         if(this.nodes[res].equals(end)) {
            stop = true;

            for(x = 0; x < this.connectesBy[res].size(); ++x) {
               INode y = (INode)this.connectesBy[res].get(x);
               ArrayList i = new ArrayList();
               i.add(y);
               inco.add(i);
            }
         }
      }

      while(inco.size() != 0) {
         ArrayList var14 = inco;
         inco = new ArrayList();

         for(x = 0; x < var14.size(); ++x) {
            ArrayList var15 = (ArrayList)var14.get(x);
            stop = false;

            for(int var17 = 0; var17 < this.nodes.length && !stop; ++var17) {
               if(this.nodes[var17].equals(var15.get(var15.size() - 1))) {
                  if(this.connectesBy[var17].size() == 0) {
                     tres.add(var15);
                  } else {
                     for(int x1 = 0; x1 < this.connectesBy[var17].size(); ++x1) {
                        INode nz = (INode)this.connectesBy[var17].get(x1);
                        ArrayList nt = new ArrayList();

                        for(int u = 0; u < var15.size(); ++u) {
                           nt.add((INode)var15.get(u));
                        }

                        nt.add(nz);
                        inco.add(nt);
                     }
                  }
               }
            }
         }
      }

      INode[][] var13 = new INode[tres.size()][];

      for(x = 0; x < tres.size(); ++x) {
         var13[x] = new INode[((ArrayList)tres.get(x)).size() + 1];

         for(int var16 = 0; var16 < ((ArrayList)tres.get(x)).size(); ++var16) {
            var13[x][var16] = (INode)((ArrayList)tres.get(x)).get(((ArrayList)tres.get(x)).size() - 1 - var16);
         }

         var13[x][((ArrayList)tres.get(x)).size()] = end;
      }

      return var13;
   }

   public int getDistance(INode end) {
      int res = -1;
      boolean stop = false;

      for(int i = 0; i < this.nodes.length && !stop; ++i) {
         if(this.nodes[i].equals(end)) {
            stop = true;
            res = this.distance[i];
         }
      }

      return res;
   }

   public void printDistances() {
      for(int i = 0; i < this.distance.length; ++i) {
         System.out.println(this.nodes[i].toString() + ":" + this.distance[i]);
      }

   }

   public void printDistancesPaths() {
      for(int i = 0; i < this.distance.length; ++i) {
         if(this.distance[i] != -1) {
            System.out.println(this.nodes[i].toString() + ":" + this.distance[i]);

            for(int z = 0; z < this.connectesBy[i].size(); ++z) {
               System.out.print(((INode)this.connectesBy[i].get(z)).toString() + " ");
            }

            System.out.println();
         }
      }

   }
}
