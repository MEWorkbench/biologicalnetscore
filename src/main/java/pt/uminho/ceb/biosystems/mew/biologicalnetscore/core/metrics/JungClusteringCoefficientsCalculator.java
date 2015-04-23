package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.INode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics.IClusteringCoefficientsCalculator;
import edu.uci.ics.jung.algorithms.metrics.Metrics;

public class JungClusteringCoefficientsCalculator implements IClusteringCoefficientsCalculator {

   protected boolean dir;


   public JungClusteringCoefficientsCalculator(boolean dir) {
      this.dir = dir;
   }

   public void setDirection(boolean dir) {
      this.dir = dir;
   }

   public Map getClusteringCoefficients(INetwork net) {
      return Metrics.clusteringCoefficients(((JungNetwork)net).getGraph());
   }

   public double[][] getCk(INetwork net) {
      HashMap temp = new HashMap();
      INode[] nodes = net.getNodes();
      ArrayList aindex = new ArrayList();

      for(int index = 0; index < nodes.length; ++index) {
         Integer clus = new Integer(net.degree(nodes[index]));
         if(!temp.containsKey(clus)) {
            temp.put(clus, new ArrayList());
            aindex.add(clus);
         }

         ((ArrayList)temp.get(clus)).add(nodes[index]);
      }

      Integer[] var11 = new Integer[aindex.size()];

      for(int var12 = 0; var12 < aindex.size(); ++var12) {
         var11[var12] = (Integer)aindex.get(var12);
      }

      Arrays.sort(var11);
      Map var13 = Metrics.clusteringCoefficients(((JungNetwork)net).getGraph());
      double[][] res = new double[var11.length][2];

      for(int i = 0; i < var11.length; ++i) {
         res[i][0] = var11[i].doubleValue();
         res[i][1] = 0.0D;
         ArrayList alis = (ArrayList)temp.get(var11[i]);

         for(int z = 0; z < alis.size(); ++z) {
            res[i][1] += ((Double)var13.get((JungNode)alis.get(z))).doubleValue();
         }

         res[i][1] /= (double)alis.size();
         if(this.dir) {
            res[i][1] /= 2.0D;
         }
      }

      return res;
   }

   public double[][] getInCk(INetwork net) {
      HashMap temp = new HashMap();
      INode[] nodes = net.getNodes();
      ArrayList aindex = new ArrayList();

      for(int index = 0; index < nodes.length; ++index) {
         Integer clus = new Integer(net.inDegree(nodes[index]));
         if(!temp.containsKey(clus)) {
            temp.put(clus, new ArrayList());
            aindex.add(clus);
         }

         ((ArrayList)temp.get(clus)).add(nodes[index]);
      }

      Integer[] var11 = new Integer[aindex.size()];

      for(int var12 = 0; var12 < aindex.size(); ++var12) {
         var11[var12] = (Integer)aindex.get(var12);
      }

      Arrays.sort(var11);
      Map var13 = Metrics.clusteringCoefficients(((JungNetwork)net).getGraph());
      double[][] res = new double[var11.length][2];

      for(int i = 0; i < var11.length; ++i) {
         res[i][0] = var11[i].doubleValue();
         res[i][1] = 0.0D;
         ArrayList alis = (ArrayList)temp.get(var11[i]);

         for(int z = 0; z < alis.size(); ++z) {
            res[i][1] += ((Double)var13.get((JungNode)alis.get(z))).doubleValue();
         }

         res[i][1] /= (double)alis.size();
         if(this.dir) {
            res[i][1] /= 2.0D;
         }
      }

      return res;
   }

   public double[][] getOutCk(INetwork net) {
      HashMap temp = new HashMap();
      INode[] nodes = net.getNodes();
      ArrayList aindex = new ArrayList();

      for(int index = 0; index < nodes.length; ++index) {
         Integer clus = new Integer(net.outDegree(nodes[index]));
         if(!temp.containsKey(clus)) {
            temp.put(clus, new ArrayList());
            aindex.add(clus);
         }

         ((ArrayList)temp.get(clus)).add(nodes[index]);
      }

      Integer[] var11 = new Integer[aindex.size()];

      for(int var12 = 0; var12 < aindex.size(); ++var12) {
         var11[var12] = (Integer)aindex.get(var12);
      }

      Arrays.sort(var11);
      Map var13 = Metrics.clusteringCoefficients(((JungNetwork)net).getGraph());
      double[][] res = new double[var11.length][2];

      for(int i = 0; i < var11.length; ++i) {
         res[i][0] = var11[i].doubleValue();
         res[i][1] = 0.0D;
         ArrayList alis = (ArrayList)temp.get(var11[i]);

         for(int z = 0; z < alis.size(); ++z) {
            res[i][1] += ((Double)var13.get((JungNode)alis.get(z))).doubleValue();
         }

         res[i][1] /= (double)alis.size();
         if(this.dir) {
            res[i][1] /= 2.0D;
         }
      }

      return res;
   }
}
