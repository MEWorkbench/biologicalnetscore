package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.network;

import java.io.Serializable;

public class JungNode implements Serializable, Comparable, INode {

   private static final long serialVersionUID = 1L;
   private String type;
   private String name;
   private String db_id;
   private String[] database_datatype;
   private String[] database_data;
   private int index;
   private boolean hasPathways;
   private String[] pathways;


   public int getIndex() {
      return this.index;
   }

   public JungNode(String type, String name, String db_id, String[] database_datatype, String[] database_data, int index) {
      this.type = type;
      this.name = name;
      this.db_id = db_id;
      this.database_datatype = database_datatype;
      this.database_data = database_data;
      this.index = index;
      this.hasPathways = false;
      this.pathways = new String[0];
   }

   public JungNode(String type, String name, String db_id, String[] database_datatype, String[] database_data) {
      this.type = type;
      this.name = name;
      this.db_id = db_id;
      this.database_datatype = database_datatype;
      this.database_data = database_data;
      this.index = -1;
      this.hasPathways = false;
      this.pathways = new String[0];
   }

   public String[] getDatabase_data() {
      if(!this.hasPathways) {
         return this.database_data;
      } else {
         String[] res = new String[this.database_data.length + 1];

         int z;
         for(z = 0; z < this.database_data.length; ++z) {
            res[z] = this.database_data[z];
         }

         res[this.database_data.length] = "";
         if(this.pathways.length > 0) {
            res[this.database_data.length] = this.pathways[0];

            for(z = 1; z < this.pathways.length; ++z) {
               res[this.database_data.length] = res[this.database_data.length] + ";" + this.pathways[z];
            }
         }

         return res;
      }
   }

   public String[] getDatabase_datatype() {
      if(!this.hasPathways) {
         return this.database_datatype;
      } else {
         String[] res = new String[this.database_datatype.length + 1];

         for(int i = 0; i < this.database_datatype.length; ++i) {
            res[i] = this.database_datatype[i];
         }

         res[this.database_datatype.length] = "Pathways";
         return res;
      }
   }

   public String getDb_id() {
      return this.db_id;
   }

   public String toString() {
      return this.name;
   }

   public String getType() {
      return this.type;
   }

   public JungNode restart() {
      return new JungNode(this.type, this.name, this.db_id, this.database_datatype, this.database_data, this.index);
   }

   public int compareTo(Object arg0) {
      if(arg0 instanceof JungNode) {
         JungNode n = (JungNode)arg0;
         if(n.getDb_id().equals(this.db_id) && n.getType().equals(this.type)) {
            return 0;
         }
      }

      return 1;
   }

   public JungNode clone() {
      return new JungNode(this.type, this.name, this.db_id, this.database_datatype, this.database_data, this.index);
   }

   public boolean equals(Object node) {
      if(node instanceof JungNode) {
         JungNode n = (JungNode)node;
         return n.getDb_id().equals(this.db_id) && n.getType().equals(this.type);
      } else {
         return false;
      }
   }

   public void setHasPathways(boolean hasPathways) {
      this.hasPathways = hasPathways;
   }

   public boolean hasPathways() {
      return this.hasPathways;
   }

   public void addPathway(String pathway) {
      boolean isThere = false;

      for(int tempPathways = 0; tempPathways < this.pathways.length && !isThere; ++tempPathways) {
         isThere = this.pathways[tempPathways].equals(pathway);
      }

      if(!isThere) {
         String[] var5 = new String[this.pathways.length + 1];

         for(int i = 0; i < this.pathways.length; ++i) {
            var5[i] = this.pathways[i];
         }

         var5[this.pathways.length] = pathway;
         this.pathways = var5;
      }

   }

   public void setDatabase_datatype(String[] databaseDatatype) {
      this.database_datatype = databaseDatatype;
   }

   public void setDatabase_data(String[] databaseData) {
      this.database_data = databaseData;
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

   public void setName(String name) {
      this.name = name;
   }
}
