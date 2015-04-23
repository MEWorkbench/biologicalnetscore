package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

import java.io.Serializable;
import java.util.HashMap;

import edu.uci.ics.jung.graph.Graph;

public class JungEdge implements Serializable, IEdge {

   private static final long serialVersionUID = 1L;
   private String type;
   private String[] database_datatype;
   private String[] database_data;
   private HashMap extradata;
   private String[] extradatanames;


   public JungEdge(String type) {
      this(type, new String[0], new String[0]);
   }

   public JungEdge(String type, String[] database_datatype, String[] database_data) {
      this.type = type;
      this.database_datatype = database_datatype;
      this.database_data = database_data;
      this.extradata = null;
      this.extradatanames = null;
   }

   public String isType() {
      return this.type;
   }

   public String[] getDatabase_data() {
      return this.database_data;
   }

   public String[] getDatabase_datatype() {
      return this.database_datatype;
   }

   public JungEdge restart() {
      return new JungEdge(this.type, this.database_datatype, this.database_data);
   }

   public HashMap getExtradata() {
      return this.extradata;
   }

   public void setExtradata(HashMap extradata, String[] extradatanames) {
      this.extradata = extradata;
      this.extradatanames = extradatanames;
   }

   public String[] getExtradatanames() {
      return this.extradatanames;
   }

   public JungEdge clone() {
      JungEdge res = new JungEdge(this.type, this.database_datatype, this.database_data);
      res.setExtradata(this.extradata, this.extradatanames);
      return res;
   }

   public JungNode getSource(Graph graph) {
      return (JungNode)graph.getEndpoints(this).getFirst();
   }

   public JungNode getDest(Graph graph) {
      return (JungNode)graph.getEndpoints(this).getSecond();
   }

   public String getData(String dataname) {
      String res = null;

      for(int i = 0; i < this.database_datatype.length && res == null; ++i) {
         if(this.database_datatype[i].equals(dataname)) {
            res = this.database_data[i];
         }
      }

      return res;
   }

   public void addData(String dataname, String data) {
      boolean found = false;

      for(int ndatabase_datatype = 0; ndatabase_datatype < this.database_datatype.length && !found; ++ndatabase_datatype) {
         if(this.database_datatype[ndatabase_datatype].equals(dataname)) {
            this.database_data[ndatabase_datatype] = data;
            found = true;
         }
      }

      if(!found) {
         String[] var7 = new String[this.database_datatype.length + 1];
         String[] ndatabase_data = new String[this.database_data.length + 1];

         for(int i = 0; i < this.database_datatype.length; ++i) {
            var7[i] = this.database_datatype[i];
            ndatabase_data[i] = this.database_data[i];
         }

         var7[this.database_datatype.length] = dataname;
         ndatabase_data[this.database_datatype.length] = data;
         this.database_data = ndatabase_data;
         this.database_datatype = var7;
      }

   }

   public void removeData(String dataname) {
      int z = -1;

      for(int ndatabase_datatype = 0; ndatabase_datatype < this.database_datatype.length && z == -1; ++ndatabase_datatype) {
         if(this.database_datatype[ndatabase_datatype].equals(dataname)) {
            z = ndatabase_datatype;
         }
      }

      if(z != -1) {
         String[] var7 = new String[this.database_datatype.length - 1];
         String[] ndatabase_data = new String[this.database_data.length - 1];
         int x = 0;

         for(int i = 0; i < this.database_datatype.length; ++i) {
            if(i != z) {
               var7[x] = this.database_datatype[i];
               ndatabase_data[x] = this.database_data[i];
               ++x;
            }
         }

         this.database_data = ndatabase_data;
         this.database_datatype = var7;
      }

   }

   public void setType(String type) {
      this.type = type;
   }

   public void setDatabase_data(String[] database_data) {
      this.database_data = database_data;
   }

   public boolean equals(IEdge e) {
      boolean res = true;
      if(e instanceof JungEdge) {
         res = this.type == e.isType();
         if(res) {
            res = this.database_datatype.length == e.getDatabase_datatype().length;
         }

         for(int i = 0; res && i < this.database_datatype.length; ++i) {
            res = this.database_datatype[i].equals(e.getDatabase_datatype()[i]);
            if(res) {
               res = this.database_data[i].equals(e.getDatabase_data()[i]);
            }
         }
      } else {
         res = false;
      }

      return res;
   }
}
