package pt.uminho.ceb.biosystems.mew.biologicalnetscore.core.auxiliarclasses.filters;


public class KnockOutComparison extends AbstractComparison<String[]> {

   protected String type;
   protected String[] knockOuts;
   protected boolean ready = false;
   protected String inputType;
   protected String inputId;


   public KnockOutComparison(String type, String[] knockOuts) {
      this.type = type;
      this.knockOuts = knockOuts;
   }

   public boolean checkReady() {
      return this.ready;
   }

   public boolean evaluate() {
      boolean res = this.type.equals(this.inputType);
      if(res) {
         boolean isThere = false;

         for(int i = 0; !isThere && i < this.knockOuts.length; ++i) {
            isThere = this.knockOuts[i].equals(this.inputId);
         }

         res = !isThere;
      }

      this.ready = false;
      return res;
   }

   public void inputData(String[] data) {
      this.inputType = data[0];
      this.inputId = data[1];
      this.ready = true;
   }
}
