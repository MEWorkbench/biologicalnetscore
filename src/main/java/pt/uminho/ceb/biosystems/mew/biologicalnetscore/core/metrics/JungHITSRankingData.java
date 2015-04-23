package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRDTTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.QRTable;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.SortabelObject4;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNetwork;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.JungNode;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics.PopUpDegreeData;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics.RankerData;
import pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network.metrics.RankingData;
import edu.uci.ics.jung.algorithms.scoring.HITS;
import edu.uci.ics.jung.algorithms.scoring.HITS.Scores;

public class JungHITSRankingData extends RankingData implements Serializable {

   private static final long serialVersionUID = 1L;
   protected double[] authority;
   protected double[] hubness;


   public JungHITSRankingData(JungNetwork net, JungNode[] nodes, PopUpDegreeData popUp, String[] nodetypes) {
      super("HITS Ranker", nodes, "HITS", popUp, nodetypes);
      this.authority = new double[nodes.length];
      this.hubness = new double[nodes.length];
      HITS hits = new HITS(net.getGraph());
      hits.evaluate();

      for(int i = 0; i < nodes.length; ++i) {
         this.authority[i] = ((Scores)hits.getVertexScore(nodes[i])).authority;
         this.hubness[i] = ((Scores)hits.getVertexScore(nodes[i])).hub;
      }

   }

   public QRTable getData() {
      ArrayList columnsNames = new ArrayList();
      columnsNames.add("Type");
      columnsNames.add("Name");
      columnsNames.add("Id");
      columnsNames.add("Authority");
      columnsNames.add("Hubness");
      QRTable qrt = new QRTable(columnsNames, "Regulations", new int[]{0, 0, 0, 1, 1});

      for(int i = 0; i < this.nodes.length; ++i) {
         JungNode n = (JungNode)this.nodes[i];
         if(this.typeSeted.equals("All") || n.getType().equals(this.typeSeted)) {
            ArrayList ql = new ArrayList();
            ql.add(n.getType());
            ql.add(n.toString());
            ql.add(n.getDb_id());
            ql.add(new Double(this.authority[i]));
            ql.add(new Double(this.hubness[i]));
            qrt.addLine(ql);
         }
      }

      return qrt;
   }

   public QRTable getDataByName() {
      return this.getData();
   }

   public void addData(RankerData rd) {
      HashMap dak1 = new HashMap();

      for(int dak2 = 0; dak2 < this.nodes.length; ++dak2) {
         JungNode i = (JungNode)this.nodes[dak2];
         dak1.put(i, "" + this.authority[dak2]);
      }

      rd.addData(dak1, "Authority");
      HashMap var6 = new HashMap();

      for(int var7 = 0; var7 < this.nodes.length; ++var7) {
         JungNode n = (JungNode)this.nodes[var7];
         var6.put(n, "" + this.hubness[var7]);
      }

      rd.addData(var6, "Hubness");
   }

   public double[] getHubnessValues() {
      return this.hubness;
   }

   public double[] getAuthorityValues() {
      return this.authority;
   }

   public QRDTTable getDData() {
      ArrayList columnsNames = new ArrayList();
      columnsNames.add("Type");
      columnsNames.add("Name");
      columnsNames.add("Id");
      columnsNames.add("Authority");
      columnsNames.add("Hubness");
      QRDTTable qrt = new QRDTTable(columnsNames, "Regulations", new int[]{0, 0, 0, 1, 1});
      SortabelObject4[] sos = new SortabelObject4[this.nodes.length];

      int i;
      for(i = 0; i < this.nodes.length; ++i) {
         JungNode ql = (JungNode)this.nodes[i];
         this.typeSeted.equals("All");
         ql.getType().equals(this.typeSeted);
         if(this.typeSeted.equals("All") || ql.getType().equals(this.typeSeted)) {
            ArrayList ql1 = new ArrayList();
            ql1.add(ql.getType());
            ql1.add(ql.toString());
            ql1.add(ql.getDb_id());
            ql1.add(new Double(this.authority[i]));
            ql1.add(new Double(this.hubness[i]));
            sos[i] = new SortabelObject4(i, this.ranker[i], ql1);
         }
      }

      Arrays.sort(sos);
      this.nodeIndex = new int[sos.length];

      for(i = sos.length; i > 0; --i) {
         this.nodeIndex[i - 1] = sos[i - 1].getPosition();
         ArrayList var7 = (ArrayList)sos[i - 1].getNode();
         qrt.addLine(var7);
      }

      return qrt;
   }

   public double getAverangeHubness() {
      double res = 0.0D;

      for(int i = 0; i < this.nodes.length; ++i) {
         res += this.hubness[i];
      }

      return res / (double)this.nodes.length;
   }

   public double getAverangeAuthority() {
      double res = 0.0D;

      for(int i = 0; i < this.nodes.length; ++i) {
         res += this.authority[i];
      }

      return res / (double)this.nodes.length;
   }
}
